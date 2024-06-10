package com.baeldung.lsd.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.baeldung.lsd.persistence.model.Campaign;
import com.baeldung.lsd.persistence.projection.CampaignClass;
import com.baeldung.lsd.persistence.projection.CampaignClosed;
import com.baeldung.lsd.persistence.projection.CampaignNative;

@DataJpaTest
public class CampaignRepositoryIntegrationTest {

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
    void givenInitialDBState_whenFindClosed_thenSuccess() {
        List<CampaignClosed> campaigns = campaignRepository.findClosedByNameContaining("Campaign 1");
        assertThat(campaigns).hasSize(1);
        CampaignClosed campaign = campaigns.get(0);
        assertThat(campaign.getName()).isEqualTo("Campaign 1");

    }

    @Test
    void givenIntialDBState_whenFindClassBased_thenSuccess() {
        List<CampaignClass> campaigns = campaignRepository.findClassByNameContaining("Campaign");

        assertThat(campaigns).hasSize(3);

        campaigns.forEach(p -> {
            assertTrue(p.getName()
                .contains("Campaign"));
        });
    }

    @Test
    void givenIntialDBState_whenGetStatistics_thenSuccess() {
        List<CampaignNative> campaigns = campaignRepository.getCampaignStatistics();
        campaigns.sort(Comparator.comparing(CampaignNative::getId));

        CampaignNative campaign1 = campaigns.get(0);
        CampaignNative campaign2 = campaigns.get(1);
        CampaignNative campaign3 = campaigns.get(2);

        assertThat(campaign1.getName()).isEqualTo("Campaign 1");
        assertThat(campaign1.getTaskCount()).isEqualTo(3);
        assertThat(campaign2.getName()).isEqualTo("Campaign 2");
        assertThat(campaign2.getTaskCount()).isOne();
        assertThat(campaign3.getName()).isEqualTo("Campaign 3");
        assertThat(campaign3.getTaskCount()).isZero();
    }

}