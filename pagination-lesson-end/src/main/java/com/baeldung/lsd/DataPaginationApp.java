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
import org.springframework.data.domain.Slice;

import com.baeldung.lsd.persistence.model.Task;
import com.baeldung.lsd.persistence.model.TaskStatus;
import com.baeldung.lsd.persistence.repository.TaskRepository;

@SpringBootApplication
public class DataPaginationApp implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DataPaginationApp.class);

    @Autowired
    private TaskRepository taskRepository;

    public static void main(final String... args) {
        SpringApplication.run(DataPaginationApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Pageable twoTasksPagination = PageRequest.of(0, 2);

        Page<Task> twoTasksAPage = taskRepository.findByStatus(TaskStatus.TO_DO, twoTasksPagination);

        LOG.info("All Tasks by status Paginated:\n {}", twoTasksAPage.getContent());

        LOG.info("Total No Of Tasks :\n {}", twoTasksAPage.getTotalElements());
        LOG.info("Total Pages of Tasks :\n {}", twoTasksAPage.getTotalPages());
        
        Slice<Task> twoTasksASlice = taskRepository.findByNameLike("Task%", twoTasksPagination);

        LOG.info("All Tasks Sliced :\n {}", twoTasksASlice.getContent());
        
        LOG.info("Are there Slices Prior :\n {}", twoTasksASlice.hasPrevious());
        LOG.info("Are there Slices After :\n {}", twoTasksASlice.hasNext());
        
        Slice<Task> nextTasks = taskRepository.findByNameLike("Task%", twoTasksPagination.next());

        LOG.info("Tasks in next page :\n {}", nextTasks.getContent());

        Pageable pageTwo = PageRequest.of(1, 2);

        Page<Task> allTasksByNamePageTwo = taskRepository.allTasksByName("Task%", pageTwo);
        LOG.info("Page Two of All Tasks By Name:\n {}", allTasksByNamePageTwo.getContent());
    }
}
