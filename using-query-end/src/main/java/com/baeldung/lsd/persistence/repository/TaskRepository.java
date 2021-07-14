package com.baeldung.lsd.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.baeldung.lsd.persistence.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {
    @Query("select count(*), year(t.dueDate) from Task t group by year(t.dueDate)")
    List<List<Integer>> countByDueYear();
}
