package com.baeldung.lsd.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.lsd.persistence.model.Task;

public interface ITaskRepository extends CrudRepository<Task, Long> {

}
