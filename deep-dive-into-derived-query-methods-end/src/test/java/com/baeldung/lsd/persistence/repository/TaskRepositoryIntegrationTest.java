package com.baeldung.lsd.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
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
import com.baeldung.lsd.persistence.model.TaskStatus;
import com.baeldung.lsd.persistence.model.Worker;

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

    @Test
    void givenTaskCreated_whenFindByDueDateLessThan_thenHasValue() {
        Campaign testCampaign = new Campaign("CTEST-2", "Task Test Campaign 1", "Description for campaign CTEST-2");
        campaignRepository.save(testCampaign);

        Task newTask = new Task("First Test Task", "First Test Task", LocalDate.now(), testCampaign);
        taskRepository.save(newTask);

        List<Task> tasks = taskRepository.findByDueDateLessThan(LocalDate.now()
            .plusDays(1));
        assertThat(tasks.size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    void givenTaskCreated_whenFindByDueDateAfter_thenHasValue() {
        Campaign testCampaign = new Campaign("CTEST-2", "Task Test Campaign 1", "Description for campaign CTEST-2");
        campaignRepository.save(testCampaign);

        Task newTask = new Task("First Test Task", "First Test Task", LocalDate.now(), testCampaign);
        taskRepository.save(newTask);

        List<Task> tasks = taskRepository.findByDueDateAfter(LocalDate.now()
            .minusDays(1));
        assertThat(tasks.size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    void givenTaskCreated_whenFindByDueDateBeforeAndStatusEquals_thenHasValue() {
        Campaign testCampaign = new Campaign("CTEST-2", "Task Test Campaign 1", "Description for campaign CTEST-2");
        campaignRepository.save(testCampaign);

        Task newTask = new Task("First Test Task", "First Test Task", LocalDate.now(), testCampaign, TaskStatus.TO_DO);
        taskRepository.save(newTask);

        List<Task> tasks = taskRepository.findByDueDateBeforeAndStatusEquals(LocalDate.now()
            .plusDays(1), TaskStatus.TO_DO);
        assertThat(tasks.size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    void givenTaskCreated_whenFindByAssigneeFirstName_thenHasValue() {
        Campaign testCampaign = new Campaign("CTEST-2", "Task Test Campaign 1", "Description for campaign CTEST-2");
        campaignRepository.save(testCampaign);

        Worker worker = new Worker("Ben@test.com", "Ben", "Doe");
        workerRepository.save(worker);

        Task newTask = new Task("First Test Task", "First Test Task", LocalDate.now(), testCampaign);
        newTask.setAssignee(worker);
        taskRepository.save(newTask);

        List<Task> tasks = taskRepository.findByAssigneeFirstName("Ben");

        assertThat(tasks.size()).isEqualTo(1);
    }

    @Test
    void given3TaskCreated_whenFindFirst2By_thenResultsSizeEqualTo2() {
        Campaign testCampaign = new Campaign("CTEST-2", "Task Test Campaign 1", "Description for campaign CTEST-2");
        campaignRepository.save(testCampaign);

        Task firstTask = new Task("First Test Task", "First Test Task", LocalDate.now(), testCampaign);
        taskRepository.save(firstTask);

        Task secondTask = new Task("Second Test Task", "Second Test Task", LocalDate.now(), testCampaign);
        taskRepository.save(secondTask);

        Task thirdTask = new Task("Third Test Task", "Third Test Task", LocalDate.now(), testCampaign);
        taskRepository.save(thirdTask);

        List<Task> tasks = taskRepository.findFirst2By();

        assertThat(tasks.size()).isEqualTo(2);
    }

    @Test
    void given2TaskCreated_whenFindFirstBy_thenSingleResult() {
        Campaign testCampaign = new Campaign("CTEST-2", "Task Test Campaign 1", "Description for campaign CTEST-2");
        campaignRepository.save(testCampaign);

        Task firstTask = new Task("First Test Task", "First Test Task", LocalDate.now(), testCampaign);
        taskRepository.save(firstTask);

        Task secondTask = new Task("Second Test Task", "Second Test Task", LocalDate.now(), testCampaign);
        taskRepository.save(secondTask);

        Task task = taskRepository.findFirstBy();

        assertThat(task).isNotNull();
    }
}