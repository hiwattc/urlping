package com.staroot.urlping.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DNSRecord {

    @Id
    private String hostName;
    private String ipAddress;
    private String lastlog;
    private String moddt;
    @PrePersist
    protected void onCreate() {
        updateModDt();
    }

    @PreUpdate
    protected void onUpdate() {
        updateModDt();
    }
    private void updateModDt() {
        // 현재 일시를 YYYY-MM-DD HH:MI:SS 형식으로 포맷
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        // moddt 필드에 할당
        moddt = formattedDateTime;
    }
}