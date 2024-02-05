package com.staroot.urlping.service;

import com.staroot.urlping.domain.DNSRecord;
import com.staroot.urlping.repository.DNSRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DNSRecordService {
    private static final Logger logger = LoggerFactory.getLogger(DNSRecordService.class);

    private final DNSRecordRepository dnsRecordRepository;

    @Autowired
    public DNSRecordService(DNSRecordRepository dnsRecordRepository) {
        this.dnsRecordRepository = dnsRecordRepository;
    }

    public void parseAndInsertDNSRecords(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                logger.info("DNS read line ::"+line);
                // Assuming A records are in the format: A     hostname     IP_address
                Pattern pattern = Pattern.compile("(\\S+)\\s+A\\s+(\\S+)");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String hostName = matcher.group(1);
                    String ipAddress = matcher.group(2);
                    logger.info("DNS hostName ::"+hostName);
                    logger.info("DNS ipAddress::"+ipAddress);

                    DNSRecord dnsRecord = new DNSRecord();
                    dnsRecord.setHostName(hostName);
                    dnsRecord.setIpAddress(ipAddress);

                    dnsRecordRepository.save(dnsRecord);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.info(e.toString());
        } catch (Exception e){
            logger.info(e.toString());
        }
    }
}