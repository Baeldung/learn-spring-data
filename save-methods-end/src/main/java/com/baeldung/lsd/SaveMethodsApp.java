package com.baeldung.lsd;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.baeldung.lsd.persistence.repository.ProjectRepository;
import com.baeldung.lsd.persistence.model.Project;
import com.baeldung.lsd.persistence.model.Task;

@SpringBootApplication
public class SaveMethodsApp implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(SaveMethodsApp.class);

    @Autowired
    private ProjectRepository projectRepository;

    public static void main(final String... args) {
        SpringApplication.run(SaveMethodsApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Project newProject = new Project("NEW1", "new project", "new project description");
        LOG.info("Project id before persisting:\n{}", newProject.getId());

        // save new entity
        projectRepository.save(newProject);

        LOG.info("Project id after persisting:\n{}", newProject.getId());

        newProject.setName("updated name");
        Set<Task> newProjectTasks = Set.of(new Task("task name", "task description", LocalDate.of(2025, 1, 1), newProject));
        newProject.setTasks(newProjectTasks);

        // update entity (has id)
        newProject = projectRepository.save(newProject);

        LOG.info("Child Task after updating:\n{}", newProject.getTasks());

        newProject.setName("updated again");
        Project p1 = projectRepository.findById(1L)
            .get();
        Set<Task> differentTasks = Set.of(new Task("different task", "different description", LocalDate.of(2025, 1, 1), p1));
        p1.setTasks(differentTasks);
        Project newProject2 = new Project("NEW2", "another project", "another project description");
        Iterable<Project> severalProjects = Arrays.asList(newProject, p1, newProject2);

        // update existing projects and save new ones
        severalProjects = projectRepository.saveAll(severalProjects);

        newProject.setName("updated once more");
        Project newProject3 = new Project("NEW2", "duplicate code!", "project with constraint violation");
        severalProjects = Arrays.asList(newProject, newProject3);

        try {
            // triggers a database error
            projectRepository.saveAll(severalProjects);
        } catch (Exception ex) {
            LOG.info("Error saving/updating multiple entities");
        }
    }
}
