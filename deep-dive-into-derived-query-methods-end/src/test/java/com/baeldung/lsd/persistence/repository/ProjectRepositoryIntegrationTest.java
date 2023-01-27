package com.baeldung.lsd.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.lsd.DeepDiveDerivedQueryMethodsApp;
import com.baeldung.lsd.persistence.model.Project;

@ExtendWith(SpringExtension.class)
// avoid running the DerivedQueryMethodsApp#run method
@ContextConfiguration(classes = { DeepDiveDerivedQueryMethodsApp.class }, initializers = ConfigDataApplicationContextInitializer.class)
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
        Project newProject = new Project("PTEST-2", "Test Project 2", "Description for project PTEST-2");
        projectRepository.save(newProject);

        Optional<Project> retrievedProject = projectRepository.findById(newProject.getId());
        assertEquals(retrievedProject.get(), newProject);
    }

    @Test
    void givenProjectCreated_whenFindByNameContaining_thenSuccess() {
        Project newProject = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");
        projectRepository.save(newProject);

        Iterable<Project> projects = projectRepository.findByNameContaining("Project");

        assertTrue(projects.iterator()
            .hasNext());
    }

    @Test
    void givenProjectCreated_whenFindByName_thenSuccess() {
        Project newProject = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");
        projectRepository.save(newProject);

        Iterable<Project> projects = projectRepository.findByName("Test Project 1");

        assertTrue(projects.iterator()
            .hasNext());
    }

    @Test
    void givenProjectCreated_whenFindByNameIsNot_thenHasValue() {
        Project newProject = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");
        projectRepository.save(newProject);

        Iterable<Project> projects = projectRepository.findByNameIsNot("my project");

        assertTrue(projects.iterator()
            .hasNext());
    }

    @Test
    void givenProjectCreated_whenFindByNameStartingWith_thenHasValue() {
        Project newProject = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");
        projectRepository.save(newProject);

        Iterable<Project> projects = projectRepository.findByNameStartingWith("Test");

        assertTrue(projects.iterator()
            .hasNext());
    }

    @Test
    void givenProjectCreated_whenFindByNameLike_thenHasValue() {
        Project newProject = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");
        projectRepository.save(newProject);

        Iterable<Project> projects = projectRepository.findByNameLike("%Pro%");

        assertTrue(projects.iterator()
            .hasNext());
    }

}