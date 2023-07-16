package com.staroot.urlping.batchjob;

import com.staroot.urlping.service.CertService;
import com.staroot.urlping.service.EmailService;
import com.staroot.urlping.util.EmailContentGenerator;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BatchCertChk {
    private static final Logger logger = LoggerFactory.getLogger(BatchCertChk.class);
    private final CertService certService;
    private final EmailService emailService;
    @Value("${cert.remain.warn:30}")
    private long addDay;

    public BatchCertChk(CertService certService, EmailService emailService) {
        this.certService = certService;
        this.emailService = emailService;
    }

    @Scheduled(fixedRate = 60000) // 1분(600,00 밀리초)마다 실행
    public void executeBatchJob() {
        // 배치 작업 실행 로직을 작성합니다.
        logger.debug("Batch job started!");
        certService.checkCert();
        logger.debug("Batch job completed!");
    }


    @Scheduled(fixedRate = 60000) // 1분(600,00 밀리초)마다 실행
    public void executeCertEmailBatchJob() {
        String to = "starootmaster@gmail.com";
        String subject = "인증서 만료 체크";
        String content = "";

        List certResponseList = new ArrayList<>();
        //certResponseList = certService.getCertChkList();
        certResponseList = certService.findByEndDtWithin(addDay);

        logger.debug("Size of certResponseList ::" + certResponseList.size());
        try {
            content = EmailContentGenerator.generateCertResponseListHtml(certResponseList);
            logger.debug("Email content ::" + content);
            if(certResponseList.size() > 0){
                emailService.sendHtmlEmail(to, subject, content);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
