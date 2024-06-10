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
    void whenApplicationIsStarted_thenExpectedFindWithNameAndDescriptionPositionalBindLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("find Campaign 3 using positional parameters:\n"
                + "[Campaign [id=3, code=C3, name=Campaign 3, description=About Campaign 3]]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindWithNameAndDescriptionNamedBindLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("find Campaign 3 using named parameters:\n"
                + "[Campaign [id=3, code=C3, name=Campaign 3, description=About Campaign 3]]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindWithCodeInLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("find Campaign 2 and Campaign 3 using IN clause:\n"
                + "[Campaign [id=2, code=C2, name=Campaign 2, description=About Campaign 2], Campaign [id=3, code=C3, name=Campaign 3, description=About Campaign 3]]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindWithDescriptionIsLikeLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("find Campaigns containing 'About' using LIKE clause:\n"
                + "[Campaign [id=2, code=C2, name=Campaign 2, description=About Campaign 2], Campaign [id=3, code=C3, name=Campaign 3, description=About Campaign 3]]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindWithDescriptionWithPrefixAndSuffixLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("find Campaign 3 using prefix and suffix in LIKE clause:\n"
                + "[Campaign [id=3, code=C3, name=Campaign 3, description=About Campaign 3]]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindWithDescriptionIsShorterThanLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("find Campaigns with short descriptions using Native query:\n"
                + "[Campaign [id=2, code=C2, name=Campaign 2, description=About Campaign 2], Campaign [id=3, code=C3, name=Campaign 3, description=About Campaign 3]]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindWithDescriptionWithPrefixLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("find all Campaigns by passing '%' to LIKE clause:\n"
                + "[Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], "
                + "Campaign [id=2, code=C2, name=Campaign 2, description=About Campaign 2], "
                + "Campaign [id=3, code=C3, name=Campaign 3, description=About Campaign 3]]"));
    }

    private Condition<ILoggingEvent> eventContains(String substring) {
        return new Condition<ILoggingEvent>(entry -> (substring == null || (entry.getFormattedMessage() != null && entry.getFormattedMessage()
            .contains(substring))), String.format("entry with message '%s'", substring));
    }
}
