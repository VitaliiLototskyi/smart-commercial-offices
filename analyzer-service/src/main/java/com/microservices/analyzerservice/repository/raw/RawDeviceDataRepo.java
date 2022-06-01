package com.microservices.analyzerservice.repository.raw;

import com.microservices.analyzerservice.model.raw.RawDeviceDataHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RawDeviceDataRepo extends MongoRepository<RawDeviceDataHistory, String> {

    List<RawDeviceDataHistory> findAllByDateReceivedAfter(LocalDateTime dateReceivedAfter);
}
