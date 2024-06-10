package com.baeldung.lsd.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

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
    void givenCustomQuery_whenSortedByDueDateDesc_thenSuccess() {
        List<Task> dbSortedTasks = taskRepository.allTasksSortedByDueDate();

        assertThat(dbSortedTasks).isNotNull()
            .isSortedAccordingTo(Comparator.comparing(Task::getDueDate, Comparator.nullsLast(Comparator.reverseOrder())));
    }

    @Test
    void givenCustomQueryWithSortParameter_whenSortedByDueDateDesc_thenSuccess() {
        Sort sortByDueDateDesc = Sort.by(Direction.DESC, "dueDate");

        List<Task> dbSortedTasks = taskRepository.allTasks(sortByDueDateDesc);

        assertThat(dbSortedTasks).isSortedAccordingTo(Comparator.comparing(Task::getDueDate, Comparator.nullsLast(Comparator.reverseOrder())));
    }

    @Test
    void givenNativeQueryWithOrderBy_whenCalled_thenSuccess() {
        List<Task> dbSortedTasks = taskRepository.allTasksSortedByDueDateDesc();

        assertThat(dbSortedTasks).isSortedAccordingTo(Comparator.comparing(Task::getDueDate, Comparator.nullsLast(Comparator.reverseOrder())));
    }

}