package com.microservices.datageneratorservice.repository;

import com.microservices.datageneratorservice.model.SmartPlug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SmartPlugRepository extends JpaRepository<SmartPlug, Long> {

    @Query("select a from SmartPlug a where a.device = (select b from Device b where b.uuid = :uuid)")
    Optional<SmartPlug> findByDeviceUUID(String uuid);

    @Query("select a from SmartPlug a where a.device = (select b from Device b where b.office = (select c from Office c where c.name = :officeName))")
    List<SmartPlug> findAllByOfficeName(String officeName);
}
