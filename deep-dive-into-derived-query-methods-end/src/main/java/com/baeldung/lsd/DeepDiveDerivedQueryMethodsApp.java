package com.baeldung.lsd;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.baeldung.lsd.persistence.model.Project;
import com.baeldung.lsd.persistence.model.Task;
import com.baeldung.lsd.persistence.model.TaskStatus;
import com.baeldung.lsd.persistence.repository.IProjectRepository;
import com.baeldung.lsd.persistence.repository.ITaskRepository;

@SpringBootApplication
public class DeepDiveDerivedQueryMethodsApp implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DeepDiveDerivedQueryMethodsApp.class);

    @Autowired
    private IProjectRepository projectRepository;
    @Autowired
    private ITaskRepository taskRepository;

    public static void main(final String... args) {
        SpringApplication.run(DeepDiveDerivedQueryMethodsApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Iterable<Project> projects = projectRepository.findByNameStartingWith("Project");
        LOG.info("Projects name starting with Project:");
        projects.forEach((project) -> LOG.info("{}", project));

        Iterable<Project> percentSignProjects = projectRepository.findByNameStartingWith("%");
        LOG.info("Projects name starting with \"%\"\n{}", percentSignProjects);

        Iterable<Project> allProjects = projectRepository.findByNameStartingWith("");
        LOG.info("Projects name starting with \"\"");
        allProjects.forEach((project) -> LOG.info("{}", project));

        List<Task> tasksStrictlyDue = taskRepository.findByDueDateGreaterThan(LocalDate.of(2025, 2, 10));
        LOG.info("Number of Tasks due strictly after: \"2025-02-10\"\n{}", tasksStrictlyDue.size());

        List<Task> tasksDue = taskRepository.findByDueDateGreaterThanEqual(LocalDate.of(2025, 2, 10));
        LOG.info("Number of Tasks due after: \"2025-02-10\"\n{}", tasksDue.size());

        List<Task> overdueTasks = taskRepository.findByDueDateBeforeAndStatusEquals(LocalDate.now(), TaskStatus.TO_DO);
        LOG.info("Overdue Tasks:\n{}", overdueTasks);

        List<Task> tasksByAssignee = taskRepository.findByAssigneeFirstName("John");
        LOG.info("Tasks assigned to John\n{}", tasksByAssignee);

        Iterable<Project> distinctProjects = projectRepository.findDistinctByTasksNameContaining("Task");
        LOG.info("Distinct projects with Task name containing \"Task\"\n{}", distinctProjects);
    }

}
