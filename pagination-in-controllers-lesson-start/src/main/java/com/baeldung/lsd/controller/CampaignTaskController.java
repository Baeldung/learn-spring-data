package com.baeldung.lsd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.lsd.persistence.model.Task;
import com.baeldung.lsd.persistence.repository.TaskRepository;

@RestController
@RequestMapping("/campaigns/{id}/tasks")
public class CampaignTaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping
    public Page<Task> list(@PathVariable("id") long id) {
        Pageable pageable = Pageable.unpaged();
        return taskRepository.findByCampaignId(id, pageable);

    }
}
