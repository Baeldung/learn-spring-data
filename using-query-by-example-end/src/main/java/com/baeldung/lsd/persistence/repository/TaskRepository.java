package com.baeldung.lsd.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.lsd.persistence.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
