package com.baeldung.lsd;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.baeldung.lsd.persistence.model.Project;
import com.baeldung.lsd.persistence.repository.ProjectRepository;
import com.baeldung.lsd.persistence.repository.TaskRepository;

@SpringBootApplication
public class DerivedQueryMethodsApp implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DerivedQueryMethodsApp.class);

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TaskRepository taskRepository;

    public static void main(final String... args) {
        SpringApplication.run(DerivedQueryMethodsApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Project> project1 = projectRepository.findByCodeEquals("P1");
        LOG.info("Project with code P1: \n{}", project1);

        int projectCount = projectRepository.countByName("Project 1");
        LOG.info("Number of projects with name 'Project 1':\n{}", projectCount);
    }

}
