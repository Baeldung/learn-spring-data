package com.baeldung.lsd.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.lsd.DeepDiveDerivedQueryMethodsApp;
import com.baeldung.lsd.persistence.model.Campaign;
import com.baeldung.lsd.persistence.model.Task;

@ExtendWith(SpringExtension.class)
// avoid running the DerivedQueryMethodsApp#run method
@ContextConfiguration(classes = { DeepDiveDerivedQueryMethodsApp.class }, initializers = ConfigDataApplicationContextInitializer.class)
@Transactional
class TaskRepositoryIntegrationTest {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    CampaignRepository campaignRepository;

    @Autowired
    WorkerRepository workerRepository;

    @Test
    void givenNewTask_whenSaved_thenSuccess() {
        Campaign testCampaign = new Campaign("CTEST-1", "Task Test Campaign 1", "Description for campaign CTEST-1");
        campaignRepository.save(testCampaign);

        Task newTask = new Task("First Test Task", "First Test Task", LocalDate.now(), testCampaign);

        assertThat(taskRepository.save(newTask)).isNotNull();
    }

    @Test
    void givenTaskCreated_whenFindById_thenSuccess() {
        Campaign testCampaign = new Campaign("CTEST-2", "Task Test Campaign 1", "Description for campaign CTEST-2");
        campaignRepository.save(testCampaign);

        Task newTask = new Task("First Test Task", "First Test Task", LocalDate.now(), testCampaign);
        taskRepository.save(newTask);

        Optional<Task> retrievedTask = taskRepository.findById(newTask.getId());
        assertThat(retrievedTask.get()).isEqualTo(newTask);
    }
}