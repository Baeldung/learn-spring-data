package com.baeldung.lsd.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.lsd.IntroToJpaRepositoriesApp;
import com.baeldung.lsd.persistence.model.Campaign;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { IntroToJpaRepositoriesApp.class }, initializers = ConfigDataApplicationContextInitializer.class)
class CampaignRepositoryIntegrationTest {

    @Autowired
    CampaignRepository campaignRepository;

    @Test
    void givenLoadedCampaign_whenFindByID_thenSuccess() {
        Optional<Campaign> savedCampaign = campaignRepository.findById(1L);
        assertNotNull(savedCampaign.get());
    }
}