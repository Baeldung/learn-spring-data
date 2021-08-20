package com.baeldung.lsd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDataAndTransactionsApp implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(SpringDataAndTransactionsApp.class);
    
    public static void main(final String... args) {
        SpringApplication.run(SpringDataAndTransactionsApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
    }

}
