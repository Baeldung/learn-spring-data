package com.baeldung.lsd;

import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.TypedSort;

import com.baeldung.lsd.persistence.model.Task;
import com.baeldung.lsd.persistence.repository.TaskRepository;

@SpringBootApplication
public class DataSortingApp implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DataSortingApp.class);

    @Autowired
    private TaskRepository taskRepository;

    public static void main(final String... args) {
        SpringApplication.run(DataSortingApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        List<Task> tasks = taskRepository.findAllByOrderByDueDateDesc();
        LOG.info("All Tasks Ordered by Due Date Descending Order:");
        tasks.forEach(t -> LOG.info("{}", t));
        

        List<Task> tasksSortedByMultipleProps = taskRepository.findAllByOrderByDueDateDescAssigneeLastNameAsc();
        LOG.info("All tasks ordered by due date in descending order and assignee last name in Ascending order :");
        tasksSortedByMultipleProps.forEach(t -> LOG.info("{}", t));

        Sort sortByDueDateAssigneLastName = Sort.by(Direction.DESC, "dueDate")
            .and(Sort.by(Direction.ASC, "assignee.lastName"));

        List<Task> tasksSortedByDueDateAssigneLastName = taskRepository.findByNameContaining("Task", sortByDueDateAssigneLastName);
        LOG.info("All tasks ordered by due date in descending order and assignee last name in Ascending order using Sort Parameter :");
        tasksSortedByDueDateAssigneLastName.forEach(t -> LOG.info("{}", t));

        TypedSort<Task> taskTypedSort = Sort.sort(Task.class);

        Sort sortByDueDate = taskTypedSort.by(Task::getDueDate).descending();
        Sort sortByAssigneeLastName = taskTypedSort.by((Function<Task, String>)task -> task.getAssignee().getLastName()).ascending();
        Sort sortByAssigneeLastNameAndDueDate = sortByDueDate.and(sortByAssigneeLastName);
        
        List<Task> typedSortResult = taskRepository.findByNameContaining("Task", sortByAssigneeLastNameAndDueDate);
        LOG.info("All tasks ordered by due date in descending order and assignee last name in Ascending order using TypedSort :");
        typedSortResult.forEach(t -> LOG.info("{}", t));

        Sort unsortedTasks = Sort.unsorted();

        List<Task> unsortedTaskList = taskRepository.findByNameContaining("Task", unsortedTasks);
        LOG.info("All Tasks unsorted :");
        unsortedTaskList.forEach(t -> LOG.info("{}", t));
        
    }
}
