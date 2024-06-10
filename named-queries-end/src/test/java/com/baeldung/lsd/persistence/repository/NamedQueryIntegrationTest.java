package com.baeldung.lsd.persistence.repository;

import com.baeldung.lsd.persistence.model.Campaign;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class NamedQueryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CampaignRepository campaignRepository;

    @Test
    void givenNewCampaign_whenFindWithIdLessThan_thenGetExpectResult() {
        Campaign newCampaign = new Campaign("CTEST-1", "Test Campaign 1", "Description for campaign CTEST-1");

        entityManager.persist(newCampaign);
        long boundary = newCampaign.getId() + 1;
        List<Campaign> result = campaignRepository.findCampaignsWithIdLessThan(boundary);

        assertThat(result).isNotEmpty()
            .allMatch(campaign -> campaign.getId() < boundary);
    }

    @Test
    void givenCampaigns_whenFindWithDescPrefix_thenGetExpectResult() {
        Campaign newCampaign = new Campaign("CTEST-1", "Test Campaign 1", "Special: Description for campaign CTEST-1");
        Campaign newCampaign2 = new Campaign("CTEST-2", "Test Campaign 2", "------: Description for campaign CTEST-1");

        entityManager.persist(newCampaign);
        entityManager.persist(newCampaign2);

        List<Campaign> result = campaignRepository.findCampaignsWithDescriptionPrefix("Special:");
        assertThat(result).contains(newCampaign).doesNotContain(newCampaign2);
    }

    @Test
    void givenCampaigns_whenFindDescShorterThan_thenGetExpectResult() {
        Campaign newCampaign = new Campaign("CTEST-1", "Test Campaign 1", "1234");
        Campaign newCampaign2 = new Campaign("CTEST-2", "Test Campaign 2", "123");

        entityManager.persist(newCampaign);
        entityManager.persist(newCampaign2);
        List<Campaign> result = campaignRepository.findCampaignsWithDescriptionShorterThan(4);
        assertThat(result).contains(newCampaign2).doesNotContain(newCampaign);
    }

    @Test
    void givenCampaigns_whenUpdateDescById_thenGetExpectResult() {
        String updatedDescription = "updated description";
        Campaign newCampaign = new Campaign("CTEST-1", "Test Campaign 1", "original description");
        entityManager.persist(newCampaign);
        int result = campaignRepository.updateCampaignDescriptionById(newCampaign.getId(), updatedDescription);
        assertThat(result).isEqualTo(1);
        assertThat(entityManager.find(Campaign.class, newCampaign.getId())
            .getDescription()).isEqualTo(updatedDescription);
    }
}
