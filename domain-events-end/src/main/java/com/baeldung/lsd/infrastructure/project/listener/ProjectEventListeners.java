package com.baeldung.lsd.infrastructure.project.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.baeldung.lsd.domain.task.model.TaskUpdated;

@Component
public class ProjectEventListeners {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectEventListeners.class);

    @TransactionalEventListener(classes = TaskUpdated.class)
    public void taskUpdatedListener(TaskUpdated event) {
        LOG.info("Task(id={}) Updated - Invoke Project Application Service Method to Process the Event", event.getTaskId());

        // ...
    }
}