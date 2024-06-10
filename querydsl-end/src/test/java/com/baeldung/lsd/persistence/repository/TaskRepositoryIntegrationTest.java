package com.baeldung.lsd.persistence.repository;

import static com.baeldung.lsd.persistence.model.predicates.TaskPredicates.tasksByCampaignCode;
import static com.baeldung.lsd.persistence.model.predicates.TaskPredicates.tasksSortedByDueDateDesc;
import static com.baeldung.lsd.persistence.model.predicates.TaskPredicates.tasksWithNameContaining;
import static com.baeldung.lsd.persistence.model.predicates.TaskPredicates.tasksWithStatusEquals;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.baeldung.lsd.persistence.model.Campaign;
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
        Campaign testCampaign = new Campaign("CTEST-1", "Task Test Campaign 1", "Description for campaign CTEST-1");
        entityManager.persist(testCampaign);
        Task newTask = new Task("First Test Task", "First Test Task", LocalDate.now(), testCampaign);

        taskRepository.save(newTask);

        assertThat(entityManager.find(Task.class, newTask.getId())).isEqualTo(newTask);
    }

    @Test
    void givenTaskCreated_whenFindById_thenSuccess() {
        Campaign testCampaign = new Campaign("CTEST-2", "Task Test Campaign 2", "Description for campaign CTEST-2");
        entityManager.persist(testCampaign);

        Task newTask = new Task("First Test Task", "First Test Task", LocalDate.now(), testCampaign);
        entityManager.persist(newTask);

        Optional<Task> retrievedTask = taskRepository.findById(newTask.getId());
        assertThat(retrievedTask.get()).isEqualTo(entityManager.find(Task.class, retrievedTask.get()
            .getId()));
    }

    @Test
    void givenTasks_whentasksWithNameContaining_thenSuccess() {
        Iterable<Task> dbTasks = taskRepository.findAll(tasksWithNameContaining("2"));

        assertThat(dbTasks).allMatch(task -> task.getName()
            .contains("2"));
    }

    @Test
    void givenTasks_whenFindTasksWithStatusEquals_thenSuccess() {
        Iterable<Task> dbTasks = taskRepository.findAll(tasksWithStatusEquals(TaskStatus.IN_PROGRESS));

        assertThat(dbTasks).extracting("status", TaskStatus.class)
            .containsOnly(TaskStatus.IN_PROGRESS);
    }

    @Test
    void givenTasks_whenTasksSortedByDueDateDesc_thenSuccess() {
        Iterable<Task> dbTasks = taskRepository.findAll(tasksSortedByDueDateDesc());

        assertThat(StreamSupport.stream(dbTasks.spliterator(), false)
            .collect(Collectors.toList())).isSortedAccordingTo(Comparator.comparing(Task::getDueDate, Comparator.nullsLast(Comparator.reverseOrder())));
    }

    @Test
    void givenTasks_whenFindTasksByCampaignCode_thenSuccess() {
        Iterable<Task> dbTasks = taskRepository.findAll(tasksByCampaignCode("C2"));

        assertThat(dbTasks).extracting("campaign.code", String.class)
            .containsOnly("C2");
    }

    @Test
    void givenTasks_whenFindTasksWithStatusEqualsAndNameContaining_thenSuccess() {
        Iterable<Task> dbTasks = taskRepository.findAll(tasksWithNameContaining("Task").and(tasksWithStatusEquals(TaskStatus.IN_PROGRESS)));

        assertThat(dbTasks).allMatch(task -> task.getName()
            .contains("Task") && task.getStatus()
            .equals(TaskStatus.IN_PROGRESS));
    }
}