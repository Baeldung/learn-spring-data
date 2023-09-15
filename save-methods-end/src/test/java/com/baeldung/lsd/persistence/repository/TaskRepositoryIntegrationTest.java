package com.baeldung.lsd.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.lsd.persistence.model.Project;
import com.baeldung.lsd.persistence.model.Task;

@DataJpaTest
@Transactional
class TaskRepositoryIntegrationTest {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Test
    void givenNewTask_whenSaved_thenSuccess() {
        Project newProject = projectRepository.save(new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1"));
        Task newTask = new Task("First Task", "First Task", LocalDate.now(), newProject);
        assertThat(taskRepository.save(newTask)).isNotNull();
    }

    @Test
    void givenTaskCreated_whenFindById_thenSuccess() {
        Project newProject = projectRepository.save(new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1"));
        Task newTask = new Task("First Task", "First Task", LocalDate.now(), newProject);
        taskRepository.save(newTask);

        Optional<Task> retrievedTask = taskRepository.findById(newTask.getId());
        assertThat(retrievedTask.get()).isEqualTo(newTask);
    }
}