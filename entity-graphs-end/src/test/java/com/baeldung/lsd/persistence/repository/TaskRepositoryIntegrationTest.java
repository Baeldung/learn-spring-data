package com.baeldung.lsd.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.from;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

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
    
    @Autowired
    private TransactionTemplate transactionTemplate;

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
    //Disable Transactions since we want to assert outside transaction
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void givenEntityGraph_whenFindByNameContaining_thenReturnDefinedFields() {
        
        //execute within a transaction
        List<Task> dbTasks = transactionTemplate.execute((status) -> {
            return taskRepository.findByStatus(TaskStatus.TO_DO);
        });
        
        dbTasks.forEach( task -> assertThat(task)
            //check the task status is TO_DO
            .returns(TaskStatus.TO_DO, from(Task::getStatus))
            //check if project association is loaded eagerly
            .returns(task.getProject(), from(Task::getProject))
            //check if assignee association is loaded eagerly
            .returns(task.getAssignee(), from(Task::getAssignee)));
    }
    
    @Test
    //Disable Transactions since we want to assert outside transaction
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void givenTaskFoundByIdWithoutEGraphs_whenAccessAssociations_thenLazyException() {
       
       //execute within a transaction
       Optional<Task> taskOpt = transactionTemplate.execute((status) -> {
           //attempt to load a task with an assignee (findbyId not using entity graphs)
            return taskRepository.findById(4l);
        });
        
        //assert outside the transaction, if associations were loaded lazily 
       taskOpt.ifPresent(task -> {
           // assert lazy initialization exception when project association is accessed
           assertThatExceptionOfType(LazyInitializationException.class).isThrownBy(() -> {
               task.getProject().getName();
           });
           // assert lazy initialization exception when assignee association is accessed
           assertThatExceptionOfType(LazyInitializationException.class).isThrownBy(() -> {
               task.getAssignee().getLastName();
           });
       });

    }
    
}