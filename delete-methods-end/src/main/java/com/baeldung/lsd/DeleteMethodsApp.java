package com.baeldung.lsd;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.baeldung.lsd.persistence.model.Campaign;
import com.baeldung.lsd.persistence.repository.CampaignRepository;

@SpringBootApplication
public class DeleteMethodsApp implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DeleteMethodsApp.class);

    @Autowired
    private CampaignRepository campaignRepository;

    public static void main(final String... args) {
        SpringApplication.run(DeleteMethodsApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // delete using reference
        Campaign p1 = campaignRepository.findById(1L)
            .get();
        campaignRepository.delete(p1);

        // delete using id
        campaignRepository.deleteById(2L);

        Iterable<Campaign> campaignsToDelete = campaignRepository.findAllById(List.of(3L, 5L));

        // delete several campaigns
        campaignRepository.deleteAll(campaignsToDelete);

        // delete using custom query and with count
        Long deleteCount = campaignRepository.deleteByNameContaining("Campaign 2");

        LOG.info("Number of removed campaigns:\n{}", deleteCount);

        // delete using custom query
        campaignRepository.deleteByNameContaining("campaign");
    }
}
