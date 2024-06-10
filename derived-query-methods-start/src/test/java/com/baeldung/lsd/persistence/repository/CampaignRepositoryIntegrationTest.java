package com.baeldung.lsd.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.lsd.DerivedQueryMethodsApp;
import com.baeldung.lsd.persistence.model.Campaign;

@ExtendWith(SpringExtension.class)
// avoid running the DerivedQueryMethodsApp#run method
@ContextConfiguration(classes = { DerivedQueryMethodsApp.class }, initializers = ConfigDataApplicationContextInitializer.class)
@Transactional
class CampaignRepositoryIntegrationTest {

    @Autowired
    CampaignRepository campaignRepository;

    @Test
    void givenNewCampaign_whenSaved_thenSuccess() {
        Campaign newCampaign = new Campaign("CTEST-1", "Test Campaign 1", "Description for campaign CTEST-1");
        assertNotNull(campaignRepository.save(newCampaign));
    }

    @Test
    void givenCampaignCreated_whenFindById_thenSuccess() {
        Campaign newCampaign = new Campaign("CTEST-2", "Test Campaign 2", "Description for campaign CTEST-2");
        campaignRepository.save(newCampaign);

        Optional<Campaign> retrievedCampaign = campaignRepository.findById(newCampaign.getId());
        assertEquals(retrievedCampaign.get(), newCampaign);
    }
}