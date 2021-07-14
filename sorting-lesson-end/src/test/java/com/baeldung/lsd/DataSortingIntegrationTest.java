package com.baeldung.lsd;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.baeldung.lsd.persistence.model.Task;
import com.baeldung.lsd.persistence.repository.TaskRepository;

@DataJpaTest
class DataSortingIntegrationTest {

    @Autowired
    TaskRepository taskRepository;

    @Test
    void givenMethodDerivedQuery_whenSortedByDueDateDesc_thenSuccess() {
        List<Task> dbSortedTasks = taskRepository.findAllByOrderByDueDateDesc();

        assertThat(dbSortedTasks).isNotNull()
            .isSortedAccordingTo(Comparator.comparing(Task::getDueDate, Comparator.nullsLast(Comparator.reverseOrder())));
    }

    @Test
    void givenMethodDerivedQuery_whenSortedByDueDateAndAssigneeLastName_thenSuccess() {
        List<Task> dbSortedTasks = taskRepository.findAllByOrderByDueDateDescAssigneeLastNameAsc();

        assertThat(dbSortedTasks).isSortedAccordingTo(Comparator.comparing(Task::getDueDate, Comparator.nullsLast(Comparator.reverseOrder()))
            .thenComparing(task -> task.getAssignee()
                .getLastName()));
    }

}
