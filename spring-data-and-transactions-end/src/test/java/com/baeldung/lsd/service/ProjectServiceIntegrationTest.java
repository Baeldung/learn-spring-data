package com.baeldung.lsd.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.doThrow;

import java.io.IOError;
import java.time.LocalDate;
import java.util.Collections;

import javax.persistence.EntityManagerFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.baeldung.lsd.persistence.model.Project;
import com.baeldung.lsd.persistence.model.Task;
import com.baeldung.lsd.persistence.model.TaskStatus;
import com.baeldung.lsd.persistence.repository.TaskRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ProjectServiceIntegrationTest {

    @Autowired
    private ProjectService projectService;

    @SpyBean
    private TaskRepository taskRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private PlatformTransactionManager transactionManager;

    private TestEntityManager entityManager;

    private TransactionTemplate transactionTemplate;

    @BeforeEach
    void setUp() {
        entityManager = new TestEntityManager(entityManagerFactory);
        transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Test
    void givenIOError_whenEndProjectInvoked_thenNoRollback() {
        Mockito.reset(taskRepository);
        Project persistedNewProject = transactionTemplate.execute((status) -> {
            Project newProject = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");
            Task newTask = new Task("TTEST-1", "Test Task 1", LocalDate.of(2025, 1, 1), newProject, TaskStatus.TO_DO);
            newProject.setTasks(Collections.singleton(newTask));
            return entityManager.persist(newProject);
        });

        Throwable throwable = catchThrowable(() -> {
            projectService.endProject(persistedNewProject);
        });

        Project continuationProject = transactionTemplate.execute((status) -> {
            return entityManager.find(Project.class, persistedNewProject.getId() + 1);
        });
        assertThat(throwable).isInstanceOf(IOError.class);
        assertThat(continuationProject.getTasks()).extracting(Task::getName)
            .containsOnly("TTEST-1");
    }

    @Test
    void givenRuntimeException_whenEndProjectInvoked_theRollback() {
        Project persistedNewProject = transactionTemplate.execute((status) -> {
            Project newProject = new Project("PTEST-2", "Test Project 2", "Description for project PTEST-2");
            Task newTask = new Task("TTEST-2", "Test Task 2", LocalDate.of(2025, 1, 1), newProject, TaskStatus.TO_DO);
            newProject.setTasks(Collections.singleton(newTask));
            return entityManager.persist(newProject);
        });
        doThrow(RuntimeException.class).when(taskRepository)
            .saveAll(ArgumentMatchers.anyCollection());

        Throwable throwable = catchThrowable(() -> {
            projectService.endProject(persistedNewProject);
        });

        Project originalProject = transactionTemplate.execute((status) -> {
            return entityManager.find(Project.class, persistedNewProject.getId());
        });
        Project continuationProject = transactionTemplate.execute((status) -> {
            return entityManager.find(Project.class, persistedNewProject.getId() + 1);
        });
        assertThat(throwable).isInstanceOf(RuntimeException.class);
        assertThat(continuationProject).isNull();
        assertThat(originalProject.getTasks()).extracting(Task::getName)
            .containsOnly("TTEST-2");
    }

}
