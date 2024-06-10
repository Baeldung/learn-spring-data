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
    void whenApplicationIsStarted_thenExpectedFindClosedByNameContainingLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("id: 1, name: Campaign 1"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("id: 2, name: Campaign 2"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("id: 3, name: Campaign 3"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindByFirstNameLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("worker: id 1, full name: John Doe"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindClassByNameContainingLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("campaign: CampaignClass [id=1, name=Campaign 1]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("campaign: CampaignClass [id=2, name=Campaign 2]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("campaign: CampaignClass [id=3, name=Campaign 3]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedGetCampaignStatisticsLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("campaign statistics: id: 1, name: Campaign 1, tasks: 3"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("campaign statistics: id: 2, name: Campaign 2, tasks: 1"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("campaign statistics: id: 3, name: Campaign 3, tasks: 0"));
    }

    private Condition<ILoggingEvent> eventContains(String substring) {
        return new Condition<ILoggingEvent>(entry -> (substring == null || (entry.getFormattedMessage() != null && entry.getFormattedMessage()
            .contains(substring))), String.format("entry with message '%s'", substring));
    }
}
