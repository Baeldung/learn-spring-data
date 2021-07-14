package com.baeldung.lsd;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.baeldung.lsd.persistence.projection.ProjectClosed;
import com.baeldung.lsd.persistence.projection.ProjectNative;
import com.baeldung.lsd.persistence.projection.UserOpen;
import com.baeldung.lsd.persistence.projection.ProjectClass;
import com.baeldung.lsd.persistence.repository.ProjectRepository;
import com.baeldung.lsd.persistence.repository.UserRepository;

@SpringBootApplication
public class ReturningCustomObjectApp implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(ReturningCustomObjectApp.class);

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public static void main(final String... args) {
        SpringApplication.run(ReturningCustomObjectApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<ProjectClosed> closedProjections = projectRepository.findClosedByNameContaining("Project");
        closedProjections.forEach(p -> LOG.info("id: {}, name: {}", p.getId(), p.getName()));

        List<UserOpen> openProjections = userRepository.findByFirstName("John");
        openProjections.forEach(u -> LOG.info("user: id {}, full name: {}", u.getId(), u.getName()));

        List<ProjectClass> classProjections = projectRepository.findClassByNameContaining("Project");
        classProjections.forEach(p -> LOG.info("project: {}", p));

        List<ProjectNative> statistics = projectRepository.getProjectStatistics();
        statistics.forEach(s -> LOG.info("project statistics: id: {}, name: {}, tasks: {}", s.getId(), s.getName(), s.getTaskCount()));
    }

}
