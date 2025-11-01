package com.speed_liv.menu.infrastructure.persistance;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.speed_liv.menu.model.entity.Restaurant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Data loader to initialize H2 database with data from restaurants-h2.json file
 * This loads DIFFERENT data than the JSON adapter
 */
@Component
public class RestaurantDataLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantDataLoader.class);
    
    private final JpaRestaurantRepository restaurantRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public RestaurantDataLoader(JpaRestaurantRepository restaurantRepository, ObjectMapper objectMapper) {
        this.restaurantRepository = restaurantRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        loadRestaurantsFromJson();
    }

    private void loadRestaurantsFromJson() {
        try {
            // Load from restaurants-h2.json (different from JSON adapter)
            ClassPathResource resource = new ClassPathResource("bd/restaurants-h2.json");
            InputStream inputStream = resource.getInputStream();
            
            List<Restaurant> restaurants = objectMapper.readValue(
                inputStream, 
                new TypeReference<List<Restaurant>>() {}
            );
            
            // Save all restaurants (with their plats via cascade)
            for (Restaurant restaurant : restaurants) {
                restaurantRepository.save(restaurant);
            }
            
            logger.info("💾 Successfully loaded {} restaurants into H2 DATABASE from restaurants-h2.json", restaurants.size());
            logger.info("💾 H2 Database contains: {}", restaurants.stream().map(Restaurant::getName).toList());
            
        } catch (IOException e) {
            logger.error("❌ Failed to load restaurants into H2 from JSON file", e);
        }
    }
}
