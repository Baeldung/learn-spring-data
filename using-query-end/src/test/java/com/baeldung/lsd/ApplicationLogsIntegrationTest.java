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
    void whenApplicationIsStarted_thenExpectedFindWithNameAndDescriptionLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Project 3:\n[Project [id=3, code=P3, name=Project 3, description=About Project 3]]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindNameByCodeLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Project Name:\nProject 1"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedCountByDueYearLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Tasks count by due years:\n Count \t year"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindSingleProjectLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Single Project:\nProject [id=1, code=P1, name=Project 1, description=Description of Project 1]"));
    }

    private Condition<ILoggingEvent> eventContains(String substring) {
        return new Condition<ILoggingEvent>(entry -> (substring == null || (entry.getFormattedMessage() != null && entry.getFormattedMessage()
            .contains(substring))), String.format("entry with message '%s'", substring));
    }
}
