package com.baeldung.lsd.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.lsd.persistence.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Iterable<Project> findByNameContaining(String name);
}
