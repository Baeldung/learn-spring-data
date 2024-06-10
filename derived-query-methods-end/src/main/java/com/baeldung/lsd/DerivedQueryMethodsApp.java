package com.baeldung.lsd;

import java.util.Optional;

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
public class DerivedQueryMethodsApp implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DerivedQueryMethodsApp.class);

    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private TaskRepository taskRepository;

    public static void main(final String... args) {
        SpringApplication.run(DerivedQueryMethodsApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        Optional<Campaign> campaign1 = campaignRepository.findByCodeEquals("C1");
        LOG.info("Campaign with code C1: \n{}", campaign1);

        int campaignCount = campaignRepository.countByName("Campaign 1");
        LOG.info("Number of campaigns with name 'Campaign 1':\n{}", campaignCount);
    }

}
