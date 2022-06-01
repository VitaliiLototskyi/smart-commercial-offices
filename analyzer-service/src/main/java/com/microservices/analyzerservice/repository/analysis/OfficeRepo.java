package com.microservices.analyzerservice.repository.analysis;

import com.microservices.analyzerservice.model.analysis.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfficeRepo extends JpaRepository<Office, Long> {

    Optional<Office> findByName(String name);
}
