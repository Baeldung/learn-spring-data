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
    void whenApplicationIsStarted_thenExpectedFindOneCampaignLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Campaign 1 output: Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindOneCaseInsensitiveLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Campaign 2 output: Campaign [id=2, code=C2, name=Campaign 2, description=About Campaign 2]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindAllLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Campaign list output: ["
                + "Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], "
                + "Campaign [id=2, code=C2, name=Campaign 2, description=About Campaign 2], "
                + "Campaign [id=3, code=C3, name=Campaign 3, description=About Campaign 3]]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindOneTaskLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Found Task: "
                + "Task [id=3, name=Task 3, description=Task 3 Description, dueDate=2025-03-16, status=TO_DO, "
                + "campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=null"));
    }

    private Condition<ILoggingEvent> eventContains(String substring) {
        return new Condition<ILoggingEvent>(entry -> (substring == null || (entry.getFormattedMessage() != null && entry.getFormattedMessage()
            .contains(substring))), String.format("entry with message '%s'", substring));
    }
}
