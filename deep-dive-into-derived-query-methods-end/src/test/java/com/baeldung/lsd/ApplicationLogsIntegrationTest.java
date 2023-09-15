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
    void whenApplicationIsStarted_thenExpectedFindByNameStartingWithProjectLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("p1_0.name like ? escape '\\'"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Projects name starting with Project"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Project [id=1, code=P1, name=Project 1"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Project [id=2, code=P2, name=Project 2"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Project [id=3, code=P3, name=Project 3"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindByNameStartingWithPercentLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Projects name starting with \"%\""));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindByNameStartingWithEmptyStringLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Projects name starting with \"\""));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Project [id=1, code=P1, name=Project 1, description=Description of Project 1]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Project [id=2, code=P2, name=Project 2, description=About Project 2]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Project [id=3, code=P3, name=Project 3, description=About Project 3]"));
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
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Distinct projects with Task name containing \"Task\""));
    }

    private Condition<ILoggingEvent> eventContains(String substring) {
        return new Condition<ILoggingEvent>(entry -> (substring == null || (entry.getFormattedMessage() != null && entry.getFormattedMessage()
            .contains(substring))), String.format("entry with message '%s'", substring));
    }
}
