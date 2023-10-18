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
    void whenApplicationIsStarted_thenExpectedFindOneProjectLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Project 1 output: Project [id=1, code=P1, name=Project 1, description=Description of Project 1]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindOneCaseInsensitiveLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Project 2 output: Project [id=2, code=P2, name=Project 2, description=About Project 2]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindAllLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Project list output: ["
                + "Project [id=1, code=P1, name=Project 1, description=Description of Project 1], "
                + "Project [id=2, code=P2, name=Project 2, description=About Project 2], "
                + "Project [id=3, code=P3, name=Project 3, description=About Project 3]]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindOneTaskLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Found Task: "
                + "Task [id=3, name=Task 3, description=Task 3 Description, dueDate=2025-03-16, status=TO_DO, "
                + "project=Project [id=1, code=P1, name=Project 1, description=Description of Project 1], assignee=null"));
    }

    private Condition<ILoggingEvent> eventContains(String substring) {
        return new Condition<ILoggingEvent>(entry -> (substring == null || (entry.getFormattedMessage() != null && entry.getFormattedMessage()
            .contains(substring))), String.format("entry with message '%s'", substring));
    }
}
