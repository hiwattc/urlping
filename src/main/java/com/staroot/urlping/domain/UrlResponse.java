package com.staroot.urlping.domain;


import jakarta.persistence.*;

@Entity
public class UrlResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    //@Column(length = 2000)
    @Lob
    @Column(columnDefinition = "CLOB")
    private String response;

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getResponse() {
        return response;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "UrlResponse{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", response='" + response + '\'' +
                '}';
    }

}
