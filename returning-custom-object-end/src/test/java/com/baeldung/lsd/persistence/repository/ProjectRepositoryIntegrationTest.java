package com.baeldung.lsd.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.baeldung.lsd.persistence.model.Project;
import com.baeldung.lsd.persistence.projection.ProjectClass;
import com.baeldung.lsd.persistence.projection.ProjectClosed;
import com.baeldung.lsd.persistence.projection.ProjectNative;

@DataJpaTest
public class ProjectRepositoryIntegrationTest {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void givenNewProject_whenSave_thenSuccess() {
        Project newProject = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");

        Project insertedProject = projectRepository.save(newProject);

        assertThat(entityManager.find(Project.class, insertedProject.getId())).isEqualTo(newProject);
    }

    @Test
    public void givenProjectCreated_whenUpdate_thenSuccess() {
        Project newProject = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");
        entityManager.persist(newProject);

        String newName = "New Project 001";
        newProject.setName(newName);
        projectRepository.save(newProject);

        assertThat(entityManager.find(Project.class, newProject.getId())
            .getName()).isEqualTo(newName);
    }

    @Test
    public void givenProjectCreated_whenFindById_thenSuccess() {
        Project newProject = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");
        entityManager.persist(newProject);

        Optional<Project> retrievedProject = projectRepository.findById(newProject.getId());
        assertThat(retrievedProject).contains(newProject);
    }

    @Test
    public void givenProjectCreated_whenFindByNameContaining_thenSuccess() {
        Project newProject1 = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");
        Project newProject2 = new Project("PTEST-2", "Test Project 2", "Description for project PTEST-2");
        entityManager.persist(newProject1);
        entityManager.persist(newProject2);

        Iterable<Project> projects = projectRepository.findByNameContaining("Test");
        assertThat(projects).contains(newProject1, newProject2);
    }

    @Test
    public void givenProjectCreated_whenDelete_thenSuccess() {
        Project newProject = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");
        entityManager.persist(newProject);

        projectRepository.delete(newProject);

        assertThat(entityManager.find(Project.class, newProject.getId())).isNull();
    }

    @Test
    public void givenInitialDBState_whenFindClosed_thenSuccess() {
        List<ProjectClosed> projects = projectRepository.findClosedByNameContaining("Project 1");
        assertThat(projects).hasSize(1);
        ProjectClosed project = projects.get(0);
        assertThat(project.getName()).isEqualTo("Project 1");

    }

    @Test
    public void givenIntialDBState_whenFindClassBased_thenSuccess() {
        List<ProjectClass> projects = projectRepository.findClassByNameContaining("Project");

        assertThat(projects).hasSize(3);

        projects.forEach(p -> {
            assertTrue(p.getName()
                .contains("Project"));
        });

    }

    @Test
    public void givenIntialDBState_whenGetStatistics_thenSuccess() {
        List<ProjectNative> projects = projectRepository.getProjectStatistics();
        projects.sort(Comparator.comparing(ProjectNative::getId));

        ProjectNative project1 = projects.get(0);
        ProjectNative project2 = projects.get(1);
        ProjectNative project3 = projects.get(2);

        assertThat(project1.getName()).isEqualTo("Project 1");
        assertThat(project1.getTaskCount()).isEqualTo(3);
        assertThat(project2.getName()).isEqualTo("Project 2");
        assertThat(project2.getTaskCount()).isOne();
        assertThat(project3.getName()).isEqualTo("Project 3");
        assertThat(project3.getTaskCount()).isZero();
    }

}