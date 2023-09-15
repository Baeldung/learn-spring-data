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
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("find Project 3 using positional parameters:\n"
                + "[Project [id=3, code=P3, name=Project 3, description=About Project 3]]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindWithNameAndDescriptionNamedBindLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("find Project 3 using named parameters:\n"
                + "[Project [id=3, code=P3, name=Project 3, description=About Project 3]]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindWithCodeInLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("find Project 2 and Project 3 using IN clause:\n"
                + "[Project [id=2, code=P2, name=Project 2, description=About Project 2], Project [id=3, code=P3, name=Project 3, description=About Project 3]]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindWithDescriptionIsLikeLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("find Projects containing 'About' using LIKE clause:\n"
                + "[Project [id=2, code=P2, name=Project 2, description=About Project 2], Project [id=3, code=P3, name=Project 3, description=About Project 3]]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindWithDescriptionWithPrefixAndSuffixLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("find Project 3 using prefix and suffix in LIKE clause:\n"
                + "[Project [id=3, code=P3, name=Project 3, description=About Project 3]]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindWithDescriptionIsShorterThanLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("find Projects with short descriptions using Native query:\n"
                + "[Project [id=2, code=P2, name=Project 2, description=About Project 2], Project [id=3, code=P3, name=Project 3, description=About Project 3]]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindWithDescriptionWithPrefixLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("find all Projects by passing '%' to LIKE clause:\n"
                + "[Project [id=1, code=P1, name=Project 1, description=Description of Project 1], "
                + "Project [id=2, code=P2, name=Project 2, description=About Project 2], "
                + "Project [id=3, code=P3, name=Project 3, description=About Project 3]]"));
    }

    private Condition<ILoggingEvent> eventContains(String substring) {
        return new Condition<ILoggingEvent>(entry -> (substring == null || (entry.getFormattedMessage() != null && entry.getFormattedMessage()
            .contains(substring))), String.format("entry with message '%s'", substring));
    }
}
