package com.baeldung.lsd.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import javax.transaction.Transactional;

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
public class ProjectRepositoryIntegrationTest {

    @Autowired
    IProjectRepository projectRepository;

    @Test
    public void givenNewProject_whenSaved_thenSuccess() {
        Project newProject = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");
        assertNotNull(projectRepository.save(newProject));
    }

    @Test
    public void givenProjectCreated_whenFindById_thenSuccess() {
        Project newProject = new Project("PTEST-2", "Test Project 2", "Description for project PTEST-2");
        projectRepository.save(newProject);

        Optional<Project> retrievedProject = projectRepository.findById(newProject.getId());
        assertEquals(retrievedProject.get(), newProject);
    }
}