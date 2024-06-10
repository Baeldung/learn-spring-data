package com.baeldung.lsd.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.baeldung.lsd.persistence.model.Campaign;

@DataJpaTest
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

    @Test
    void givenCampaignCreated_whenFindByNameAndDescription_thenSuccess() {
        Campaign newCampaign = new Campaign("CTEST-3", "Campaign 3", "About Campaign 3");
        entityManager.persist(newCampaign);

        List<Campaign> campaignList = campaignRepository.findWithNameAndDescription();

        assertThat(campaignList).contains(newCampaign);
    }

    @Test
    void givenCampaignCreated_whenFindSingleCampaign_thenSuccess() {
        Campaign newCampaign = new Campaign("CTEST-1", "Test Campaign 1", "Description for campaign CTEST-1");
        entityManager.persist(newCampaign);

        Campaign campaign = campaignRepository.findSingleCampaign();

        assertThat(campaign).isNotNull();
    }

    @Test
    void givenCampaignExists_whenFindNameByCode_thenSuccess() {
        Optional<String> campaignName = campaignRepository.findNameByCode();

        campaignName.ifPresent((name) -> assertThat(name).isEqualTo("Campaign 1"));
    }
}