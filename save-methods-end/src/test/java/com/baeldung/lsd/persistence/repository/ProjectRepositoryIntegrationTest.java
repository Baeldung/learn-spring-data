package com.baeldung.lsd.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.lsd.persistence.model.Project;

@DataJpaTest
@Transactional
class ProjectRepositoryIntegrationTest {

    @Autowired
    ProjectRepository projectRepository;

    @Test
    void givenNewProject_whenSaved_thenSuccess() {
        Project newProject = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");
        assertNotNull(projectRepository.save(newProject));
    }

    @Test
    void givenProjectCreated_whenFindById_thenSuccess() {
        Project newProject = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");
        projectRepository.save(newProject);

        Optional<Project> retrievedProject = projectRepository.findById(newProject.getId());
        assertEquals(retrievedProject.get(), newProject);
    }
}