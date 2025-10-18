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
 * Data loader to initialize the database with data from JSON file
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
            ClassPathResource resource = new ClassPathResource("bd/restaurants.json");
            InputStream inputStream = resource.getInputStream();
            
            List<Restaurant> restaurants = objectMapper.readValue(
                inputStream, 
                new TypeReference<List<Restaurant>>() {}
            );
            
            // Save all restaurants (with their plats via cascade)
            for (Restaurant restaurant : restaurants) {
                restaurantRepository.save(restaurant);
            }
            
            logger.info("Successfully loaded {} restaurants from JSON file", restaurants.size());
            
        } catch (IOException e) {
            logger.error("Failed to load restaurants from JSON file", e);
        }
    }
}
