package com.baeldung.lsd.persistence.repository;

import com.baeldung.lsd.persistence.model.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {
}
