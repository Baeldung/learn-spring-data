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
    void whenApplicationIsStarted_thenExpectedNamedQueryProjectsWithIdGreaterThanLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Find Projects with Id greater than 1 using EntityManager:\n"
                + "[Project [id=2, code=P2, name=Project 2, description=About Project 2], "
                + "Project [id=3, code=P3, name=Project 3, description=About Project 3]]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindProjectsWithIdLessThanLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Find Projects with Id less than 3:\n"
                + "[Project [id=1, code=P1, name=Project 1, description=Description of Project 1], "
                + "Project [id=2, code=P2, name=Project 2, description=About Project 2]]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedUpdateProjectDescriptionByIdLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("After updating the description of the Project(id=1):\n"
                + "Project [id=1, code=P1, name=Project 1, description=New description updated by named query]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindProjectsWithDescriptionShorterThanLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Find Projects with description shorter than 16:\n"
                + "[Project [id=2, code=P2, name=Project 2, description=About Project 2], "
                + "Project [id=3, code=P3, name=Project 3, description=About Project 3]]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindProjectsWithDescriptionPrefixLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Find Projects with Description Prefix (NamedQuery from properties file):\n"
                + "[Project [id=2, code=P2, name=Project 2, description=About Project 2], "
                + "Project [id=3, code=P3, name=Project 3, description=About Project 3]]"));
    }

    private Condition<ILoggingEvent> eventContains(String substring) {
        return new Condition<ILoggingEvent>(entry -> (substring == null || (entry.getFormattedMessage() != null && entry.getFormattedMessage()
            .contains(substring))), String.format("entry with message '%s'", substring));
    }
}
