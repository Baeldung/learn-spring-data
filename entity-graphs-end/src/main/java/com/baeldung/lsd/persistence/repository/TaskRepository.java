package com.baeldung.lsd.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import com.baeldung.lsd.persistence.model.Task;
import com.baeldung.lsd.persistence.model.TaskStatus;

public interface TaskRepository extends CrudRepository<Task, Long> {
    
    @EntityGraph(attributePaths = {"assignee", "campaign"})
    List<Task> findByStatus(TaskStatus taskStatus);
}
