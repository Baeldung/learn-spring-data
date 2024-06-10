package com.baeldung.lsd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
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
    public Page<Task> list(@PathVariable("id") long id, @PageableDefault(page = 0, size = 2) @SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return taskRepository.findByCampaignId(id, pageable);
    }
}
