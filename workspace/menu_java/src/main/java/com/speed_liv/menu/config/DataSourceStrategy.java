package com.speed_liv.menu.config;

/**
 * Enum to define available data source strategies
 */
public enum DataSourceStrategy {
    JSON,    // Use JSON file as data source
    H2,      // Use H2 database as data source
    BOTH     // Use both (for comparison/migration)
}
