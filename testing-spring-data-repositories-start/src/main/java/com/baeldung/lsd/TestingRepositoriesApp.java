package com.baeldung.lsd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.baeldung.lsd.persistence.repository.CampaignRepository;

@SpringBootApplication
public class TestingRepositoriesApp implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(TestingRepositoriesApp.class);

    @Autowired
    private CampaignRepository campaignRepository;

    public static void main(final String... args) {
        SpringApplication.run(TestingRepositoriesApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }

}
