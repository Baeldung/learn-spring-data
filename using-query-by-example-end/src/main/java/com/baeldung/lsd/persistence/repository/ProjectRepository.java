package com.baeldung.lsd.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.lsd.persistence.model.Project;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    Iterable<Project> findByNameContaining(String name);
}
