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
    void whenApplicationIsStarted_thenExpectedFindAllByOrderByDueDateDescLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("All Tasks Ordered by Due Date Descending Order:"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=4, name=Task 4, description=Task 4 Description, dueDate=2025-06-25, status=TO_DO, "
                + "campaign=Campaign [id=2, code=C2, name=Campaign 2, description=About Campaign 2], assignee=Worker [id=1, email=john@test.com, firstName=John, lastName=Doe]]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=5, name=Copying Task, description=Copying Task 2 Description, dueDate=2025-03-17, status=TO_DO, "
                + "campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=Worker [id=2, email=smith@test.com, firstName=John, lastName=Smith]]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=6, name=Copying Task, description=Copying Task 2 Description, dueDate=2025-03-17, status=TO_DO, "
                + "campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=Worker [id=1, email=john@test.com, firstName=John, lastName=Doe]]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindAllByOrderByDueDateDescAssigneeLastNameAscLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("All tasks ordered by due date in descending order and assignee last name in Ascending order :"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=4, name=Task 4, description=Task 4 Description, dueDate=2025-06-25, status=TO_DO, "
                + "campaign=Campaign [id=2, code=C2, name=Campaign 2, description=About Campaign 2], assignee=Worker [id=1, email=john@test.com, firstName=John, lastName=Doe]]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=6, name=Copying Task, description=Copying Task 2 Description, dueDate=2025-03-17, status=TO_DO, "
                + "campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=Worker [id=1, email=john@test.com, firstName=John, lastName=Doe]]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=5, name=Copying Task, description=Copying Task 2 Description, dueDate=2025-03-17, status=TO_DO, "
                + "campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=Worker [id=2, email=smith@test.com, firstName=John, lastName=Smith]]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindByNameContainingAndSortByDueDateAssigneLastNameLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("All tasks ordered by due date in descending order and assignee last name in Ascending order using Sort Parameter :"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=4, name=Task 4, description=Task 4 Description, dueDate=2025-06-25, status=TO_DO, "
                + "campaign=Campaign [id=2, code=C2, name=Campaign 2, description=About Campaign 2], assignee=Worker [id=1, email=john@test.com, firstName=John, lastName=Doe]]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=6, name=Copying Task, description=Copying Task 2 Description, dueDate=2025-03-17, status=TO_DO, "
                + "campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=Worker [id=1, email=john@test.com, firstName=John, lastName=Doe]]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=5, name=Copying Task, description=Copying Task 2 Description, dueDate=2025-03-17, status=TO_DO, "
                + "campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=Worker [id=2, email=smith@test.com, firstName=John, lastName=Smith]]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindByNameContainingAndSortByAssigneeLastNameAndDueDateLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("All tasks ordered by due date in descending order and assignee last name in Ascending order using TypedSort :"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=4, name=Task 4, description=Task 4 Description, dueDate=2025-06-25, status=TO_DO, "
                + "campaign=Campaign [id=2, code=C2, name=Campaign 2, description=About Campaign 2], assignee=Worker [id=1, email=john@test.com, firstName=John, lastName=Doe]]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=6, name=Copying Task, description=Copying Task 2 Description, dueDate=2025-03-17, status=TO_DO, "
                + "campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=Worker [id=1, email=john@test.com, firstName=John, lastName=Doe]]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=5, name=Copying Task, description=Copying Task 2 Description, dueDate=2025-03-17, status=TO_DO, "
                + "campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=Worker [id=2, email=smith@test.com, firstName=John, lastName=Smith]]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedFindByNameContainingAndUnsortedTasksLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("All Tasks unsorted :"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=1, name=Task 1, description=Task 1 Description, dueDate=2025-01-12, status=TO_DO, "
                + "campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=null]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=2, name=Task 2, description=Task 2 Description, dueDate=2025-02-10, status=TO_DO, "
                + "campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=null]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=3, name=Task 3, description=Task 3 Description, dueDate=2025-03-16, status=TO_DO, "
                + "campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=null]"));
    }

    private Condition<ILoggingEvent> eventContains(String substring) {
        return new Condition<ILoggingEvent>(entry -> (substring == null || (entry.getFormattedMessage() != null && entry.getFormattedMessage()
            .contains(substring))), String.format("entry with message '%s'", substring));
    }
}
