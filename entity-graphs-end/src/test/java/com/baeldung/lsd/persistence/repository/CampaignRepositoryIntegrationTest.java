package com.baeldung.lsd.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;

import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.baeldung.lsd.persistence.model.Campaign;
import com.baeldung.lsd.persistence.model.Task;

@DataJpaTest
class CampaignRepositoryIntegrationTest {

    @Autowired
    CampaignRepository campaignRepository;

    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private TransactionTemplate transactionTemplate;


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
    //Disable Transactions since we want to assert outside transaction
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void givenCampaigns_whenFindByNameContaining_thenAllFieldsLoad() {
        
        //execute within a transaction
        Iterable<Campaign> campaigns = transactionTemplate.execute((status) -> {
            return campaignRepository.findByNameContaining("Test");
        });
        
        //assert outside the transaction, if associations were loaded eagerly 
        campaigns.forEach(campaign -> {
            assertThat(campaign)
                // check if tasks association is loaded eagerly
                .returns(campaign.getTasks(), Campaign::getTasks);
            // check if task assignee is loaded eagerly
            campaign.getTasks().forEach(task -> assertThat(task).returns(task.getAssignee(), Task::getAssignee));
        });

    }
    
    @Test
    //Disable Transactions since we want to assert outside transaction
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void givenCampaignFoundByIdWithoutEGraphs_whenAccessAssociations_thenLazyException() {
       
        //execute within a transaction
       Optional<Campaign> campaignOpt = transactionTemplate.execute((status) -> {
         //attempt to load a campaign(findbyId not using entity graphs)
            return campaignRepository.findById(1l);
        });
        
       // assert outside the transaction, if associations were loaded lazily
       campaignOpt.ifPresent(campaign -> {
           // assert lazy initialization exception when tasks association is accessed outside of a transaction
           assertThatThrownBy(() -> {
               campaign.getTasks().isEmpty();
           }).isInstanceOf(LazyInitializationException.class);
       });

    }

}