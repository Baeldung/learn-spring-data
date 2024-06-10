package com.baeldung.lsd.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.lsd.persistence.model.Campaign;
import com.baeldung.lsd.persistence.model.Task;

@SpringBootTest
@Transactional
class TaskRepositoryIntegrationTest {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    CampaignRepository campaignRepository;

    @Test
    void givenNewTask_whenSaved_thenSuccess() {
        Campaign newCampaign = campaignRepository.save(new Campaign("CTEST-1", "Test Campaign 1", "Description for campaign CTEST-1"));
        Task newTask = new Task("First Task", "First Task", LocalDate.now(), newCampaign);
        assertThat(taskRepository.save(newTask)).isNotNull();
    }

    @Test
    void givenTaskCreated_whenFindById_thenSuccess() {
        Campaign newCampaign = campaignRepository.save(new Campaign("CTEST-1", "Test Campaign 1", "Description for campaign CTEST-1"));
        Task newTask = new Task("First Task", "First Task", LocalDate.now(), newCampaign);
        taskRepository.save(newTask);

        Optional<Task> retrievedTask = taskRepository.findById(newTask.getId());
        assertThat(retrievedTask.get()).isEqualTo(newTask);
    }
}