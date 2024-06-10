package com.baeldung.lsd.persistence.repository;

import com.baeldung.lsd.persistence.model.Campaign;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@AutoConfigureTestEntityManager
class CampaignRepositoryIntegrationTest {

    @Autowired
    CampaignRepository campaignRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void givenNewCampaign_whenSave_thenSuccess() {
        Campaign newCampaign = new Campaign("CTEST-1", "Test Campaign 1", "Description for campaign CTEST-1");

        Campaign insertedCampaign = campaignRepository.save(newCampaign);

        assertThat(entityManager.find(Campaign.class, insertedCampaign.getId())).isEqualTo(newCampaign);
    }

    @Test
    void givenCampaignCreated_whenUpdate_thenSuccess() {
        Campaign newCampaign = new Campaign("CTEST-1", "Test Campaign 1", "Description for campaign CTEST-1");
        entityManager.persist(newCampaign);

        String newName = "New Campaign 001";
        newCampaign.setName(newName);
        campaignRepository.save(newCampaign);

        assertThat(entityManager.find(Campaign.class, newCampaign.getId())
            .getName()).isEqualTo(newName);
    }

    @Test
    void givenCampaignCreated_whenFindById_thenSuccess() {
        Campaign newCampaign = new Campaign("CTEST-1", "Test Campaign 1", "Description for campaign CTEST-1");
        entityManager.persist(newCampaign);

        Optional<Campaign> retrievedCampaign = campaignRepository.findById(newCampaign.getId());
        assertThat(retrievedCampaign).contains(newCampaign);
    }

    @Test
    void givenCampaignCreated_whenFindByNameContaining_thenSuccess() {
        Campaign newCampaign1 = new Campaign("CTEST-1", "Test Campaign 1", "Description for campaign CTEST-1");
        Campaign newCampaign2 = new Campaign("CTEST-2", "Test Campaign 2", "Description for campaign CTEST-2");
        entityManager.persist(newCampaign1);
        entityManager.persist(newCampaign2);

        Iterable<Campaign> campaigns = campaignRepository.findByNameContaining("Test");
        assertThat(campaigns).contains(newCampaign1, newCampaign2);
    }

    @Test
    void givenCampaignCreated_whenDelete_thenSuccess() {
        Campaign newCampaign = new Campaign("CTEST-1", "Test Campaign 1", "Description for campaign CTEST-1");
        entityManager.persist(newCampaign);

        campaignRepository.delete(newCampaign);

        assertThat(entityManager.find(Campaign.class, newCampaign.getId())).isNull();
    }

}