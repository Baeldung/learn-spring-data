package com.baeldung.lsd;

import com.baeldung.lsd.persistence.model.Project;
import com.baeldung.lsd.persistence.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootApplication
public class NamedQueriesApp implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(NamedQueriesApp.class);

    @Autowired
    EntityManager entityManager;

    @Autowired
    private ProjectRepository projectRepository;

    public static void main(final String... args) {
        SpringApplication.run(NamedQueriesApp.class, args);
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        List<Project> projectsWithIdGt1 = entityManager.createNamedQuery("namedQueryProjectsWithIdGreaterThan", Project.class)
            .setParameter("id", 1L)
            .getResultList();
        LOG.info("Find Projects with Id greater than 1 using EntityManager:\n{}", projectsWithIdGt1);

        List<Project> projectsWithIdLt3 = projectRepository.findProjectsWithIdLessThan(3L);
        LOG.info("Find Projects with Id less than 3:\n{}", projectsWithIdLt3);

        projectRepository.updateProjectDescriptionById(1L, "New description updated by named query");
        Project project1 = projectRepository.findById(1L)
            .get();
        LOG.info("After updating the description of the Project(id=1):\n{}", project1);

        List<Project> projectsWithShortDescription = projectRepository.findProjectsWithDescriptionShorterThan(16);
        LOG.info("Find Projects with description shorter than 16:\n{}", projectsWithShortDescription);

        List<Project> projectsWithDescriptionPrefix = projectRepository.findProjectsWithDescriptionPrefix("About");
        LOG.info("Find Projects with Description Prefix (NamedQuery from properties file):\n{}", projectsWithDescriptionPrefix);

    }

}
