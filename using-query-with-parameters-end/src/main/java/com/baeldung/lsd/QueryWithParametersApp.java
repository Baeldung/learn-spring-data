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

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class QueryWithParametersApp implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(QueryWithParametersApp.class);

    @Autowired
    private CampaignRepository campaignRepository;

    public static void main(final String... args) {
        SpringApplication.run(QueryWithParametersApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Campaign> campaignList1 = campaignRepository.findWithNameAndDescriptionPositionalBind("Campaign 3", "About Campaign 3");
        LOG.info("find Campaign 3 using positional parameters:\n{}", campaignList1);

        List<Campaign> campaignList2 = campaignRepository.findWithNameAndDescriptionNamedBind("About Campaign 3", "Campaign 3");
        LOG.info("find Campaign 3 using named parameters:\n{}", campaignList2);

        List<Campaign> campaignList3 = campaignRepository.findWithCodeIn(Set.of("C2", "C3"));
        LOG.info("find Campaign 2 and Campaign 3 using IN clause:\n{}", campaignList3);

        List<Campaign> campaignList4 = campaignRepository.findWithDescriptionIsLike("About");
        LOG.info("find Campaigns containing 'About' using LIKE clause:\n{}", campaignList4);

        List<Campaign> campaignList5 = campaignRepository.findWithDescriptionWithPrefixAndSuffix("About", "3");
        LOG.info("find Campaign 3 using prefix and suffix in LIKE clause:\n{}", campaignList5);

        List<Campaign> campaignList6 = campaignRepository.findWithDescriptionIsShorterThan(17);
        LOG.info("find Campaigns with short descriptions using Native query:\n{}", campaignList6);

        List<Campaign> campaignList7 = campaignRepository.findWithDescriptionWithPrefix("%");
        LOG.info("find all Campaigns by passing '%' to LIKE clause:\n{}", campaignList7);

    }

}
