package com.baeldung.lsd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
        projectRepository.findAll()
            .forEach(project -> LOG.info(project.toString()));

        taskRepository.findById(1L)
            .ifPresent(task -> LOG.info(task.toString()));

        long noOfUsers = userRepository.count();
        LOG.info("number of users: {}", noOfUsers);
    }
}
