package com.baeldung.lsd.infrastructure.event.listener;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.baeldung.lsd.domain.task.model.TaskUpdated;
import com.baeldung.lsd.infrastructure.campaign.listener.CampaignEventListeners;
import com.baeldung.lsd.persistence.model.Campaign;
import com.baeldung.lsd.persistence.model.Task;
import com.baeldung.lsd.persistence.repository.CampaignRepository;
import com.baeldung.lsd.persistence.repository.TaskRepository;

@SpringBootTest
class TaskEventListenerIntegrationTest {

    @MockitoBean
    private CampaignEventListeners taskEventListener;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void whenAddTasktoCampaign_thenDomainEventTriggered() {
        Campaign campaign = campaignRepository.findById(1L)
            .get();

        taskRepository.save(new Task("Sample Name", "Sample Description", LocalDate.now(), campaign));

        verify(taskEventListener).taskUpdatedListener(any(TaskUpdated.class));
    }

}
