package com.baeldung.lsd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.baeldung.lsd.persistence.model.Task;
import com.baeldung.lsd.persistence.repository.TaskRepository;

@SpringBootApplication
public class PaginationAndSortingSupportApp implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(PaginationAndSortingSupportApp.class);

    @Autowired
    private TaskRepository taskRepository;

    public static void main(final String... args) {
        SpringApplication.run(PaginationAndSortingSupportApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Pageable tasksFirstPage = PageRequest.of(0, 2);

        Page<Task> tasksPage1 = taskRepository.findAll(tasksFirstPage);
        LOG.info("Page 1 of All Tasks:");
        tasksPage1.forEach(task -> LOG.info(task.toString()));

        Pageable tasksSecondPage = PageRequest.of(1, 2);

        Page<Task> tasksPage2 = taskRepository.findAll(tasksSecondPage);
        LOG.info("Page 2 of All Tasks:");
        tasksPage2.forEach(task -> LOG.info(task.toString()));

        Sort sortByTaskNameDsc = Sort.by(Direction.DESC, "name");

        Iterable<Task> tasksSortedByNameDsc = taskRepository.findAll(sortByTaskNameDsc);
        LOG.info("All Tasks Sorted By Name in Descending Order:");
        tasksSortedByNameDsc.forEach(task -> LOG.info(task.toString()));

        Pageable tasksFirstPageSortedByNameDsc = PageRequest.of(0, 2, Sort.by("name")
            .descending());

        Page<Task> tasksPage1SortedByNameDsc = taskRepository.findAll(tasksFirstPageSortedByNameDsc);
        LOG.info("Page 1 of All Tasks Sorted by Name in Descending Order:");
        tasksPage1SortedByNameDsc.forEach(task -> LOG.info(task.toString()));

    }

}
