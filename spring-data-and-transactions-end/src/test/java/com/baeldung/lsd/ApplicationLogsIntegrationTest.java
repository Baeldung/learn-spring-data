package com.baeldung.lsd;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.lsd.utils.LoggerListAppender;

import ch.qos.logback.classic.spi.ILoggingEvent;

@SpringBootTest
class ApplicationLogsIntegrationTest {

    @BeforeAll
    public static void clearLogList() {
        LoggerListAppender.clearEventList();
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindByIdLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Creating new transaction with name [org.springframework.data.jpa.repository.support.SimpleJpaRepository.findById]: "
                + "PROPAGATION_REQUIRED,ISOLATION_DEFAULT,readOnly"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Getting transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.findById]"));
        //assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("select p1_0.id,p1_0.code,p1_0.description"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Completing transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.findById]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Initiating transaction commit"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Committing JPA transaction on EntityManager [SessionImpl"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedEndCampaignLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Creating new transaction with name [com.baeldung.lsd.service.CampaignService.endCampaign]: "
                + "PROPAGATION_REQUIRED,ISOLATION_DEFAULT"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Getting transaction for [com.baeldung.lsd.service.CampaignService.endCampaign]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Participating in existing transaction"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Completing transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.findById]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Getting transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("insert into campaign (code,description,name,id) values (?,?,?,default)"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Completing transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]"));

        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Completing transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.saveAll]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Completing transaction for [com.baeldung.lsd.service.CampaignService.endCampaign] after exception: java.io.IOError"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Initiating transaction commit"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Committing JPA transaction on EntityManager [SessionImpl"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Closing JPA EntityManager [SessionImpl"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Expected Error thrown"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindByNameContainingLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Continuation Campaign Tasks:"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=2, name=Task 2, description=Task 2 Description, dueDate=2025-02-10, status=TO_DO, "
                + "campaign=Campaign [id=4, code=C1-CONT, name=Campaign 1 - Cont, description=Description of Campaign 1], assignee=null]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=1, name=Task 1, description=Task 1 Description, dueDate=2025-01-12, status=TO_DO, "
                + "campaign=Campaign [id=4, code=C1-CONT, name=Campaign 1 - Cont, description=Description of Campaign 1], assignee=null]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=3, name=Task 3, description=Task 3 Description, dueDate=2025-03-16, status=TO_DO, "
                + "campaign=Campaign [id=4, code=C1-CONT, name=Campaign 1 - Cont, description=Description of Campaign 1], assignee=null]"));
    }

    private Condition<ILoggingEvent> eventContains(String substring) {
        return new Condition<ILoggingEvent>(entry -> (substring == null || (entry.getFormattedMessage() != null && entry.getFormattedMessage()
            .contains(substring))), String.format("entry with message '%s'", substring));
    }
}
