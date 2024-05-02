package com.example.api.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.api.domain.FailedEvent;

public interface FailedEventRepository extends JpaRepository<FailedEvent, Long>{
    
}
