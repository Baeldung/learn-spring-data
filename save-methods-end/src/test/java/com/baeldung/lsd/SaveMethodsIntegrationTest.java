package com.baeldung.lsd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.lsd.persistence.model.Campaign;
import com.baeldung.lsd.persistence.model.Task;
import com.baeldung.lsd.persistence.repository.CampaignRepository;
import com.baeldung.lsd.persistence.repository.TaskRepository;

@DataJpaTest
@Transactional
class SaveMethodsIntegrationTest {

    @Autowired
    CampaignRepository campaignRepository;

    @Autowired
    TaskRepository taskRepository;

    @Test
    void givenRegularAppContext_whenSaveNewCampaign_thenGeneratedIdIsPopulated() {
        Campaign newCampaign = new Campaign("TEST-NEW1", "TEST-new Campaign", "TEST-new campaign description");
        assertThat(newCampaign.getId()).isNull();

        // save new entity
        newCampaign = campaignRepository.save(newCampaign);

        assertThat(newCampaign.getId()).isPositive();
    }

    @Test
    void givenRegularAppContext_whenUpdateCampaignAddingTasks_thenCampaignIsUpdatedAndTaskGeneratedIdIsPopulated() {
        Campaign newCampaign = new Campaign("TEST-NEW1", "TEST-new Campaign", "TEST-new campaign description");
        newCampaign = campaignRepository.save(newCampaign);
        newCampaign.setName("TEST-updated name");
        Set<Task> newCampaignTasks = new HashSet<>();
        newCampaignTasks.add(new Task("TEST-task name", "TEST-task description", LocalDate.of(2025, 1, 1), newCampaign));
        newCampaign.setTasks(newCampaignTasks);

        // update entity (has id)
        newCampaign = campaignRepository.save(newCampaign);

        Campaign persistedCampaign = campaignRepository.findById(newCampaign.getId())
            .get();
        assertThat(persistedCampaign.getName()).isEqualTo(newCampaign.getName());
        assertThat(newCampaign.getTasks()
            .iterator()
            .next()
            .getId()).isPositive();
    }

    @Test
    void givenRegularAppContext_whenUpdateAndPersistSeveralCampaigns_thenEverythingExecutedCorrectly() {
        Campaign newCampaign = new Campaign("TEST-NEW1", "TEST-new Campaign", "TEST-new campaign description");
        Set<Task> originalTasks = new HashSet<>();
        originalTasks.add(new Task("TEST-TASK1-name", "TEST-TASK1-description", LocalDate.of(2025, 1, 1), newCampaign));
        originalTasks.add(new Task("TEST-TASK2-name", "TEST-TASK2-description", LocalDate.of(2025, 1, 1), newCampaign));
        newCampaign.setTasks(originalTasks);
        newCampaign = campaignRepository.save(newCampaign);
        newCampaign.setName("TEST-updated again");
        Set<Task> differentTasks = newCampaign.getTasks();
        differentTasks.clear();
        differentTasks.add(new Task("TEST-different task", "TEST-different description", LocalDate.of(2025, 1, 1), newCampaign));
        newCampaign.setTasks(differentTasks);
        Campaign newCampaign2 = new Campaign("TEST-NEW2", "TEST-another Campaign", "TEST-another campaign description");
        Iterable<Campaign> severalCampaigns = Arrays.asList(newCampaign, newCampaign2);
        long totalCount1 = campaignRepository.count();

        // update existing campaigns and save new ones
        severalCampaigns = campaignRepository.saveAll(severalCampaigns);

        long totalCount2 = campaignRepository.count();
        assertThat(totalCount2).isGreaterThan(totalCount1);
        assertThat(campaignRepository.findById(newCampaign.getId())
            .map(Campaign::getTasks)
            .get()).hasSize(1);
    }

    @Test
    void givenRegularAppContext_whenSaveSeveralCampaignsWithDataIntegrityViolation_thenEverythingIsRollbacked() {
        assertThrows(DataIntegrityViolationException.class, () -> {
            Campaign newCampaign1 = new Campaign("TEST-NEW1", "TEST-new Campaign", "TEST-new campaign description");
            newCampaign1 = campaignRepository.save(newCampaign1);
            newCampaign1.setDescription("updated description");
            Campaign newCampaign2 = new Campaign("TEST-NEW1", "TEST-duplicate code!", "TEST-campaign with constraint violation");
            Iterable<Campaign> severalCampaigns2 = Arrays.asList(newCampaign1, newCampaign2);

            campaignRepository.saveAll(severalCampaigns2);
        });
    }
}
