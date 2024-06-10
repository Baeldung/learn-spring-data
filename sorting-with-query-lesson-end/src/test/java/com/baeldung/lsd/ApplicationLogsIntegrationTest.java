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
    void whenApplicationIsStarted_thenExpectedAllTasksSortedByDueDateLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("All Tasks sorted by due date descending order :"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=4, name=Task 4, description=Task 4 Description, dueDate=2025-06-25, status=TO_DO, "
                + "campaign=Campaign [id=2, code=C2, name=Campaign 2, description=About Campaign 2], assignee=Worker [id=1, email=john@test.com, firstName=John, lastName=Doe]]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=5, name=Copying Task, description=Copying Task 2 Description, dueDate=2025-03-17, status=TO_DO, "
                + "campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=Worker [id=2, email=smith@test.com, firstName=John, lastName=Smith]]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=6, name=Copying Task, description=Copying Task 2 Description, dueDate=2025-03-17, status=TO_DO, "
                + "campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=Worker [id=1, email=john@test.com, firstName=John, lastName=Doe]]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedAllTasksLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("All Tasks sorted by due date descending order:"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=4, name=Task 4, description=Task 4 Description, dueDate=2025-06-25, status=TO_DO, "
                + "campaign=Campaign [id=2, code=C2, name=Campaign 2, description=About Campaign 2], assignee=Worker [id=1, email=john@test.com, firstName=John, lastName=Doe]]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=5, name=Copying Task, description=Copying Task 2 Description, dueDate=2025-03-17, status=TO_DO, "
                + "campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=Worker [id=2, email=smith@test.com, firstName=John, lastName=Smith]]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=6, name=Copying Task, description=Copying Task 2 Description, dueDate=2025-03-17, status=TO_DO, "
                + "campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=Worker [id=1, email=john@test.com, firstName=John, lastName=Doe]]"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedAllTasksSortedByDueDateAndSortByAsigneeLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("All Tasks sorted by due date descending order and assignee last name in descending order:"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=4, name=Task 4, description=Task 4 Description, dueDate=2025-06-25, status=TO_DO, "
                + "campaign=Campaign [id=2, code=C2, name=Campaign 2, description=About Campaign 2], assignee=Worker [id=1, email=john@test.com, firstName=John, lastName=Doe]]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=5, name=Copying Task, description=Copying Task 2 Description, dueDate=2025-03-17, status=TO_DO, "
                + "campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=Worker [id=2, email=smith@test.com, firstName=John, lastName=Smith]]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=6, name=Copying Task, description=Copying Task 2 Description, dueDate=2025-03-17, status=TO_DO, "
                + "campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=Worker [id=1, email=john@test.com, firstName=John, lastName=Doe]]"));

        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("join worker a1_0 on a1_0.id=t1_0.assignee_id order by t1_0.due_date desc,a1_0.last_name desc"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedAllTasksSortedByDueDateDescLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("All Tasks sorted by due date descending order with native query:"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=4, name=Task 4, description=Task 4 Description, dueDate=2025-06-25, status=TO_DO, "
                + "campaign=Campaign [id=2, code=C2, name=Campaign 2, description=About Campaign 2], assignee=Worker [id=1, email=john@test.com, firstName=John, lastName=Doe]]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=5, name=Copying Task, description=Copying Task 2 Description, dueDate=2025-03-17, status=TO_DO, "
                + "campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=Worker [id=2, email=smith@test.com, firstName=John, lastName=Smith]]"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Task [id=6, name=Copying Task, description=Copying Task 2 Description, dueDate=2025-03-17, status=TO_DO, "
                + "campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=Worker [id=1, email=john@test.com, firstName=John, lastName=Doe]]"));

        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("select * from Task t order by t.due_date desc"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("from worker w1_0 where w1_0.id=?"));
    }

    private Condition<ILoggingEvent> eventContains(String substring) {
        return new Condition<ILoggingEvent>(entry -> (substring == null || (entry.getFormattedMessage() != null && entry.getFormattedMessage()
            .contains(substring))), String.format("entry with message '%s'", substring));
    }
}
