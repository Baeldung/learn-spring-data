package com.baeldung.lsd;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.baeldung.lsd.persistence.model.Task;
import com.baeldung.lsd.persistence.repository.TaskRepository;

@SpringBootApplication
public class SortingWithQueryApp implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(SortingWithQueryApp.class);

    @Autowired
    private TaskRepository taskRepository;

    public static void main(final String... args) {
        SpringApplication.run(SortingWithQueryApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        List<Task> customQueryResults = taskRepository.allTasksSortedByDueDate();

        LOG.info("All Tasks sorted by due date descending order :");
        customQueryResults.forEach(t -> LOG.info("{}", t));

        Sort sortByDueDateDesc = Sort.by(new Sort.Order(Direction.DESC, "dueDate").nullsFirst());

        List<Task> customQueryWithSortParamResults = taskRepository.allTasks(sortByDueDateDesc);

        LOG.info("All Tasks sorted by due date descending order:");
        customQueryWithSortParamResults.forEach(t -> LOG.info("{}", t));

        Sort sortByAsignee = Sort.by(Direction.DESC, "assignee.lastName");

        List<Task> queryWithOrderByAndSortParamResults = taskRepository.allTasksSortedByDueDate(sortByAsignee);

        LOG.info("All Tasks sorted by due date descending order and assignee last name in descending order:");
        queryWithOrderByAndSortParamResults.forEach(t -> LOG.info("{}", t));

        List<Task> nativeQueryTasksSortedByDueDateDescResults = taskRepository.allTasksSortedByDueDateDesc();
        LOG.info("All Tasks sorted by due date descending order with native query:");
        nativeQueryTasksSortedByDueDateDescResults.forEach(t -> LOG.info("{}", t));

    }

}
