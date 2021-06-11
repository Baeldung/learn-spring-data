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
import com.baeldung.lsd.persistence.model.Task;
import com.baeldung.lsd.persistence.repository.IProjectRepository;
import com.baeldung.lsd.persistence.repository.ITaskRepository;
import com.baeldung.lsd.persistence.repository.IUserRepository;

@SpringBootApplication
public class IntroToJpaRepositoriesApp implements ApplicationRunner {

    @Autowired
    private IProjectRepository projectRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ITaskRepository taskRepository;
    private static final Logger LOG = LoggerFactory.getLogger(IntroToJpaRepositoriesApp.class);

    public static void main(final String... args) {
        SpringApplication.run(IntroToJpaRepositoriesApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Iterable<Project> allProjects = projectRepository.findAll();
        LOG.info("All Projects:\n{}", allProjects);

        Optional<Task> project1 = taskRepository.findById(1L);
        LOG.info("Task by id 1:\n{}", project1);

        long noOfUsers = userRepository.count();
        LOG.info("Number of users:\n{}", noOfUsers);
    }
}
