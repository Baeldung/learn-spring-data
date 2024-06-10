package com.baeldung.lsd;

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
public class EntityGraphsApp implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(EntityGraphsApp.class);

    @Autowired
    private CampaignRepository campaignRepository;
    
    @Autowired
    private TaskRepository taskRepository;

    public static void main(final String... args) {
        SpringApplication.run(EntityGraphsApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        
        Iterable<Campaign> allCampaignsByName = campaignRepository.findByNameContaining("Campaign");
        allCampaignsByName.forEach(campaign -> LOG.info("Tasks for Campaign with Name {} :: {}", campaign.getName(), campaign.getTasks()));

        List<Task> allToDoTasks = taskRepository.findByStatus(TaskStatus.TO_DO);
        allToDoTasks.forEach(task -> LOG.info("TODO Task :: {}", task));
    }

}
