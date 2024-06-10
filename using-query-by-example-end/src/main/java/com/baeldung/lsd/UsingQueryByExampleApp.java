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

import com.baeldung.lsd.persistence.model.Campaign;
import com.baeldung.lsd.persistence.model.Task;
import com.baeldung.lsd.persistence.repository.CampaignRepository;
import com.baeldung.lsd.persistence.repository.TaskRepository;

@SpringBootApplication
public class UsingQueryByExampleApp implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(UsingQueryByExampleApp.class);

    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private TaskRepository taskRepository;

    public static void main(final String... args) {
        SpringApplication.run(UsingQueryByExampleApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Campaign campaign = new Campaign();
        campaign.setName("Campaign 1");

        Example<Campaign> campaignExample = Example.of(campaign);
        Optional<Campaign> found = campaignRepository.findOne(campaignExample);
        if (found.isPresent()) {
            LOG.info("Campaign 1 output: {}", found.get()
                .toString());
        }

        // case-insensitive
        Campaign campaign2 = new Campaign();
        campaign2.setName("campaign 2");
        ExampleMatcher caseInsensitiveMatcher = ExampleMatcher.matchingAll()
            .withIgnoreCase();
        Example<Campaign> caseInsensitiveExample = Example.of(campaign2, caseInsensitiveMatcher);
        Optional<Campaign> found2 = campaignRepository.findOne(caseInsensitiveExample);
        if (found2.isPresent())
            LOG.info("Campaign 2 output: {}", found2.get()
                .toString());

        // custom matching
        Campaign probe = new Campaign();
        probe.setName("campaign");

        ExampleMatcher matchContains = ExampleMatcher.matching()
            .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.startsWith()
                .ignoreCase());
        Example<Campaign> probeExample = Example.of(probe, matchContains);
        List<Campaign> campaigns = campaignRepository.findAll(probeExample);

        LOG.info("Campaign list output: {}", campaigns);

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
