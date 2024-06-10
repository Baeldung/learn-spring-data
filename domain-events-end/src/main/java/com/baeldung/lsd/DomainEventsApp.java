package com.baeldung.lsd;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.baeldung.lsd.persistence.model.Campaign;
import com.baeldung.lsd.persistence.model.Task;
import com.baeldung.lsd.persistence.repository.CampaignRepository;
import com.baeldung.lsd.persistence.repository.TaskRepository;

@SpringBootApplication
public class DomainEventsApp implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DomainEventsApp.class);

    @Autowired
    private CampaignRepository campaignRepository;
    
    @Autowired
    private TaskRepository taskRepository;
    
    public static void main(final String... args) {
        SpringApplication.run(DomainEventsApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Campaign campaign = campaignRepository.findById(1L).get();
        
        taskRepository.save(new Task("Sample Name", "Sample Description", LocalDate.of(2025, 01, 01), campaign));
        
    }

}
