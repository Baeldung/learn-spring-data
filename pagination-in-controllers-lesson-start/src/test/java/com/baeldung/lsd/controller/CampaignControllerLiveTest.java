package com.baeldung.lsd.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.lsd.persistence.model.Campaign;
import com.baeldung.lsd.persistence.repository.CampaignRepository;

@WebMvcTest(CampaignController.class)
class CampaignControllerLiveTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CampaignRepository campaignRepository;

    @Test
    void givenCampaigns_whenAccessCampaigns_thenSuccess() throws Exception {
        Campaign campaign1 = new Campaign(UUID.randomUUID()
            .toString(), "Campaign One", "Projet One Description");
        Campaign campaign2 = new Campaign(UUID.randomUUID()
            .toString(), "Campaign Two", "Projet Two Description");
        when(campaignRepository.findAll()).thenReturn(Arrays.asList(campaign1, campaign2));

        mockMvc.perform(get("/campaigns"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$.[0].name", is(campaign1.getName())))
            .andExpect(jsonPath("$.[1].name", is(campaign2.getName())));

    }

}
