package com.baeldung.lsd;

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
public class AuditingApp implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(AuditingApp.class);

    @Autowired
    private CampaignRepository campaignRepository;

    public static void main(final String... args) {
        SpringApplication.run(AuditingApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Campaign campaign = new Campaign("P4", "Audited Campaign", "This campaign has auditable fields");
        campaign = campaignRepository.save(campaign);
        LOG.info("New Campaign Auditing Data:{}", campaign.getAuditingData());

        Thread.sleep(2000);
        campaign.setName("Updated Campaign");
        campaign = campaignRepository.save(campaign);
        LOG.info("Updated Campaign Auditing Data:{}", campaign.getAuditingData());
    }
}
