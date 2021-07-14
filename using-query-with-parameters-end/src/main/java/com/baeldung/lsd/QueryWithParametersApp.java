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

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class QueryWithParametersApp implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(QueryWithParametersApp.class);

    @Autowired
    private ProjectRepository projectRepository;

    public static void main(final String... args) {
        SpringApplication.run(QueryWithParametersApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Project> projectList1 = projectRepository.findWithNameAndDescriptionPositionalBind("Project 3", "About Project 3");
        LOG.info("find Project 3 using positional parameters:\n{}", projectList1);

        List<Project> projectList2 = projectRepository.findWithNameAndDescriptionNamedBind("About Project 3", "Project 3");
        LOG.info("find Project 3 using named parameters:\n{}", projectList2);

        List<Project> projectList3 = projectRepository.findWithCodeIn(Set.of("P2", "P3"));
        LOG.info("find Project 2 and Project 3 using IN clause:\n{}", projectList3);

        List<Project> projectList4 = projectRepository.findWithDescriptionIsLike("About");
        LOG.info("find Projects containing 'About' using LIKE clause:\n{}", projectList4);

        List<Project> projectList5 = projectRepository.findWithDescriptionWithPrefixAndSuffix("About", "3");
        LOG.info("find Project 3 using prefix and suffix in LIKE clause:\n{}", projectList5);

        List<Project> projectList6 = projectRepository.findWithDescriptionIsShorterThan(16);
        LOG.info("find Projects with short descriptions using Native query:\n{}", projectList6);

        List<Project> projectList7 = projectRepository.findWithDescriptionWithPrefix("%");
        LOG.info("find all Projects by passing '%' to LIKE clause:\n{}", projectList7);

    }

}
