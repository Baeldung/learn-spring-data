package com.baeldung.lsd.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.baeldung.lsd.persistence.model.AuditingData;
import com.baeldung.lsd.persistence.model.Campaign;

@SpringBootTest
@DirtiesContext
class CampaignAuditingDataIntegrationTest {

    @Autowired
    CampaignRepository campaignRepository;

    @Test
    void givenCampaignCreated_whenSave_thenAuditFieldsAutoSet() {
        Campaign newCampaign = new Campaign("CTEST-AUD1", "Test Campaign - Auditing Data 1", "Description for campaign CTEST-AUD1");

        AuditingData auditingData = campaignRepository.save(newCampaign)
            .getAuditingData();

        assertThat(auditingData).isNotNull();
        assertThat(auditingData.getCreatedDate()).isNotNull();
        assertThat(auditingData.getLastModifiedDate()).isNotNull();
        assertThat(auditingData.getCreatedBy()).isNotNull();
        assertThat(auditingData.getLastModifiedBy()).isNotNull();
    }

    @Test
    void givenCampaignCreated_whenUpdate_thenLastModifiedDateUpdated() {
        Campaign newCampaign = new Campaign("CTEST-AUD2", "Test Campaign  - Auditing Data 2", "Description for campaign CTEST-AUD2");
        newCampaign = campaignRepository.save(newCampaign);

        newCampaign.setName("Test Campaign Aud 2 updated");
        AuditingData auditingData = campaignRepository.save(newCampaign)
            .getAuditingData();

        assertThat(auditingData.getLastModifiedDate()).isAfter(auditingData.getCreatedDate());
    }
}