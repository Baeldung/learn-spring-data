package com.baeldung.lsd;

import java.io.IOError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.baeldung.lsd.persistence.model.Project;
import com.baeldung.lsd.persistence.repository.ProjectRepository;
import com.baeldung.lsd.service.ProjectService;

@SpringBootApplication
public class SpringDataAndTransactionsApp implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(SpringDataAndTransactionsApp.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    public static void main(final String... args) {
        SpringApplication.run(SpringDataAndTransactionsApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        Project dbProjectOne = projectRepository.findById(1L)
            .get();

        try {
            projectService.endProject(dbProjectOne);
        } catch (IOError e) {
            LOG.info("Expected Error thrown");
        }

        projectRepository.findByNameContaining(" - Cont").forEach(project -> {
            LOG.info("Continuation Project Tasks:\n");
            project.getTasks().forEach(task -> LOG.info(task.toString()));
        });
            

        
    }

}
