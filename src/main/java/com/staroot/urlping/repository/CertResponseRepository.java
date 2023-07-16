package com.staroot.urlping.repository;

import com.staroot.urlping.domain.CertResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CertResponseRepository extends JpaRepository<CertResponse, Long> {

    @Query("SELECT url FROM CertResponse WHERE url like '%naver%' ")
    List<CertResponse> findAllForExpireChk();

    //@Query("SELECT url FROM CertResponse cr WHERE cr.endDt >= CURRENT_DATE AND cr.endDt <= :targetDate")
    @Query("SELECT cr FROM CertResponse cr WHERE cr.endDt <= :targetDate")
    List<CertResponse> findByEndDtWithin(String targetDate);
}


