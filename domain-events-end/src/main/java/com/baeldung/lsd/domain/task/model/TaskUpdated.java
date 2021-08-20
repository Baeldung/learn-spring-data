package com.baeldung.lsd.domain.task.model;

import com.baeldung.lsd.persistence.model.Task;

public class TaskUpdated {

    private Long taskId;

    public TaskUpdated(Task task) {
        this.taskId = task.getId();
    }

    public Long getTaskId() {
        return taskId;
    }

}