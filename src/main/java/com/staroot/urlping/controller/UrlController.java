package com.staroot.urlping.controller;

import com.staroot.urlping.service.CertService;
import com.staroot.urlping.service.UrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UrlController {
    private final UrlService urlService;
    private final CertService certService;
    private static final Logger logger = LoggerFactory.getLogger(UrlController.class);
    @Autowired
    public UrlController(UrlService urlService, CertService certService) {
        this.urlService = urlService;
        this.certService = certService;
    }
    @GetMapping("/urls")
    public void getUrls() throws IOException {
        System.out.println("urls called");
        logger.debug("URL: {}", "Hello this is /urls");
    }


    @PostMapping("/urls")
    public void saveUrlResponse(@RequestBody String url) throws IOException {
        logger.debug("URL: {}", url);
        urlService.saveUrlResponse(url);
    }
    @PostMapping("/cert")
    public void saveCertResponse(@RequestBody String url) throws IOException {
        logger.debug("URL: {}", url);
        certService.saveCertResponse(url);
    }
}

