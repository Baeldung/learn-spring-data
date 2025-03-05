package com.baeldung.lsd.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.doThrow;

import java.io.IOError;
import java.time.LocalDate;
import java.util.Collections;

import jakarta.persistence.EntityManagerFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.baeldung.lsd.persistence.model.Campaign;
import com.baeldung.lsd.persistence.model.Task;
import com.baeldung.lsd.persistence.model.TaskStatus;
import com.baeldung.lsd.persistence.repository.TaskRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CampaignServiceIntegrationTest {

    @Autowired
    private CampaignService campaignService;

    @MockitoSpyBean
    private TaskRepository taskRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private PlatformTransactionManager transactionManager;

    private TestEntityManager entityManager;

    private TransactionTemplate transactionTemplate;

    @BeforeEach
    void setUp() {
        entityManager = new TestEntityManager(entityManagerFactory);
        transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Test
    void givenIOError_whenEndCampaignInvoked_thenNoRollback() {
        Mockito.reset(taskRepository);
        Campaign persistedNewCampaign = transactionTemplate.execute((status) -> {
            Campaign newCampaign = new Campaign("CTEST-1", "Test Campaign 1", "Description for campaign CTEST-1");
            Task newTask = new Task("TTEST-1", "Test Task 1", LocalDate.of(2025, 1, 1), newCampaign, TaskStatus.TO_DO);
            newCampaign.setTasks(Collections.singleton(newTask));
            return entityManager.persist(newCampaign);
        });

        Throwable throwable = catchThrowable(() -> {
            campaignService.endCampaign(persistedNewCampaign);
        });

        Campaign continuationCampaign = transactionTemplate.execute((status) -> {
            return entityManager.find(Campaign.class, persistedNewCampaign.getId() + 1);
        });
        assertThat(throwable).isInstanceOf(IOError.class);
        assertThat(continuationCampaign.getTasks()).extracting(Task::getName)
            .containsOnly("TTEST-1");
    }

    @Test
    void givenRuntimeException_whenEndCampaignInvoked_theRollback() {
        Campaign persistedNewCampaign = transactionTemplate.execute((status) -> {
            Campaign newCampaign = new Campaign("CTEST-2", "Test Campaign 2", "Description for campaign CTEST-2");
            Task newTask = new Task("TTEST-2", "Test Task 2", LocalDate.of(2025, 1, 1), newCampaign, TaskStatus.TO_DO);
            newCampaign.setTasks(Collections.singleton(newTask));
            return entityManager.persist(newCampaign);
        });
        doThrow(RuntimeException.class).when(taskRepository)
            .saveAll(ArgumentMatchers.anyCollection());

        Throwable throwable = catchThrowable(() -> {
            campaignService.endCampaign(persistedNewCampaign);
        });

        Campaign originalCampaign = transactionTemplate.execute((status) -> {
            return entityManager.find(Campaign.class, persistedNewCampaign.getId());
        });
        Campaign continuationCampaign = transactionTemplate.execute((status) -> {
            return entityManager.find(Campaign.class, persistedNewCampaign.getId() + 1);
        });
        assertThat(throwable).isInstanceOf(RuntimeException.class);
        assertThat(continuationCampaign).isNull();
        assertThat(originalCampaign.getTasks()).extracting(Task::getName)
            .containsOnly("TTEST-2");
    }

}
