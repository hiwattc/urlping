package com.staroot.urlping;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class CertService {
    private final CertResponseRepository certResponseRepository;

    @Autowired
    public CertService(CertResponseRepository certResponseRepository) {
        this.certResponseRepository = certResponseRepository;
    }

    @Transactional
    public void saveCertResponse(String url) throws IOException {

        CertResponse certResponse = new CertResponse();
        certResponse.setUrl(url);
        certResponse.setLogDt(LocalDateTime.now());
        certResponse.setSysMgr("hiwatt");
        certResponse.setAppMgr("hiwatt");
        certResponse.setEndDt("2023-07-13");
        certResponse.setRootCaEndDt("2023-07-13");
        certResponse.setChain1EndDt("2023-07-13");
        certResponse.setChain2EndDt("2023-07-13");


        certResponseRepository.save(certResponse);
    }
}

