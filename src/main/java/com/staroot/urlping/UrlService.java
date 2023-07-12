package com.staroot.urlping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Service
public class UrlService {
    private final UrlResponseRepository urlResponseRepository;

    @Autowired
    public UrlService(UrlResponseRepository urlResponseRepository) {
        this.urlResponseRepository = urlResponseRepository;
    }

    @Transactional
    public void saveUrlResponse(String url) throws IOException {
        URLConnection connection = new URL(url).openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        UrlResponse urlResponse = new UrlResponse();
        urlResponse.setUrl(url);
        urlResponse.setResponse(response.toString());

        urlResponseRepository.save(urlResponse);
    }
}

