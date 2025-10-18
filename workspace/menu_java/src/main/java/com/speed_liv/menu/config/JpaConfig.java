package com.speed_liv.menu.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configuration for JPA repositories
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.speed_liv.menu.infrastructure.persistance")
@EntityScan(basePackages = "com.speed_liv.menu.model.entity")
public class JpaConfig {
}
