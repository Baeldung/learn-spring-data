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
    void whenApplicationIsStarted_thenExpectedFindByStatusLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("All Tasks by status Paginated:\n "
                + "[Task [id=1, name=Task 1, description=Task 1 Description, dueDate=2025-01-12, status=TO_DO, campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=null], "
                + "Task [id=2, name=Task 2, description=Task 2 Description, dueDate=2025-02-10, status=TO_DO, campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=null]]"));

        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Total No Of Tasks :\n 4"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Total Pages of Tasks :\n 2"));

        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("select count(t1_0.id) from task t1_0 where t1_0.status=?"));
    }

    @Test
    void whenApplicationIsStarted_thenExpectedfindByNameLikeLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("All Tasks Sliced :\n "
                + "[Task [id=1, name=Task 1, description=Task 1 Description, dueDate=2025-01-12, status=TO_DO, campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=null], "
                + "Task [id=2, name=Task 2, description=Task 2 Description, dueDate=2025-02-10, status=TO_DO, campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=null]]"));

        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Are there Slices Prior :\n false"));
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Are there Slices After :\n true"));

        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("where t1_0.name like ? escape '\\' fetch first ? rows only"));
        
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Tasks in next page :\n "
            + "[Task [id=3, name=Task 3, description=Task 3 Description, dueDate=2025-03-16, status=TO_DO, campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=null], "
            + "Task [id=4, name=Task 4, description=Task 4 Description, dueDate=2025-06-25, status=TO_DO, campaign=Campaign [id=2, code=C2, name=Campaign 2, description=About Campaign 2], assignee=Worker [id=1, email=john@test.com, firstName=John, lastName=Doe]]]"));

        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("where t1_0.name like ? escape '\\' offset ? rows fetch first ? rows only"));

    }

    @Test
    void whenApplicationIsStarted_thenExpectedAllTasksByNameLogs() throws InterruptedException {
        Thread.sleep(500);
        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("Page Two of All Tasks By Name:\n "
                + "[Task [id=3, name=Task 3, description=Task 3 Description, dueDate=2025-03-16, status=TO_DO, campaign=Campaign [id=1, code=C1, name=Campaign 1, description=Description of Campaign 1], assignee=null], "
                + "Task [id=4, name=Task 4, description=Task 4 Description, dueDate=2025-06-25, status=TO_DO, campaign=Campaign [id=2, code=C2, name=Campaign 2, description=About Campaign 2], assignee=Worker [id=1, email=john@test.com, firstName=John, lastName=Doe]]]"));

        assertThat(LoggerListAppender.getEvents()).haveAtLeastOne(eventContains("where t1_0.name like ? escape '' offset ? rows fetch first ? rows only"));
    }

    private Condition<ILoggingEvent> eventContains(String substring) {
        return new Condition<ILoggingEvent>(entry -> (substring == null || (entry.getFormattedMessage() != null && entry.getFormattedMessage()
            .contains(substring))), String.format("entry with message '%s'", substring));
    }
}
