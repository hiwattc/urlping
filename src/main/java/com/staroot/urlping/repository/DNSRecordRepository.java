package com.staroot.urlping.repository;

import com.staroot.urlping.domain.DNSRecord;
import org.springframework.data.repository.CrudRepository;

public interface DNSRecordRepository extends CrudRepository<DNSRecord, String> {

}
