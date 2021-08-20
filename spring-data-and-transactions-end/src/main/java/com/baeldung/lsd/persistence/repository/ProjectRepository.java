package com.baeldung.lsd.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.lsd.persistence.model.Project;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    
    @Transactional(readOnly = true)
    Iterable<Project> findByNameContaining(String name);
}
