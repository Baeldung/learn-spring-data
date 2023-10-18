package com.baeldung.lsd.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.baeldung.lsd.persistence.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long>, PagingAndSortingRepository<Task, Long> {

    List<Task> findAllByOrderByDueDateDesc();

    List<Task> findAllByOrderByDueDateDescAssigneeLastNameAsc();

    List<Task> findByNameContaining(String taskName, Sort sort);

}
