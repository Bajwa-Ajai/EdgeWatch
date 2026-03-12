package com.edgewatch.consumer.repository;

import java.time.OffsetDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edgewatch.consumer.entity.TickerEntity;

public interface TickerRepository extends JpaRepository<TickerEntity, OffsetDateTime>{
    
}
