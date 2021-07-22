package com.baeldung.lsd.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.baeldung.lsd.persistence.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {

    @Query("select t from Task t order by t.dueDate desc")
    List<Task> allTasksSortedByDueDate();

    @Query("select t from Task t")
    List<Task> allTasks(Sort sort);

    @Query("select t from Task t order by t.dueDate desc")
    List<Task> allTasksSortedByDueDate(Sort sort);
    
    @Query(value = "select * from Task t order by t.due_date desc", nativeQuery = true)
    List<Task> allTasksSortedByDueDateDesc();

}
