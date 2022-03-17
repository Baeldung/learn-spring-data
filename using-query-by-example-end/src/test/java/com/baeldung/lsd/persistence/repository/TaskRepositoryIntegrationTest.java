package com.baeldung.lsd.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import com.baeldung.lsd.persistence.model.Project;
import com.baeldung.lsd.persistence.model.Task;

@DataJpaTest
class TaskRepositoryIntegrationTest {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void givenNewTask_whenSaved_thenSuccess() {
        Project testProject = new Project("TTEST-1", "Task Test Project 1", "Description for project TTEST-1");
        entityManager.persist(testProject);
        Task newTask = new Task("First Test Task", "First Test Task", LocalDate.now(), testProject);

        taskRepository.save(newTask);

        assertThat(entityManager.find(Task.class, newTask.getId())).isEqualTo(newTask);
    }

    @Test
    void givenTaskCreated_whenFindById_thenSuccess() {
        Project testProject = new Project("TTEST-2", "Task Test Project 2", "Description for project TTEST-2");
        entityManager.persist(testProject);

        Task newTask = new Task("First Test Task", "First Test Task", LocalDate.now(), testProject);
        entityManager.persist(newTask);

        Optional<Task> retrievedTask = taskRepository.findById(newTask.getId());
        assertThat(retrievedTask.get()).isEqualTo(entityManager.find(Task.class, retrievedTask.get()
            .getId()));
    }

    @Test
    void givenTaskExampleCreatedWithMultipleMatchers_whenQueryByExample_thenSuccess() {
        Task taskProbe = new Task();
        taskProbe.setDescription("Description");
        taskProbe.setDueDate(LocalDate.of(2025, 3, 16));

        Example<Task> taskExample = Example.of(taskProbe, ExampleMatcher.matching()
            .withMatcher("description", matcher -> matcher.endsWith()
                .ignoreCase())
            .withMatcher("dueDate", ExampleMatcher.GenericPropertyMatcher::exact)
            .withIgnorePaths("uuid"));

        Optional<Task> found = taskRepository.findOne(taskExample);

        assertTrue(found.isPresent());
        assertThat(found.get()
            .getName()).isEqualTo("Task 3");
    }

}