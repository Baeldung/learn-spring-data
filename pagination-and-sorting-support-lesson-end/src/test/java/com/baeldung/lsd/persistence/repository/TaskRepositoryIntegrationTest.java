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
    void givenTasks_whenPaginatedPageOne_thenSuccess() {
        
        Pageable tasksFirstPage = PageRequest.of(0, 2);
        Page<Task> tasksPage1 = taskRepository.findAll(tasksFirstPage);
        
        assertThat(tasksPage1).isNotNull();
        assertThat(tasksPage1.getContent()).isNotNull().isNotEmpty().hasSize(2);
        
    }
    
    @Test
    void givenTasks_whenSortedByNameLastTask_thenSuccess() {
        
        Task task4 = entityManager.find(Task.class, 4L);

        Sort sortTaskByName = Sort.by(Direction.ASC, "name");
        Iterable<Task> tasksSorted = taskRepository.findAll(sortTaskByName);
        
        assertThat(tasksSorted).hasSize(4).endsWith(task4);
        
    }
    
    @Test
    void givenTasks_whenSortedByNameAndPaginatedLastPageLastTask_thenSuccess() {

        Task task1 = entityManager.find(Task.class, 1L);

        Pageable sortedTasksLastPage = PageRequest.of(1, 2, Sort.by(Direction.DESC, "name"));
        Page<Task> tasksPage2 = taskRepository.findAll(sortedTasksLastPage);
        
        assertThat(tasksPage2).isNotNull();
        assertThat(tasksPage2.getContent()).isNotNull().isNotEmpty().hasSize(2).endsWith(task1);
        
    }
    
}