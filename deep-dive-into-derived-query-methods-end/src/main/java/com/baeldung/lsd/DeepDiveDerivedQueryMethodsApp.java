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

import com.baeldung.lsd.persistence.model.Campaign;
import com.baeldung.lsd.persistence.model.Task;
import com.baeldung.lsd.persistence.model.TaskStatus;
import com.baeldung.lsd.persistence.repository.CampaignRepository;
import com.baeldung.lsd.persistence.repository.TaskRepository;

@SpringBootApplication
public class DeepDiveDerivedQueryMethodsApp implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DeepDiveDerivedQueryMethodsApp.class);

    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private TaskRepository taskRepository;

    public static void main(final String... args) {
        SpringApplication.run(DeepDiveDerivedQueryMethodsApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        Iterable<Campaign> campaigns = campaignRepository.findByNameStartingWith("Campaign");
        LOG.info("Campaigns name starting:");
        campaigns.forEach(campaign -> LOG.info("{}", campaign));

        Iterable<Campaign> percentSignCampaigns = campaignRepository.findByNameStartingWith("%");
        LOG.info("Campaigns name starting with \"%\"\n{}", percentSignCampaigns);

        Iterable<Campaign> allCampaigns = campaignRepository.findByNameStartingWith("");
        LOG.info("Campaigns name starting with \"\"");
        allCampaigns.forEach(campaign -> LOG.info("{}", campaign));

        List<Task> tasksStrictlyDue = taskRepository.findByDueDateGreaterThan(LocalDate.of(2025, 2, 10));
        LOG.info("Number of Tasks due strictly after: \"2025-02-10\"\n{}", tasksStrictlyDue.size());

        List<Task> tasksDue = taskRepository.findByDueDateGreaterThanEqual(LocalDate.of(2025, 2, 10));
        LOG.info("Number of Tasks due after: \"2025-02-10\"\n{}", tasksDue.size());

        List<Task> overdueTasks = taskRepository.findByDueDateBeforeAndStatusEquals(LocalDate.now(), TaskStatus.TO_DO);
        LOG.info("Overdue Tasks:\n{}", overdueTasks);

        List<Task> tasksByAssignee = taskRepository.findByAssigneeFirstName("John");
        LOG.info("Tasks assigned to John\n{}", tasksByAssignee);

        Iterable<Campaign> distinctCampaigns = campaignRepository.findDistinctByTasksNameContaining("Task");
        LOG.info("Distinct campaigns with Task name containing \"Task\"\n{}", distinctCampaigns);
    }

}
