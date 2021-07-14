package com.baeldung.lsd.persistence.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.baeldung.lsd.persistence.model.Project;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    Iterable<Project> findByNameContaining(String name);

    @Query("select p from Project p where p.name='Project 3' and p.description='About Project 3'")
    List<Project> findWithNameAndDescription();
}
