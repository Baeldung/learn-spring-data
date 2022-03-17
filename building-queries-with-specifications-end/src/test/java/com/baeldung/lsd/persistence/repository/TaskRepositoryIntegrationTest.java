package com.baeldung.lsd.persistence.repository;

import static com.baeldung.lsd.persistence.model.specification.TaskSpecifications.isDueDateBetween;
import static com.baeldung.lsd.persistence.model.specification.TaskSpecifications.isTaskInProgress;
import static com.baeldung.lsd.persistence.model.specification.TaskSpecifications.isTaskUnassigned;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.baeldung.lsd.persistence.model.Project;
import com.baeldung.lsd.persistence.model.Task;
import com.baeldung.lsd.persistence.model.TaskStatus;

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
    void givenTasks_whenFindTasksInProgress_thenSuccess() {
        List<Task> dbTasks = taskRepository.findAll(isTaskInProgress());
        assertThat(dbTasks).allMatch(t -> t.getStatus() == TaskStatus.IN_PROGRESS);
    }

    @Test
    void givenTasks_whenFindTasksWithDueDateWithinRange_thenSuccess() {

        LocalDate fromDate = LocalDate.of(2025, 1, 1);
        LocalDate toDate = LocalDate.of(2025, 6, 30);

        List<Task> dbTasks = taskRepository.findAll(isDueDateBetween(fromDate, toDate));

        assertThat(dbTasks).allMatch(t -> t.getDueDate()
            .isBefore(toDate) && t.getDueDate()
            .isAfter(fromDate));
    }

    @Test
    void givenTasks_whenFindTasksUnassigned_thenSuccess() {
        List<Task> dbTasks = taskRepository.findAll(isTaskUnassigned());
        assertThat(dbTasks).allMatch(t -> t.getAssignee() == null);
    }
}