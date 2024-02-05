package com.staroot.urlping.controller;

import com.staroot.urlping.service.DNSRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DNSRecordController {

    private final DNSRecordService dnsRecordService;

    @Autowired
    public DNSRecordController(DNSRecordService dnsRecordService) {
        this.dnsRecordService = dnsRecordService;
    }

    @GetMapping("/parse-dns")
    public String parseDNSFile() {
        String filePath = "dns_sample.txt";
        dnsRecordService.parseAndInsertDNSRecords(filePath);
        return "DNS records parsed and inserted successfully!";
    }
}
