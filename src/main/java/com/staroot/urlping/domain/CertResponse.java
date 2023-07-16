package com.staroot.urlping.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class CertResponse {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime logDt;
    @Id
    private String url;
    private String sysMgr;
    private String appMgr;
    private String endDt;
    private String rootCaEndDt;
    private String chain1EndDt;
    private String chain2EndDt;

    public LocalDateTime getLogDt() {
        return logDt;
    }

    public String getUrl() {
        return url;
    }

    public String getSysMgr() {
        return sysMgr;
    }

    public String getAppMgr() {
        return appMgr;
    }

    public String getEndDt() {
        return endDt;
    }

    public String getRootCaEndDt() {
        return rootCaEndDt;
    }

    public String getChain1EndDt() {
        return chain1EndDt;
    }

    public String getChain2EndDt() {
        return chain2EndDt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogDt(LocalDateTime logDt) {
        this.logDt = logDt;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSysMgr(String sysMgr) {
        this.sysMgr = sysMgr;
    }

    public void setAppMgr(String appMgr) {
        this.appMgr = appMgr;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public void setRootCaEndDt(String rootCaEndDt) {
        this.rootCaEndDt = rootCaEndDt;
    }

    public void setChain1EndDt(String chain1EndDt) {
        this.chain1EndDt = chain1EndDt;
    }

    public void setChain2EndDt(String chain2EndDt) {
        this.chain2EndDt = chain2EndDt;
    }

    @Override
    public String toString() {
        return "CertResponse{" +
                "id=" + id +
                ", logDt=" + logDt +
                ", url='" + url + '\'' +
                ", sysMgr='" + sysMgr + '\'' +
                ", appMgr='" + appMgr + '\'' +
                ", endDt='" + endDt + '\'' +
                ", rootCaEndDt='" + rootCaEndDt + '\'' +
                ", chain1EndDt='" + chain1EndDt + '\'' +
                ", chain2EndDt='" + chain2EndDt + '\'' +
                '}';
    }
}
