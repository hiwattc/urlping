package com.staroot.urlping.controller;

import com.staroot.urlping.service.CertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CertController {
    private final CertService certService;
    private static final Logger logger = LoggerFactory.getLogger(CertController.class);

    public CertController(CertService certService) {
        this.certService = certService;
    }

    @GetMapping("/cert")
    public String getCert(Model model) throws IOException {
        System.out.println("cert called");
        logger.debug("URL: {}", "Hello this is /cert");
        List certResponseList = new ArrayList<>();
        certResponseList = certService.getCertChkList();
        model.addAttribute("certResponses", certResponseList);
        return "certResponseList";
    }
    @GetMapping("/certchk")
    @ResponseBody
    public void getCertChk(Model model) throws IOException {
        System.out.println("certchk called");
        logger.debug("URL: {}", "Hello this is /certchk");
        certService.checkCert();
    }
}

