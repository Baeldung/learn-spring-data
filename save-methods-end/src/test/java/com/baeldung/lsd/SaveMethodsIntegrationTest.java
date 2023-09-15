package com.baeldung.lsd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.lsd.persistence.model.Project;
import com.baeldung.lsd.persistence.model.Task;
import com.baeldung.lsd.persistence.repository.ProjectRepository;
import com.baeldung.lsd.persistence.repository.TaskRepository;

@DataJpaTest
@Transactional
class SaveMethodsIntegrationTest {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    TaskRepository taskRepository;

    @Test
    void givenRegularAppContext_whenSaveNewProject_thenGeneratedIdIsPopulated() {
        Project newProject = new Project("TEST-NEW1", "TEST-new Project", "TEST-new project description");
        assertThat(newProject.getId()).isNull();

        // save new entity
        newProject = projectRepository.save(newProject);

        assertThat(newProject.getId()).isPositive();
    }

    @Test
    void givenRegularAppContext_whenUpdateProjectAddingTasks_thenProjectIsUpdatedAndTaskGeneratedIdIsPopulated() {
        Project newProject = new Project("TEST-NEW1", "TEST-new Project", "TEST-new project description");
        newProject = projectRepository.save(newProject);
        newProject.setName("TEST-updated name");
        Set<Task> newProjectTasks = new HashSet<>();
        newProjectTasks.add(new Task("TEST-task name", "TEST-task description", LocalDate.of(2025, 1, 1), newProject));
        newProject.setTasks(newProjectTasks);

        // update entity (has id)
        newProject = projectRepository.save(newProject);

        Project persistedProject = projectRepository.findById(newProject.getId())
            .get();
        assertThat(persistedProject.getName()).isEqualTo(newProject.getName());
        assertThat(newProject.getTasks()
            .iterator()
            .next()
            .getId()).isPositive();
    }

    @Test
    void givenRegularAppContext_whenUpdateAndPersistSeveralProjects_thenEverythingExecutedCorrectly() {
        Project newProject = new Project("TEST-NEW1", "TEST-new Project", "TEST-new project description");
        Set<Task> originalTasks = new HashSet<>();
        originalTasks.add(new Task("TEST-TASK1-name", "TEST-TASK1-description", LocalDate.of(2025, 1, 1), newProject));
        originalTasks.add(new Task("TEST-TASK2-name", "TEST-TASK2-description", LocalDate.of(2025, 1, 1), newProject));
        newProject.setTasks(originalTasks);
        newProject = projectRepository.save(newProject);
        newProject.setName("TEST-updated again");
        Set<Task> differentTasks = newProject.getTasks();
        differentTasks.clear();
        differentTasks.add(new Task("TEST-different task", "TEST-different description", LocalDate.of(2025, 1, 1), newProject));
        newProject.setTasks(differentTasks);
        Project newProject2 = new Project("TEST-NEW2", "TEST-another Project", "TEST-another project description");
        Iterable<Project> severalProjects = Arrays.asList(newProject, newProject2);
        long totalCount1 = projectRepository.count();

        // update existing projects and save new ones
        severalProjects = projectRepository.saveAll(severalProjects);

        long totalCount2 = projectRepository.count();
        assertThat(totalCount2).isGreaterThan(totalCount1);
        assertThat(projectRepository.findById(newProject.getId())
            .map(Project::getTasks)
            .get()).hasSize(1);
    }

    @Test
    void givenRegularAppContext_whenSaveSeveralProjectsWithDataIntegrityViolation_thenEverythingIsRollbacked() {
        assertThrows(DataIntegrityViolationException.class, () -> {
            Project newProject1 = new Project("TEST-NEW1", "TEST-new Project", "TEST-new project description");
            newProject1 = projectRepository.save(newProject1);
            newProject1.setDescription("updated description");
            Project newProject2 = new Project("TEST-NEW1", "TEST-duplicate code!", "TEST-project with constraint violation");
            Iterable<Project> severalProjects2 = Arrays.asList(newProject1, newProject2);

            projectRepository.saveAll(severalProjects2);
        });
    }
}
