package com.baeldung.lsd.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.io.IOError;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.baeldung.lsd.persistence.model.Campaign;
import com.baeldung.lsd.persistence.model.Task;
import com.baeldung.lsd.persistence.model.TaskStatus;
import com.baeldung.lsd.persistence.repository.CampaignRepository;
import com.baeldung.lsd.persistence.repository.TaskRepository;

@ExtendWith(MockitoExtension.class)
class CampaignServiceUnitTest {
    
    @InjectMocks
    private CampaignService campaignService;
    
    @Mock
    private TaskRepository taskRepository;
    
    @Mock
    private CampaignRepository campaignRepository;
    
    private Campaign campaign;
    
    private Task task;
    
    @BeforeEach
    void setMockOutput() {
        campaign = new Campaign("Mock Code", "Mock Name", "Mock Description");
        campaign.setId(1L);
        
        task = new Task("Mock Task", "Mock Description", null, campaign, TaskStatus.IN_PROGRESS);
        task.setId(1L);
        task.setCampaign(campaign);
        campaign.setTasks(Set.<Task>of(task));
        
    }
    
    @Test
    void givenOverDueTasks_whenEndCampaign_thenException() {
        
        Throwable throwable = catchThrowable(() -> {
            campaignService.endCampaign(campaign);
        });
        
        assertThat(throwable).isInstanceOf(IOError.class);
        assertThat(task.getCampaign()).isNotNull().isNotEqualTo(campaign);
        assertThat(task.getCampaign().getName()).contains(" - Cont");
        assertThat(task.getCampaign().getCode()).contains("-CONT");
    }

}
