package com.baeldung.lsd.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.baeldung.lsd.persistence.model.Project;

@DataJpaTest
class ProjectRepositoryIntegrationTest {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void givenNewProject_whenSave_thenSuccess() {
        Project newProject = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");

        Project insertedProject = projectRepository.save(newProject);

        assertThat(entityManager.find(Project.class, insertedProject.getId())).isEqualTo(newProject);
    }

    @Test
    void givenProjectCreated_whenUpdate_thenSuccess() {
        Project newProject = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");
        entityManager.persist(newProject);

        String newName = "New Project 001";
        newProject.setName(newName);
        projectRepository.save(newProject);

        assertThat(entityManager.find(Project.class, newProject.getId())
            .getName()).isEqualTo(newName);
    }

    @Test
    void givenProjectCreated_whenFindById_thenSuccess() {
        Project newProject = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");
        entityManager.persist(newProject);

        Optional<Project> retrievedProject = projectRepository.findById(newProject.getId());
        assertThat(retrievedProject).contains(newProject);
    }

    @Test
    void givenProjectCreated_whenFindByNameContaining_thenSuccess() {
        Project newProject1 = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");
        Project newProject2 = new Project("PTEST-2", "Test Project 2", "Description for project PTEST-2");
        entityManager.persist(newProject1);
        entityManager.persist(newProject2);

        Iterable<Project> projects = projectRepository.findByNameContaining("Test");
        assertThat(projects).contains(newProject1, newProject2);
    }

    @Test
    void givenProjectCreated_whenDelete_thenSuccess() {
        Project newProject = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");
        entityManager.persist(newProject);

        projectRepository.delete(newProject);

        assertThat(entityManager.find(Project.class, newProject.getId())).isNull();
    }

}