package com.baeldung.lsd.persistence.repository;

import com.baeldung.lsd.persistence.model.Worker;
import org.springframework.data.repository.CrudRepository;

public interface WorkerRepository extends CrudRepository<Worker, Long> {

}
