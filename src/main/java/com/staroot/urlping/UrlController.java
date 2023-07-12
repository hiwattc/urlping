package com.staroot.urlping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class UrlController {
    private final UrlService urlService;
    private final CertService certService;
    private static final Logger logger = LoggerFactory.getLogger(UrlService.class);
    @Autowired
    public UrlController(UrlService urlService, CertService certService) {
        this.urlService = urlService;
        this.certService = certService;
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

