package com.baeldung.lsd.persistence.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.lsd.persistence.model.Task;
import com.baeldung.lsd.persistence.model.TaskStatus;

public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findByDueDateLessThan(LocalDate dueDate);

    List<Task> findByDueDateGreaterThan(LocalDate dueDate);

    List<Task> findByDueDateLessThanEqual(LocalDate dueDate);

    List<Task> findByDueDateGreaterThanEqual(LocalDate dueDate);

    List<Task> findByDueDateAfter(LocalDate dueDate);

    List<Task> findByDueDateBefore(LocalDate duaDate);

    List<Task> findByDueDateBeforeAndStatusEquals(LocalDate dueDate, TaskStatus status);

    List<Task> findByAssigneeFirstName(String name);

    List<Task> findFirst2By();

    Task findFirstBy();
}
