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
    void whenApplicationIsStarted_thenExpectedFindByNameStartingWithCampaignLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("c1_0.name like ? escape '\\'"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Campaigns name starting:"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Campaign [id=1, code=C1, name=Campaign 1"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Campaign [id=2, code=C2, name=Campaign 2"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Campaign [id=3, code=C3, name=Campaign 3"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindByNameStartingWithPercentLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Campaigns name starting with \"%\""));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindByNameStartingWithEmptyStringLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Campaigns name starting with \"\""));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Campaign [id=2, code=C2, name=Campaign 2, description=About Campaign 2]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Campaign [id=3, code=C3, name=Campaign 3, description=About Campaign 3]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindByDueDateGreaterThanLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Number of Tasks due strictly after: \"2025-02-10\""));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindByDueDateGreaterThanEqualLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Number of Tasks due after: \"2025-02-10\""));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindByDueDateBeforeAndStatusEqualsLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Overdue Tasks"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindByAssigneeFirstNameLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Tasks assigned to John"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindDistinctByTasksNameContainingLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Distinct campaigns with Task name containing \"Task\""));
    }

    private Condition<ILoggingEvent> eventContains(String substring) {
        return new Condition<ILoggingEvent>(entry -> (substring == null || (entry.getFormattedMessage() != null && entry.getFormattedMessage()
            .contains(substring))), String.format("entry with message '%s'", substring));
    }
}
