package com.baeldung.lsd;

import com.baeldung.lsd.persistence.model.Campaign;
import com.baeldung.lsd.persistence.repository.CampaignRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import java.util.List;

@SpringBootApplication
public class NamedQueriesApp implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(NamedQueriesApp.class);

    @Autowired
    EntityManager entityManager;

    @Autowired
    private CampaignRepository campaignRepository;

    public static void main(final String... args) {
        SpringApplication.run(NamedQueriesApp.class, args);
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        List<Campaign> campaignsWithIdGt1 = entityManager.createNamedQuery("namedQueryCampaignsWithIdGreaterThan", Campaign.class)
            .setParameter("id", 1L)
            .getResultList();
        LOG.info("Find Campaigns with Id greater than 1 using EntityManager:\n{}", campaignsWithIdGt1);

        List<Campaign> campaignsWithIdLt3 = campaignRepository.findCampaignsWithIdLessThan(3L);
        LOG.info("Find Campaigns with Id less than 3:\n{}", campaignsWithIdLt3);

        campaignRepository.updateCampaignDescriptionById(1L, "New description updated by named query");
        Campaign campaign1 = campaignRepository.findById(1L)
            .get();
        LOG.info("After updating the description of the Campaign(id=1):\n{}", campaign1);

        List<Campaign> campaignsWithShortDescription = campaignRepository.findCampaignsWithDescriptionShorterThan(17);
        LOG.info("Find Campaigns with description shorter than 17:\n{}", campaignsWithShortDescription);

        List<Campaign> campaignsWithDescriptionPrefixes = campaignRepository.findCampaignsWithDescriptionPrefix("About");
        LOG.info("Find Campaigns with Description Prefix (NamedQuery from properties file):\n{}", campaignsWithDescriptionPrefixes);

    }

}
