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
    void whenApplicationIsStarted_thenExpectedFindAllTasksFirstPageLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Page 1 of All Tasks:"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=1, name=Task 1, description=Task 1 Description, dueDate=2025-01-12, status=TO_DO, "
                + "campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=null]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=2, name=Task 2, description=Task 2 Description, dueDate=2025-02-10, status=TO_DO, "
                + "campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=null]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindAllTasksSecondPageLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Page 2 of All Tasks:"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=3, name=Task 3, description=Task 3 Description, dueDate=2025-03-16, status=TO_DO, "
                + "campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=null]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=4, name=Task 4, description=Task 4 Description, dueDate=2025-06-25, status=TO_DO, "
                + "campaign=Campaign [id=2, code=C2, name=Campaign 2, description=About Campaign 2], assignee=Worker [id=1, email=john@test.com, firstName=John, lastName=Doe]]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindAllSortByTaskNameDscLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("All Tasks Sorted By Name in Descending Order:"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=4, name=Task 4, description=Task 4 Description, dueDate=2025-06-25, status=TO_DO, "
                + "campaign=Campaign [id=2, code=C2, name=Campaign 2, description=About Campaign 2], assignee=Worker [id=1, email=john@test.com, firstName=John, lastName=Doe]]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=3, name=Task 3, description=Task 3 Description, dueDate=2025-03-16, status=TO_DO, "
                + "campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=null"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=2, name=Task 2, description=Task 2 Description, dueDate=2025-02-10, status=TO_DO, "
                + "campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=null"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=1, name=Task 1, description=Task 1 Description, dueDate=2025-01-12, status=TO_DO, "
                + "campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=null"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindAllTasksFirstPageSortedByNameDscLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Page 1 of All Tasks Sorted by Name in Descending Order:"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=4, name=Task 4, description=Task 4 Description, dueDate=2025-06-25, status=TO_DO, "
                + "campaign=Campaign [id=2, code=C2, name=Campaign 2, description=About Campaign 2], assignee=Worker [id=1, email=john@test.com, firstName=John, lastName=Doe]]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=3, name=Task 3, description=Task 3 Description, dueDate=2025-03-16, status=TO_DO, "
                + "campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=null"));
    }

    private Condition<ILoggingEvent> eventContains(String substring) {
        return new Condition<ILoggingEvent>(entry -> (substring == null || (entry.getFormattedMessage() != null && entry.getFormattedMessage()
            .contains(substring))), String.format("entry with message '%s'", substring));
    }
}
