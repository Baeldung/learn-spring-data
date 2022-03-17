package com.baeldung.lsd;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.lsd.persistence.model.Project;
import com.baeldung.lsd.persistence.repository.ProjectRepository;
import com.baeldung.lsd.persistence.repository.TaskRepository;

@SpringBootTest
@Transactional
class DeleteMethodsIntegrationTest {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    TaskRepository taskRepository;

    @Test
    void givenRegularAppContext_whenDeleteExistingProjectUsingReference_thenTotalProjectCountIsReduced() {
        Project newProject = new Project("TEST-NEW1", "TEST-new Project", "TEST-new project description");
        newProject = projectRepository.save(newProject);
        long totalCount1 = projectRepository.count();

        // delete using reference
        projectRepository.delete(newProject);

        long totalCount2 = projectRepository.count();

        assertThat(totalCount2).isLessThan(totalCount1);
    }

    @Test
    void givenRegularAppContext_whenDeleteProjectUsingId_thenTotalProjectCountIsReduced() {
        Project newProject = new Project("TEST-NEW1", "TEST-new Project", "TEST-new project description");
        newProject = projectRepository.save(newProject);

        long totalCount1 = projectRepository.count();

        // delete using id
        projectRepository.deleteById(newProject.getId());

        long totalCount2 = projectRepository.count();

        assertThat(totalCount2).isLessThan(totalCount1);
    }

    @Test
    void givenRegularAppContext_whenDeleteSeveralProjects_thenTotalProjectCountIsReduced() {
        Project newProject = new Project("TEST-NEW1", "TEST-new Project1", "TEST-new project1 description");
        newProject = projectRepository.save(newProject);
        Project newProject2 = new Project("TEST-NEW2", "TEST-new Project2", "TEST-new project2 description");
        newProject2 = projectRepository.save(newProject2);
        Iterable<Project> projectsToDelete = projectRepository.findAllById(List.of(newProject.getId(), newProject2.getId()));
        long totalCount1 = projectRepository.count();

        // delete several projects
        projectRepository.deleteAll(projectsToDelete);

        long totalCount2 = projectRepository.count();

        assertThat(totalCount2).isLessThan(totalCount1);
    }

    @Test
    void givenRegularAppContext_whenDeleteByNameNonMatchingProject_thenOutputCountIsZero() {
        // delete using custom query and with count
        Long deleteCount = projectRepository.deleteByNameContaining("Non-matching name");

        assertThat(deleteCount).isZero();
    }

    @Test
    void givenRegularAppContext_whenDeleteByNameMatchingEverything_thenTotalProjectAndTaskCountIsZero() {
        // delete using custom query
        projectRepository.removeByNameContaining("Project");

        long finalProjectCount = projectRepository.count();
        long finaltaskCount = taskRepository.count();
        assertThat(finalProjectCount).isZero();
        assertThat(finaltaskCount).isZero();
    }
}
