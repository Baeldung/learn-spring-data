package com.baeldung.lsd.persistence.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.baeldung.lsd.persistence.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {

    @Modifying(clearAutomatically = true)
    @Query("delete Task t where t.status=com.baeldung.lsd.persistence.model.TaskStatus.DONE")
    int deleteCompletedTasks();

}
