package com.staroot.urlping.controller;

import com.staroot.urlping.service.CertService;
import com.staroot.urlping.service.EmailService;
import com.staroot.urlping.util.EmailContentGenerator;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class EmailController {

    private final EmailService emailService;
    private final CertService certService;
    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    @Value("${cert.remain.warn:30}")
    private long addDay;

    @Autowired
    public EmailController(EmailService emailService, CertService certService) {
        this.emailService = emailService;
        this.certService = certService;
    }

    @GetMapping("/send-email-test")
    public String sendEmail() {
        String to = "hiwattc@gmail.com";
        String subject = "메일 제목";
        String content = "메일 내용입니다.";

        List certResponseList = new ArrayList<>();
        //certResponseList = certService.getCertChkList();
        certResponseList = certService.findByEndDtWithin(addDay);

        logger.debug("Size of certResponseList ::" + certResponseList.size());
        try {
            content = EmailContentGenerator.generateCertResponseListHtml(certResponseList);
            logger.debug("Email content ::" + content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //emailService.sendEmail(to, subject, content);
        try {
            if(certResponseList.size() > 0){
                emailService.sendHtmlEmail(to, subject, content);
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return "이메일이 발송되었습니다.";
    }
}
