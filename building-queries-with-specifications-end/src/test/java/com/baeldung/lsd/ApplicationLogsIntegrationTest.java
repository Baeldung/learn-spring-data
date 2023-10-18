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
    void whenApplicationIsStarted_thenExpectedFindAllIsTaskInProgressLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("All tasks in progress :"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=2, name=Task 2, description=Task 2 Description, dueDate=2025-02-10, status=IN_PROGRESS, "
                + "project=Project [id=1, code=P1, name=Project 1, description=Description of Project 1], assignee=null"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=4, name=Task 4, description=Task 4 Description, dueDate=2025-06-25, status=IN_PROGRESS, "
                + "project=Project [id=2, code=P2, name=Project 2, description=About Project 2], assignee=Worker [id=1, email=john@test.com, firstName=John, lastName=Doe]]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindAllInProgressTasksUnassignedWithinDatesResultsLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("All tasks within date range in progress and unassigned  :"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=2, name=Task 2, description=Task 2 Description, dueDate=2025-02-10, status=IN_PROGRESS, "
                + "project=Project [id=1, code=P1, name=Project 1, description=Description of Project 1], assignee=null"));
    }

    private Condition<ILoggingEvent> eventContains(String substring) {
        return new Condition<ILoggingEvent>(entry -> (substring == null || (entry.getFormattedMessage() != null && entry.getFormattedMessage()
            .contains(substring))), String.format("entry with message '%s'", substring));
    }
}
