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

import com.baeldung.lsd.persistence.model.Project;
import com.baeldung.lsd.persistence.model.Task;
import com.baeldung.lsd.persistence.model.TaskStatus;

@DataJpaTest
class TaskRepositoryIntegrationTest {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void givenNewTask_whenSaved_thenSuccess() {
        Project testProject = new Project("TTEST-1", "Task Test Project 1", "Description for project TTEST-1");
        projectRepository.save(testProject);
        Task newTask = new Task("First Test Task", "First Test Task", LocalDate.now(), testProject);

        taskRepository.save(newTask);

        assertThat(entityManager.find(Task.class, newTask.getId())).isEqualTo(newTask);
    }

    @Test
    void givenTaskCreated_whenFindById_thenSuccess() {
        Project testProject = new Project("TTEST-2", "Task Test Project 1", "Description for project TTEST-2");
        projectRepository.save(testProject);

        Task newTask = new Task("First Test Task", "First Test Task", LocalDate.now(), testProject);
        taskRepository.save(newTask);

        Optional<Task> retrievedTask = taskRepository.findById(newTask.getId());
        assertThat(retrievedTask.get()).isEqualTo(entityManager.find(Task.class, retrievedTask.get()
            .getId()));
    }

    @Test
    void givenSlicedTasksByName_whenHasPrevHasAfter_thenSuccess() {
        Task taskOne = taskRepository.findById(1l)
            .get();
        Task taskTwo = taskRepository.findById(2l)
            .get();

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
        Task taskOne = taskRepository.findById(1l)
            .get();
        Task taskTwo = taskRepository.findById(2l)
            .get();

        Pageable twoTasksPagination = PageRequest.of(0, 2);

        Page<Task> twoTasksAPage = taskRepository.findByStatus(TaskStatus.TO_DO, twoTasksPagination);

        assertThat(twoTasksAPage.getTotalElements()).isEqualTo(4l);
        assertThat(twoTasksAPage.getTotalPages()).isEqualTo(2);
        assertThat(twoTasksAPage.getContent()).isNotNull()
            .isNotEmpty()
            .endsWith(taskOne, taskTwo);
    }

    @Test
    void givenCustomeQuery_whenFindAllTasksPaginated_thenSuccess() {
        Task taskThree = taskRepository.findById(3l)
            .get();
        Task taskFour = taskRepository.findById(4l)
            .get();

        Pageable pageTwo = PageRequest.of(1, 2);

        Page<Task> allTasksByNamePageTwo = taskRepository.allTasksByName("Task%", pageTwo);

        assertThat(allTasksByNamePageTwo.getTotalElements()).isEqualTo(4l);
        assertThat(allTasksByNamePageTwo.getTotalPages()).isEqualTo(2);
        assertThat(allTasksByNamePageTwo.getContent()).isNotNull()
            .isNotEmpty()
            .endsWith(taskThree, taskFour);
    }
}