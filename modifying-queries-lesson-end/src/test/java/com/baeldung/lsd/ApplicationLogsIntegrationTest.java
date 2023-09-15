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
    void whenApplicationIsStarted_thenExpectedDeleteCompletedTasksLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Number of Records Deleted :\n 1"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedDeletedFindByIdLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Completed Task :\n Optional.empty"));
    }

    private Condition<ILoggingEvent> eventContains(String substring) {
        return new Condition<ILoggingEvent>(entry -> (substring == null || (entry.getFormattedMessage() != null && entry.getFormattedMessage()
            .contains(substring))), String.format("entry with message '%s'", substring));
    }
}
