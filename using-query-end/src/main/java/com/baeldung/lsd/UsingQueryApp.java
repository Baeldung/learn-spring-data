package com.baeldung.lsd;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.baeldung.lsd.persistence.model.Campaign;
import com.baeldung.lsd.persistence.repository.CampaignRepository;
import com.baeldung.lsd.persistence.repository.TaskRepository;

@SpringBootApplication
public class UsingQueryApp implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(UsingQueryApp.class);

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private TaskRepository taskRepository;

    public static void main(final String... args) {
        SpringApplication.run(UsingQueryApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Campaign> campaigns = campaignRepository.findWithNameAndDescription();
        LOG.info("Campaign 3:\n{}", campaigns);

        Optional<String> campaignName = campaignRepository.findNameByCode();
        campaignName.ifPresent(c -> LOG.info("Campaign Name:\n{}", c));

        List<List<Integer>> tasksCountByDueYear = taskRepository.countByDueYear();
        LOG.info("Tasks count by due years:\n Count \t year\n{}", tasksCountByDueYear.stream()
            .map(t -> "\t" + t.get(0) + "\t" + t.get(1))
            .collect(Collectors.joining("\n")));

        Campaign campaign = campaignRepository.findSingleCampaign();
        LOG.info("Single Campaign:\n{}", campaign);
    }

}
