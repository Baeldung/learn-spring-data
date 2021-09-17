package com.baeldung.lsd.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

@DataJpaTest
class ProjectRepositoryIntegrationTest {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private TransactionTemplate transactionTemplate;
    
    @Test
    void givenNewProject_whenSave_thenSuccess() {
        Project newProject = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");

        Project insertedProject = projectRepository.save(newProject);

        assertThat(entityManager.find(Project.class, insertedProject.getId())).isEqualTo(newProject);
    }

    @Test
    void givenProjectCreated_whenUpdate_thenSuccess() {
        Project newProject = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");
        entityManager.persist(newProject);

        String newName = "New Project 001";
        newProject.setName(newName);
        projectRepository.save(newProject);

        assertThat(entityManager.find(Project.class, newProject.getId())
            .getName()).isEqualTo(newName);
    }

    @Test
    void givenProjectCreated_whenFindById_thenSuccess() {
        Project newProject = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");
        entityManager.persist(newProject);

        Optional<Project> retrievedProject = projectRepository.findById(newProject.getId());
        assertThat(retrievedProject).contains(newProject);
    }

    @Test
    void givenProjectCreated_whenFindByNameContaining_thenSuccess() {
        Project newProject1 = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");
        Project newProject2 = new Project("PTEST-2", "Test Project 2", "Description for project PTEST-2");
        entityManager.persist(newProject1);
        entityManager.persist(newProject2);

        Iterable<Project> projects = projectRepository.findByNameContaining("Test");
        assertThat(projects).contains(newProject1, newProject2);
    }

    @Test
    void givenProjectCreated_whenDelete_thenSuccess() {
        Project newProject = new Project("PTEST-1", "Test Project 1", "Description for project PTEST-1");
        entityManager.persist(newProject);

        projectRepository.delete(newProject);

        assertThat(entityManager.find(Project.class, newProject.getId())).isNull();
    }
    
    @Test
    //Disable Transactions since we want to assert outside transaction
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void givenProjects_whenFindByNameContaining_thenAllFieldsLoad() {
        
        //execute within a transaction
        Iterable<Project> projects = transactionTemplate.execute((status) -> {
            return projectRepository.findByNameContaining("Test");
        });
        
        //assert outside the transaction, if associations were loaded eagerly 
        projects.forEach(project -> {
            assertThat(project)
                // check if tasks association is loaded eagerly
                .returns(project.getTasks(), Project::getTasks);
            // check if task assignee is loaded eagerly
            project.getTasks().forEach(task -> assertThat(task).returns(task.getAssignee(), Task::getAssignee));
        });

    }
    
    @Test
    //Disable Transactions since we want to assert outside transaction
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void givenProjectFoundByIdWithoutEGraphs_whenAccessAssociations_thenLazyException() {
       
        //execute within a transaction
       Optional<Project> projectOpt = transactionTemplate.execute((status) -> {
         //attempt to load a project(findbyId not using entity graphs)
            return projectRepository.findById(1l);
        });
        
       // assert outside the transaction, if associations were loaded lazily
       projectOpt.ifPresent(project -> {
           // assert lazy initialization exception when tasks association is accessed outside of a transaction
           assertThatThrownBy(() -> {
               project.getTasks().isEmpty();
           }).isInstanceOf(LazyInitializationException.class);
       });

    }

}