package com.baeldung.lsd.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    CampaignRepository campaignRepository;

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
    void givenTasksCreated_whenSearch_returnMatchingTask() {
        Campaign testCampaign = new Campaign("CTEST-2", "Task Test Campaign 2", "Description for campaign CTEST-2");
        entityManager.persist(testCampaign);
        Task newTask = new Task("First Test Task", "This is First Test Task", LocalDate.now(), testCampaign, TaskStatus.DONE);
        entityManager.persist(newTask);

        List<Task> matchingTasks = taskRepository.search("First Task");

        assertThat(matchingTasks).contains(newTask);
    }
    @Test
    void givenTasksCreated_whenFindAll_returnsAllTasksNotDone() {
        Campaign testCampaign = new Campaign("CTEST-2", "Task Test Campaign 2", "Description for campaign CTEST-2");
        entityManager.persist(testCampaign);
        Task doneTask = new Task("First Test Task", "First Test Task", LocalDate.now(), testCampaign, TaskStatus.DONE);
        entityManager.persist(doneTask);
        Task todoTask = new Task("Second Test Task", "Second Test Task", LocalDate.now(), testCampaign, TaskStatus.TO_DO);
        entityManager.persist(todoTask);

        List<Task> retrievedTask = taskRepository.findAll();

        boolean containsDone = retrievedTask.stream()
            .anyMatch(task -> task.getStatus()
                .equals(TaskStatus.DONE));
        assertThat(containsDone).isFalse();

        boolean containsTodo = retrievedTask.stream()
            .anyMatch(task -> task.getUuid()
                .equals(todoTask.getUuid()));
        assertThat(containsTodo).isTrue();
    }
}