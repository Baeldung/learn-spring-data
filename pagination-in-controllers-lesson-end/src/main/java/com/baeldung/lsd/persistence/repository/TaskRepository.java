package com.baeldung.lsd.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.baeldung.lsd.persistence.model.Task;

public interface TaskRepository extends PagingAndSortingRepository<Task, Long>, JpaRepository<Task, Long> {
    Page<Task> findByProjectId(long id, Pageable pageable);
}
