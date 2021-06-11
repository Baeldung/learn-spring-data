package com.baeldung.lsd.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.lsd.DerivedQueryMethodsApp;
import com.baeldung.lsd.persistence.model.Project;

@ExtendWith(SpringExtension.class)
// avoid running the DerivedQueryMethodsApp#run method
@ContextConfiguration(classes = { DerivedQueryMethodsApp.class }, initializers = ConfigDataApplicationContextInitializer.class)
@Transactional
class ProjectRepositoryIntegrationTest {

    @Autowired
    IProjectRepository projectRepository;

    @Test
    void givenNewProject_whenSaved_thenSuccess() {
        Project newProject = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");
        assertNotNull(projectRepository.save(newProject));
    }

    @Test
    void givenProjectCreated_whenFindById_thenSuccess() {
        Project newProject = new Project("PTEST-2", "Test Project 2", "Description for project PTEST-2");
        projectRepository.save(newProject);

        Optional<Project> retrievedProject = projectRepository.findById(newProject.getId());
        assertEquals(retrievedProject.get(), newProject);
    }

    @Test
    void givenProjectCreated_whenFindByCodeEquals_thenSuccess() {
        Project newProject = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");
        projectRepository.save(newProject);

        Optional<Project> retrievedProject = projectRepository.findByCodeEquals("PTEST-1");

        assertTrue(retrievedProject.isPresent());
    }

    @Test
    void givenSingleProject_whenCount_returnsOne() {
        Project newProject = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");
        projectRepository.save(newProject);

        int count = projectRepository.countByName("Test Project 1");

        assertEquals(1,count);
    }
}