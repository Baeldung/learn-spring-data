package com.baeldung.lsd.persistence.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.baeldung.lsd.persistence.model.Task;

public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {
}
