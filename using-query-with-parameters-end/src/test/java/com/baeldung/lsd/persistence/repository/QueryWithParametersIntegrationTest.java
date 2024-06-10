package com.baeldung.lsd.persistence.repository;

import com.baeldung.lsd.persistence.model.Campaign;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class QueryWithParametersIntegrationTest {

    @Autowired
    CampaignRepository campaignRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void whenFindByCodeIn_thenReturnExpectedResult() {

        Campaign newCampaign1 = new Campaign("CTEST-1", "Test Campaign 1", "Description for campaign CTEST-1");
        Campaign newCampaign2 = new Campaign("CTEST-2", "Test Campaign 2", "Description for campaign CTEST-2");
        entityManager.persist(newCampaign1);
        entityManager.persist(newCampaign2);

        List<Campaign> result = campaignRepository.findWithCodeIn(Set.of("CTEST-1", "CTEST-2"));
        assertThat(result).contains(newCampaign1, newCampaign2);
    }

    @Test
    void whenFindByNameAndDescriptionPositionalBind_thenReturnExpectedResult() {
        Campaign newCampaign1 = new Campaign("CTEST-1", "Test Campaign 1", "Description for campaign CTEST-1");
        Campaign newCampaign2 = new Campaign("CTEST-2", "Test Campaign 2", "Description for campaign CTEST-2");
        entityManager.persist(newCampaign1);
        entityManager.persist(newCampaign2);

        List<Campaign> result = campaignRepository.findWithNameAndDescriptionPositionalBind("Test Campaign 1", "Description for campaign CTEST-1");
        assertThat(result).containsOnly(newCampaign1);
    }

    @Test
    void whenFindByNameAndDescriptionNamedBind_thenReturnExpectedResult() {
        Campaign newCampaign1 = new Campaign("CTEST-1", "Test Campaign 1", "Description for campaign CTEST-1");
        Campaign newCampaign2 = new Campaign("CTEST-2", "Test Campaign 2", "Description for campaign CTEST-2");
        entityManager.persist(newCampaign1);
        entityManager.persist(newCampaign2);

        List<Campaign> result = campaignRepository.findWithNameAndDescriptionNamedBind("Description for campaign CTEST-1", "Test Campaign 1");
        assertThat(result).containsOnly(newCampaign1);
    }

    @Test
    void whenFindByDescriptionIsLike_thenReturnExpectedResult() {
        Campaign newCampaign1 = new Campaign("CTEST-1", "Test Campaign 1", "Description for campaign CTEST-1");
        Campaign newCampaign2 = new Campaign("CTEST-2", "Test Campaign 2", "Description for campaign CTEST-2");
        entityManager.persist(newCampaign1);
        entityManager.persist(newCampaign2);

        List<Campaign> result = campaignRepository.findWithDescriptionIsLike("for");
        assertThat(result).containsOnly(newCampaign1, newCampaign2);
    }

    @Test
    void whenFindByDescriptionIsShorterThan_thenReturnExpectedResult() {
        Campaign newCampaign1 = new Campaign("CTEST-1", "Test Campaign 1", "12345678");
        Campaign newCampaign2 = new Campaign("CTEST-2", "Test Campaign 2", "12345");
        Campaign newCampaign3 = new Campaign("CTEST-3", "Test Campaign 3", "12345");
        entityManager.persist(newCampaign1);
        entityManager.persist(newCampaign2);
        entityManager.persist(newCampaign3);

        List<Campaign> result = campaignRepository.findWithDescriptionIsShorterThan(6);
        assertThat(result).containsOnly(newCampaign2, newCampaign3);

    }

    @Test
    void whenFindByDescriptionWthPrefixAndSuffix_thenReturnExpectedResult() {
        Campaign newCampaign1 = new Campaign("CTEST-1", "Test Campaign 1", "PRE Description for campaign CTEST-1 SUFFIX");
        Campaign newCampaign2 = new Campaign("CTEST-2", "Test Campaign 2", "PRE Description for campaign CTEST-2 BAR");
        entityManager.persist(newCampaign1);
        entityManager.persist(newCampaign2);

        List<Campaign> result = campaignRepository.findWithDescriptionWithPrefixAndSuffix("PRE", "SUFFIX");
        assertThat(result).containsOnly(newCampaign1);
    }

    @Test
    void whenFindByDescriptionWthPrefixAndSuffixWithInjection_thenReturnExpectedResult() {
        Campaign newCampaign1 = new Campaign("CTEST-1", "Test Campaign 1", "PRE Description for campaign CTEST-1 SUFFIX");
        Campaign newCampaign2 = new Campaign("CTEST-2", "Test Campaign 2", "PRE Description for campaign CTEST-2 BAR");
        entityManager.persist(newCampaign1);
        entityManager.persist(newCampaign2);

        List<Campaign> result = campaignRepository.findWithDescriptionWithPrefix("%");
        assertThat(result).hasSize(5);
    }
}
