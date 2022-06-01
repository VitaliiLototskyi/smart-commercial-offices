package com.microservices.dataaggregationservice.repository;

import com.microservices.dataaggregationservice.model.RawDeviceDataHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RawDeviceDataHistoryRepo extends MongoRepository<RawDeviceDataHistory, String> {
}
