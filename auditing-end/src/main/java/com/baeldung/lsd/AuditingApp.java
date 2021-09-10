package com.baeldung.lsd;

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
public class AuditingApp implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(AuditingApp.class);

    @Autowired
    private ProjectRepository projectRepository;

    public static void main(final String... args) {
        SpringApplication.run(AuditingApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Project project = new Project("P4", "Audited Project", "This project has auditable fields");
        project = projectRepository.save(project);
        LOG.info("New Project Auditing Data:{}", project.getAuditingData());

        Thread.sleep(2000);
        project.setName("Updated Project");
        project = projectRepository.save(project);
        LOG.info("Updated Project Auditing Data:{}", project.getAuditingData());
    }
}
