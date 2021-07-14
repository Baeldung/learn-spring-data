package com.baeldung.lsd.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.baeldung.lsd.persistence.model.Project;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    Iterable<Project> findByNameContaining(String name);

    @Query(value = "select p from Project as p where p.name='Project 3' and p.description='About Project 3'")
    List<Project> findWithNameAndDescription();

    @Query(value = "select p.name from Project as p where p.code='P1'")
    Optional<String> findNameByCode();

    @Query(nativeQuery = true, value = "select * from project limit 1")
    Project findSingleProject();
}
