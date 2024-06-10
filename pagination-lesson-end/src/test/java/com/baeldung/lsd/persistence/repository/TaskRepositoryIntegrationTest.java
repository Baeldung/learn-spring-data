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
import org.springframework.data.domain.Slice;

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
    void givenSlicedTasksByName_whenHasPrevHasAfter_thenSuccess() {
        Task taskOne = entityManager.find(Task.class, 1L);
        Task taskTwo = entityManager.find(Task.class, 2L);

        Pageable twoTasksPagination = PageRequest.of(0, 2);

        Slice<Task> sliceOneTasksByName = taskRepository.findByNameLike("Task%", twoTasksPagination);

        assertThat(sliceOneTasksByName.hasPrevious()).isFalse();
        assertThat(sliceOneTasksByName.hasNext()).isTrue();
        assertThat(sliceOneTasksByName.getContent()).isNotNull()
            .isNotEmpty()
            .endsWith(taskOne, taskTwo);
    }

    @Test
    void givenTasks_whenFindAllByStatusPageOne_thenSucess() {
        Task taskOne = entityManager.find(Task.class, 1L);
        Task taskTwo = entityManager.find(Task.class, 2L);

        Pageable twoTasksPagination = PageRequest.of(0, 2);

        Page<Task> twoTasksAPage = taskRepository.findByStatus(TaskStatus.TO_DO, twoTasksPagination);

        assertThat(twoTasksAPage.getTotalElements()).isEqualTo(4L);
        assertThat(twoTasksAPage.getTotalPages()).isEqualTo(2);
        assertThat(twoTasksAPage.getContent()).isNotNull()
            .isNotEmpty()
            .endsWith(taskOne, taskTwo);
    }

    @Test
    void givenCustomeQuery_whenFindAllTasksPaginated_thenSuccess() {
        Task taskThree = entityManager.find(Task.class, 3L);
        Task taskFour = entityManager.find(Task.class, 4L);

        Pageable pageTwo = PageRequest.of(1, 2);

        Page<Task> allTasksByNamePageTwo = taskRepository.allTasksByName("Task%", pageTwo);

        assertThat(allTasksByNamePageTwo.getTotalElements()).isEqualTo(4L);
        assertThat(allTasksByNamePageTwo.getTotalPages()).isEqualTo(2);
        assertThat(allTasksByNamePageTwo.getContent()).isNotNull()
            .isNotEmpty()
            .endsWith(taskThree, taskFour);
    }
}