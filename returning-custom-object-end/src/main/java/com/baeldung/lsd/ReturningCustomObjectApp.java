package com.baeldung.lsd;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.baeldung.lsd.persistence.projection.CampaignClosed;
import com.baeldung.lsd.persistence.projection.CampaignNative;
import com.baeldung.lsd.persistence.projection.WorkerOpen;
import com.baeldung.lsd.persistence.projection.CampaignClass;
import com.baeldung.lsd.persistence.repository.CampaignRepository;
import com.baeldung.lsd.persistence.repository.WorkerRepository;

@SpringBootApplication
public class ReturningCustomObjectApp implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(ReturningCustomObjectApp.class);

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private WorkerRepository workerRepository;

    public static void main(final String... args) {
        SpringApplication.run(ReturningCustomObjectApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<CampaignClosed> closedProjections = campaignRepository.findClosedByNameContaining("Campaign");
        closedProjections.forEach(c -> LOG.info("id: {}, name: {}", c.getId(), c.getName()));

        List<WorkerOpen> openProjections = workerRepository.findByFirstName("John");
        openProjections.forEach(w -> LOG.info("worker: id {}, full name: {}", w.getId(), w.getName()));

        List<CampaignClass> classProjections = campaignRepository.findClassByNameContaining("Campaign");
        classProjections.forEach(p -> LOG.info("campaign: {}", p));

        List<CampaignNative> statistics = campaignRepository.getCampaignStatistics();
        statistics.forEach(s -> LOG.info("campaign statistics: id: {}, name: {}, tasks: {}", s.getId(), s.getName(), s.getTaskCount()));
    }

}
