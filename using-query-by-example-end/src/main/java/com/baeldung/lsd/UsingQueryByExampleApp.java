package com.baeldung.lsd;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import com.baeldung.lsd.persistence.model.Project;
import com.baeldung.lsd.persistence.model.Task;
import com.baeldung.lsd.persistence.repository.ProjectRepository;
import com.baeldung.lsd.persistence.repository.TaskRepository;

@SpringBootApplication
public class UsingQueryByExampleApp implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(UsingQueryByExampleApp.class);

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TaskRepository taskRepository;

    public static void main(final String... args) {
        SpringApplication.run(UsingQueryByExampleApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Project project = new Project();
        project.setName("Project 1");

        Example<Project> projectExample = Example.of(project);
        Optional<Project> found = projectRepository.findOne(projectExample);
        if (found.isPresent()) {
            LOG.info("Project 1 output: {}", found.get()
                .toString());
        }

        // case-insensitive
        Project project2 = new Project();
        project2.setName("project 2");
        ExampleMatcher caseInsensitiveMatcher = ExampleMatcher.matchingAll()
            .withIgnoreCase();
        Example<Project> caseInsensitiveExample = Example.of(project2, caseInsensitiveMatcher);
        Optional<Project> found2 = projectRepository.findOne(caseInsensitiveExample);
        if (found2.isPresent())
            LOG.info("Project 2 output: {}", found2.get()
                .toString());

        // custom matching
        Project probe = new Project();
        probe.setName("project");

        ExampleMatcher matchContains = ExampleMatcher.matching()
            .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.startsWith()
                .ignoreCase());
        Example<Project> probeExample = Example.of(probe, matchContains);
        List<Project> projects = projectRepository.findAll(probeExample);

        LOG.info("Project list output: {}", projects);

        // custom matching with multiple attributes
        Task taskProbe = new Task();
        taskProbe.setDescription("Description");
        taskProbe.setDueDate(LocalDate.of(2025, 3, 16));

        ExampleMatcher customMatcher = ExampleMatcher.matching()
            .withMatcher("description", match -> match.endsWith()
                .ignoreCase())
            .withMatcher("dueDate", match -> match.exact())
            .withIgnorePaths("uuid");

        Example<Task> taskExample = Example.of(taskProbe, customMatcher);
        Optional<Task> taskOp = taskRepository.findOne(taskExample);

        taskOp.ifPresent(task -> LOG.info("Found Task: {}", task.toString()));
    }

}
