package com.staroot.urlping.service;


import com.staroot.urlping.domain.CertResponse;
import com.staroot.urlping.repository.CertResponseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CertService {
    private final CertResponseRepository certResponseRepository;
    private static final Logger logger = LoggerFactory.getLogger(UrlService.class);

    @Autowired
    public CertService(CertResponseRepository certResponseRepository) {
        this.certResponseRepository = certResponseRepository;
    }

    @Transactional
    public void saveCertResponse(String url) throws IOException {
        logger.debug("URL: {}", url);

        CertResponse certResponse = new CertResponse();
        certResponse.setUrl(url);
        certResponse.setLogDt(LocalDateTime.now());
        certResponse.setSysMgr("hiwatt");
        certResponse.setAppMgr("hiwatt");
        certResponse.setEndDt("2023-07-13");
        certResponse.setRootCaEndDt("2023-07-13");
        certResponse.setChain1EndDt("2023-07-13");
        certResponse.setChain2EndDt("2023-07-13");

        logger.debug("certResponse :: {}", certResponse.toString());
        certResponseRepository.save(certResponse);
    }
    public List getCertChkList(){
        List certResponseList = new ArrayList<>();
        certResponseList = certResponseRepository.findAll();
        return certResponseList;
    }
    public List findAllForExpireChk(){
        List certResponseList = new ArrayList<>();
        certResponseList = certResponseRepository.findAllForExpireChk();
        return certResponseList;
    }
    public List findByEndDtWithin(long addDay){
        LocalDate currentDate = LocalDate.now();
        LocalDate targetDate = currentDate.plusDays(addDay);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String targetDateString = targetDate.format(formatter);

        System.out.println("Target Date: " + targetDate);
        List certResponseList = new ArrayList<>();
        certResponseList = certResponseRepository.findByEndDtWithin(targetDateString);
        return certResponseList;
    }


    public List checkCert(){
        String fileName = "url_list.txt";
        String filePath = System.getProperty("user.dir") + File.separator + fileName;
        List certResponseList = new ArrayList<>();
        CertResponse certResponse = new CertResponse();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if(!line.startsWith("#")){
                    line = line.trim();
                    if (!line.isEmpty()) {
                        certResponse = checkCertByURL(line);
                        certResponseRepository.save(certResponse);
                        certResponseList.add(certResponse);

                        //만료기준 알림을 주기위한 조건 설정
//                        if(getDaysRemaining(certResponse.getEndDt()) < 60){
//                            certResponseList.add(certResponse);
//                        }


                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return certResponseList;
    }


    @Transactional
    private CertResponse checkCertByURL(String url) {
        // 첫 번째 인증서는 최상위 인증서
        CertResponse certResponse = new CertResponse();

        try {
            // 인증서 검증 비활성화
            disableCertificateValidation();
            int connectionTimeout = 3000; // 연결 타임아웃 (5초)
            int readTimeout = 3000; // 읽기 타임아웃 (10초)

            System.out.print("Checking : "+url +" ... ");
            URL serverURL = new URL(url);
            HttpsURLConnection conn = (HttpsURLConnection) serverURL.openConnection();
            conn.setConnectTimeout(connectionTimeout);
            conn.setReadTimeout(readTimeout);
            conn.connect();
            Certificate[] certs = conn.getServerCertificates();


            X509Certificate rootCert = (X509Certificate) certs[0];
            String rootExpirationDate = formatDate(rootCert.getNotAfter());
            long rootDaysRemaining = getDaysRemaining(rootCert.getNotAfter());
            System.out.print("(" + rootExpirationDate+"/"+rootDaysRemaining + "일) ");
            for (int i = 1; i < certs.length; i++) {
                X509Certificate intermediateCert = (X509Certificate) certs[i];
                String intermediateExpirationDate = formatDate(intermediateCert.getNotAfter());
                long intermediateDaysRemaining = getDaysRemaining(intermediateCert.getNotAfter());
                System.out.print("(" + intermediateExpirationDate+"/"+intermediateDaysRemaining + "일) ");
                switch (i){
                    case 1:
                        certResponse.setRootCaEndDt(intermediateExpirationDate);
                        break;
                    case 2:
                        certResponse.setChain1EndDt(intermediateExpirationDate);
                        break;
                    case 3:
                        certResponse.setChain2EndDt(intermediateExpirationDate);
                        break;
                }
            }

            //save check result info
            certResponse.setUrl(url);
            certResponse.setLogDt(LocalDateTime.now());
            certResponse.setEndDt(rootExpirationDate);
            certResponse.setSysMgr("admin");
            certResponse.setAppMgr("manager");


            System.out.println("");
            conn.disconnect();



        } catch (IOException e) {
            //e.printStackTrace();
            if(e.toString().length() > 100){
                System.out.println(e.toString().substring(0,99));
            }else{
                System.out.println(e.toString());
            }
        }
        return certResponse;
    }
    private void disableCertificateValidation() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                        }
                        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String formatDate(java.util.Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    private long getDaysRemaining(Date expirationDate) {
        long currentTimeMillis = System.currentTimeMillis();
        long expirationTimeMillis = expirationDate.getTime();
        long remainingMillis = expirationTimeMillis - currentTimeMillis;
        return TimeUnit.MILLISECONDS.toDays(remainingMillis);
    }
    private long getDaysRemaining(String expirationDate) {
        LocalDate currentDate = LocalDate.now();
        LocalDate targetDate = LocalDate.parse(expirationDate);
        long daysDifference = ChronoUnit.DAYS.between(currentDate, targetDate);
        return daysDifference;
    }
}

