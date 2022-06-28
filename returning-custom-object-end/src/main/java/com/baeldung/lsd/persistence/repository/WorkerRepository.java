package com.baeldung.lsd.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.lsd.persistence.model.Worker;
import com.baeldung.lsd.persistence.projection.WorkerOpen;

public interface WorkerRepository extends CrudRepository<Worker, Long> {

    List<WorkerOpen> findByFirstName(String firstName);

}
