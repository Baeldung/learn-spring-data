package com.baeldung.lsd.persistence.repository;

import com.baeldung.lsd.persistence.model.Task;
import org.springframework.data.repository.CrudRepository;

public interface ITaskRepository extends CrudRepository<Task, Long> {
}
