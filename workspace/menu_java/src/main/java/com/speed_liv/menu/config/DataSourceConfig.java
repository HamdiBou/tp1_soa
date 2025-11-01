package com.speed_liv.menu.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for data source strategy
 * Reads from application.properties: app.datasource.strategy
 */
@Configuration
@ConfigurationProperties(prefix = "app.datasource")
public class DataSourceConfig {

    private DataSourceStrategy strategy = DataSourceStrategy.H2; // Default to H2

    public DataSourceStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(DataSourceStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean isJsonEnabled() {
        return strategy == DataSourceStrategy.JSON || strategy == DataSourceStrategy.BOTH;
    }

    public boolean isH2Enabled() {
        return strategy == DataSourceStrategy.H2 || strategy == DataSourceStrategy.BOTH;
    }

    public boolean isBothEnabled() {
        return strategy == DataSourceStrategy.BOTH;
    }
}
