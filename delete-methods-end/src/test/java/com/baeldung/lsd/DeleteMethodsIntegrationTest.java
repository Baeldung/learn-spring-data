package com.baeldung.lsd;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.lsd.persistence.model.Campaign;
import com.baeldung.lsd.persistence.repository.CampaignRepository;
import com.baeldung.lsd.persistence.repository.TaskRepository;

@SpringBootTest
@Transactional
class DeleteMethodsIntegrationTest {

    @Autowired
    CampaignRepository campaignRepository;

    @Autowired
    TaskRepository taskRepository;

    @Test
    void givenRegularAppContext_whenDeleteExistingCampaignUsingReference_thenTotalCampaignCountIsReduced() {
        Campaign newCampaign = new Campaign("TEST-NEW1", "TEST-new Campaign", "TEST-new campaign description");
        newCampaign = campaignRepository.save(newCampaign);
        long totalCount1 = campaignRepository.count();

        // delete using reference
        campaignRepository.delete(newCampaign);

        long totalCount2 = campaignRepository.count();

        assertThat(totalCount2).isLessThan(totalCount1);
    }

    @Test
    void givenRegularAppContext_whenDeleteCampaignUsingId_thenTotalCampaignCountIsReduced() {
        Campaign newCampaign = new Campaign("TEST-NEW1", "TEST-new Campaign", "TEST-new campaign description");
        newCampaign = campaignRepository.save(newCampaign);

        long totalCount1 = campaignRepository.count();

        // delete using id
        campaignRepository.deleteById(newCampaign.getId());

        long totalCount2 = campaignRepository.count();

        assertThat(totalCount2).isLessThan(totalCount1);
    }

    @Test
    void givenRegularAppContext_whenDeleteSeveralCampaigns_thenTotalCampaignCountIsReduced() {
        Campaign newCampaign = new Campaign("TEST-NEW1", "TEST-new Campaign1", "TEST-new campaign1 description");
        newCampaign = campaignRepository.save(newCampaign);
        Campaign newCampaign2 = new Campaign("TEST-NEW2", "TEST-new Campaign2", "TEST-new campaign2 description");
        newCampaign2 = campaignRepository.save(newCampaign2);
        Iterable<Campaign> campaignsToDelete = campaignRepository.findAllById(List.of(newCampaign.getId(), newCampaign2.getId()));
        long totalCount1 = campaignRepository.count();

        // delete several campaigns
        campaignRepository.deleteAll(campaignsToDelete);

        long totalCount2 = campaignRepository.count();

        assertThat(totalCount2).isLessThan(totalCount1);
    }

    @Test
    void givenRegularAppContext_whenDeleteByNameNonMatchingCampaign_thenOutputCountIsZero() {
        // delete using custom query and with count
        Long deleteCount = campaignRepository.deleteByNameContaining("Non-matching name");

        assertThat(deleteCount).isZero();
    }

    @Test
    void givenRegularAppContext_whenDeleteByNameMatchingEverything_thenTotalCampaignAndTaskCountIsZero() {
        // delete using custom query
        campaignRepository.removeByNameContaining("Campaign");

        long finalCampaignCount = campaignRepository.count();
        long finaltaskCount = taskRepository.count();
        assertThat(finalCampaignCount).isZero();
        assertThat(finaltaskCount).isZero();
    }
}
