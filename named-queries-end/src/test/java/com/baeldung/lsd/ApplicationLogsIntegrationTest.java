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
    void whenApplicationIsStarted_thenExpectedNamedQueryCampaignsWithIdGreaterThanLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Find Campaigns with Id greater than 1 using EntityManager:\n"
                + "[Campaign [id=2, code=C2, name=Campaign 2, description=About Campaign 2], "
                + "Campaign [id=3, code=C3, name=Campaign 3, description=About Campaign 3]]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindCampaignsWithIdLessThanLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Find Campaigns with Id less than 3:\n"
                + "[Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], "
                + "Campaign [id=2, code=C2, name=Campaign 2, description=About Campaign 2]]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedUpdateCampaignDescriptionByIdLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("After updating the description of the Campaign(id=1):\n"
                + "Campaign [id=1, code=C1, name=Campaign 1, description=New description updated by named query]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindCampaignsWithDescriptionShorterThanLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Find Campaigns with description shorter than 17:\n"
                + "[Campaign [id=2, code=C2, name=Campaign 2, description=About Campaign 2], "
                + "Campaign [id=3, code=C3, name=Campaign 3, description=About Campaign 3]]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindCampaignsWithDescriptionPrefixLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Find Campaigns with Description Prefix (NamedQuery from properties file):\n"
                + "[Campaign [id=2, code=C2, name=Campaign 2, description=About Campaign 2], "
                + "Campaign [id=3, code=C3, name=Campaign 3, description=About Campaign 3]]"));
    }

    private Condition<ILoggingEvent> eventContains(String substring) {
        return new Condition<ILoggingEvent>(entry -> (substring == null || (entry.getFormattedMessage() != null && entry.getFormattedMessage()
            .contains(substring))), String.format("entry with message '%s'", substring));
    }
}
