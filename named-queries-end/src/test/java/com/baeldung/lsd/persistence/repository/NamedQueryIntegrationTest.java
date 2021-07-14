package com.baeldung.lsd.persistence.repository;

import com.baeldung.lsd.persistence.model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class NamedQueryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void givenNewProject_whenFindWithIdLessThan_thenGetExpectResult() {
        Project newProject = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");

        entityManager.persist(newProject);
        long boundary = newProject.getId() + 1;
        List<Project> result = projectRepository.findProjectsWithIdLessThan(boundary);

        assertThat(result).isNotEmpty()
            .allMatch(project -> project.getId() < boundary);
    }

    @Test
    void givenProjects_whenFindWithDescPrefix_thenGetExpectResult() {
        Project newProject = new Project("PTEST-1", "Test Project 1", "Special: Description for project PTEST-1");
        Project newProject2 = new Project("PTEST-2", "Test Project 2", "------: Description for project PTEST-1");

        entityManager.persist(newProject);
        entityManager.persist(newProject2);

        List<Project> result = projectRepository.findProjectsWithDescriptionPrefix("Special:");
        assertThat(result).contains(newProject).doesNotContain(newProject2);
    }

    @Test
    void givenProjects_whenFindDescShorterThan_thenGetExpectResult() {
        Project newProject = new Project("PTEST-1", "Test Project 1", "1234");
        Project newProject2 = new Project("PTEST-2", "Test Project 2", "123");

        entityManager.persist(newProject);
        entityManager.persist(newProject2);
        List<Project> result = projectRepository.findProjectsWithDescriptionShorterThan(4);
        assertThat(result).contains(newProject2).doesNotContain(newProject);
    }

    @Test
    void givenProjects_whenUpdateDescById_thenGetExpectResult() {
        String updatedDescription = "updated description";
        Project newProject = new Project("PTEST-1", "Test Project 1", "original description");
        entityManager.persist(newProject);
        int result = projectRepository.updateProjectDescriptionById(newProject.getId(), updatedDescription);
        assertThat(result).isEqualTo(1);
        assertThat(entityManager.find(Project.class, newProject.getId())
            .getDescription()).isEqualTo(updatedDescription);
    }
}
