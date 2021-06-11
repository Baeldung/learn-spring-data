package com.baeldung.lsd;

import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.baeldung.lsd.persistence.model.Task;
import com.baeldung.lsd.persistence.model.TaskStatus;
import com.baeldung.lsd.persistence.repository.TaskRepository;
import com.baeldung.lsd.persistence.repository.UserRepository;

@SpringBootApplication
public class ModifyingQueriesApp implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(ModifyingQueriesApp.class);

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public static void main(final String... args) {
        SpringApplication.run(ModifyingQueriesApp.class, args);
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        int deletedRecords = taskRepository.deleteCompletedTasks();
        LOG.info("Number of Records Deleted :\n {}", deletedRecords);

        userRepository.addActiveColumn();

        Optional<Task> taskOptional = taskRepository.findById(1L);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setStatus(TaskStatus.DONE);

            int deletedCompletedRecords = taskRepository.deleteCompletedTasks();
            LOG.info("Number of Records Deleted :\n {}", deletedCompletedRecords);

            Optional<Task> taskCompleted = taskRepository.findById(1L);
            LOG.info("Completed Task :\n {}", taskCompleted);
        }

    }

}
