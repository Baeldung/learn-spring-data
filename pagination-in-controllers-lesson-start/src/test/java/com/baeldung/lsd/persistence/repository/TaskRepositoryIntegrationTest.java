package com.baeldung.lsd.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.baeldung.lsd.persistence.model.Campaign;
import com.baeldung.lsd.persistence.model.Task;

@DataJpaTest
class TaskRepositoryIntegrationTest {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void givenNewTask_whenSaved_thenSuccess() {
        Campaign testCampaign = new Campaign("TTEST-1", "Task Test Campaign 1", "Description for campaign TTEST-1");
        entityManager.persist(testCampaign);
        Task newTask = new Task("First Test Task", "First Test Task", LocalDate.now(), testCampaign);

        taskRepository.save(newTask);

        assertThat(entityManager.find(Task.class, newTask.getId())).isEqualTo(newTask);
    }

    @Test
    void givenTaskCreated_whenFindById_thenSuccess() {
        Campaign testCampaign = new Campaign("TTEST-2", "Task Test Campaign 2", "Description for campaign TTEST-2");
        entityManager.persist(testCampaign);

        Task newTask = new Task("First Test Task", "First Test Task", LocalDate.now(), testCampaign);
        entityManager.persist(newTask);

        Optional<Task> retrievedTask = taskRepository.findById(newTask.getId());
        assertThat(retrievedTask.get()).isEqualTo(entityManager.find(Task.class, retrievedTask.get()
            .getId()));
    }

    @Test
    void givenCampaignTasks_whenFindByCampaignId_thenSuccess() {
        Task task4 = entityManager.find(Task.class, 4L);

        Pageable pageable = PageRequest.of(0, 2);
        Page<Task> retrievedTask = taskRepository.findByCampaignId(2L, pageable);

        assertThat(retrievedTask.getContent()).contains(entityManager.find(Task.class, task4.getId()));
    }
}