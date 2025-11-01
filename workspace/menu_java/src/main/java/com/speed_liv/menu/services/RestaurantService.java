package com.speed_liv.menu.services;

import com.speed_liv.menu.config.DataSourceConfig;
import com.speed_liv.menu.config.DataSourceStrategy;
import com.speed_liv.menu.model.entity.Restaurant;
import com.speed_liv.menu.model.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for Restaurant business logic (Application Layer in Hexagonal Architecture)
 * Supports strategy pattern to switch between JSON file and H2 database adapters
 */
@Service
@Transactional
public class RestaurantService {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);
    
    private final RestaurantRepository h2Repository;
    private final RestaurantRepository jsonRepository;
    private final DataSourceConfig dataSourceConfig;

    @Autowired
    public RestaurantService(
            @Qualifier("h2RestaurantAdapter") RestaurantRepository h2Repository,
            @Qualifier("jsonRestaurantAdapter") RestaurantRepository jsonRepository,
            DataSourceConfig dataSourceConfig) {
        this.h2Repository = h2Repository;
        this.jsonRepository = jsonRepository;
        this.dataSourceConfig = dataSourceConfig;
        
        logger.info("🔧 RestaurantService initialized with strategy: {}", dataSourceConfig.getStrategy());
    }

    // ================ STRATEGY-BASED METHODS (Frontend uses these) ================

    /**
     * Get all restaurants using configured strategy
     * Frontend calls this - backend decides which data source to use
     */
    @Transactional(readOnly = true)
    public List<Restaurant> getAllRestaurants() {
        DataSourceStrategy strategy = dataSourceConfig.getStrategy();
        logger.info("🔧 Service: Getting all restaurants using strategy: {}", strategy);
        
        switch (strategy) {
            case JSON:
                return jsonRepository.findAll();
            case H2:
                return h2Repository.findAll();
            case BOTH:
                // Combine both data sources
                List<Restaurant> combined = new ArrayList<>();
                combined.addAll(h2Repository.findAll());
                combined.addAll(jsonRepository.findAll());
                logger.info("📊 Returning combined data: {} from H2 + {} from JSON = {} total",
                        h2Repository.findAll().size(),
                        jsonRepository.findAll().size(),
                        combined.size());
                return combined;
            default:
                return h2Repository.findAll();
        }
    }

    /**
     * Get restaurant by ID using configured strategy
     * Frontend calls this - backend decides which data source to use
     */
    @Transactional(readOnly = true)
    public Optional<Restaurant> getRestaurantById(Long id) {
        DataSourceStrategy strategy = dataSourceConfig.getStrategy();
        logger.info("🔧 Service: Getting restaurant {} using strategy: {}", id, strategy);
        
        switch (strategy) {
            case JSON:
                return jsonRepository.findById(id);
            case H2:
                return h2Repository.findById(id);
            case BOTH:
                // Try H2 first, then JSON
                Optional<Restaurant> fromH2 = h2Repository.findById(id);
                if (fromH2.isPresent()) {
                    logger.info("📊 Found restaurant {} in H2", id);
                    return fromH2;
                }
                logger.info("📊 Restaurant {} not in H2, checking JSON", id);
                return jsonRepository.findById(id);
            default:
                return h2Repository.findById(id);
        }
    }

    // ================ JSON-SPECIFIC METHODS ================

    /**
     * Get all restaurants from JSON file
     */
    @Transactional(readOnly = true)
    public List<Restaurant> getAllRestaurantsFromJson() {
        logger.info("🔧 Service: Getting all restaurants from JSON file");
        return jsonRepository.findAll();
    }

    /**
     * Get restaurant by ID from JSON file
     */
    @Transactional(readOnly = true)
    public Optional<Restaurant> getRestaurantByIdFromJson(Long id) {
        logger.info("🔧 Service: Getting restaurant {} from JSON file", id);
        return jsonRepository.findById(id);
    }

    // ================ H2-SPECIFIC METHODS ================

    /**
     * Get all restaurants from H2 database (explicit)
     */
    @Transactional(readOnly = true)
    public List<Restaurant> getAllRestaurantsFromH2() {
        logger.info("🔧 Service: Getting all restaurants from H2 database");
        return h2Repository.findAll();
    }

    /**
     * Get restaurant by ID from H2 database (explicit)
     */
    @Transactional(readOnly = true)
    public Optional<Restaurant> getRestaurantByIdFromH2(Long id) {
        logger.info("🔧 Service: Getting restaurant {} from H2 database", id);
        return h2Repository.findById(id);
    }

    // ================ WRITE OPERATIONS (H2 only) ================

    /**
     * Save a restaurant to H2 database
     */
    public Restaurant saveRestaurant(Restaurant restaurant) {
        logger.info("🔧 Service: Saving restaurant to H2 database");
        return h2Repository.save(restaurant);
    }

    /**
     * Delete a restaurant from H2 database
     */
    public void deleteRestaurant(Long id) {
        logger.info("🔧 Service: Deleting restaurant {} from H2 database", id);
        h2Repository.deleteById(id);
    }

    /**
     * Check if a restaurant exists in H2 database
     */
    @Transactional(readOnly = true)
    public boolean restaurantExists(Long id) {
        logger.info("🔧 Service: Checking if restaurant {} exists in H2 database", id);
        return h2Repository.existsById(id);
    }
}
