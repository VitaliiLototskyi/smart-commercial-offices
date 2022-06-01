package com.microservices.analyzerservice.repository.analysis;

import com.microservices.analyzerservice.model.analysis.VacuumCleanerHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacuumCleanerHistoryRepo extends JpaRepository<VacuumCleanerHistory, Long> {
}
