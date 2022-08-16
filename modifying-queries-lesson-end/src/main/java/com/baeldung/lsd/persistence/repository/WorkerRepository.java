package com.baeldung.lsd.persistence.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.baeldung.lsd.persistence.model.Worker;

public interface WorkerRepository extends CrudRepository<Worker, Long> {
    
    @Modifying
    @Query(value = "alter table Worker add column active int not null default 1", nativeQuery = true)
    void addActiveColumn();

}
