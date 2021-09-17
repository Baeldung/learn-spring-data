package com.baeldung.lsd.persistence.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import com.baeldung.lsd.persistence.model.Project;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    
    @EntityGraph(value = "project-with-tasks")
    Iterable<Project> findByNameContaining(String name);
}
