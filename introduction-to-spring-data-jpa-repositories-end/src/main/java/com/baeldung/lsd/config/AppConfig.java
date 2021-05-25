package com.baeldung.lsd.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.baeldung.lsd.persistence.repository")
@EntityScan("com.baeldung.lsd.persistence.model")
public class AppConfig {

}
