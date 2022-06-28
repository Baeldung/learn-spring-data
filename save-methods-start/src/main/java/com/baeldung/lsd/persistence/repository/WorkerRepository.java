package com.baeldung.lsd.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.lsd.persistence.model.Worker;

public interface WorkerRepository extends CrudRepository<Worker, Long> {

}
