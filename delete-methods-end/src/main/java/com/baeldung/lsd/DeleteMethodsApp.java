package com.baeldung.lsd;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.baeldung.lsd.persistence.model.Project;
import com.baeldung.lsd.persistence.repository.ProjectRepository;

@SpringBootApplication
public class DeleteMethodsApp implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DeleteMethodsApp.class);

    @Autowired
    private ProjectRepository projectRepository;

    public static void main(final String... args) {
        SpringApplication.run(DeleteMethodsApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // delete using reference
        Project p1 = projectRepository.findById(1L)
            .get();
        projectRepository.delete(p1);

        // delete using id
        projectRepository.deleteById(2L);

        Iterable<Project> projectsToDelete = projectRepository.findAllById(List.of(3L, 5L));

        // delete several projects
        projectRepository.deleteAll(projectsToDelete);

        // delete using custom query and with count
        Long deleteCount = projectRepository.deleteByNameContaining("Project 2");

        LOG.info("Number of removed projects:\n{}", deleteCount);

        // delete using custom query
        projectRepository.deleteByNameContaining("project");
    }
}
