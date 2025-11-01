package com.speed_liv.menu.infrastructure.persistance;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.speed_liv.menu.model.entity.Restaurant;
import com.speed_liv.menu.model.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JSON-based implementation of RestaurantRepository
 * Reads data from restaurants.json file
 */
@Component("jsonRestaurantAdapter")
public class JsonRestaurantRepositoryAdapter implements RestaurantRepository {

    private static final Logger logger = LoggerFactory.getLogger(JsonRestaurantRepositoryAdapter.class);
    private final ObjectMapper objectMapper;
    private List<Restaurant> restaurants;

    public JsonRestaurantRepositoryAdapter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        loadRestaurantsFromJson();
    }

    private void loadRestaurantsFromJson() {
        try {
            ClassPathResource resource = new ClassPathResource("bd/restaurants.json");
            InputStream inputStream = resource.getInputStream();
            
            restaurants = objectMapper.readValue(
                inputStream, 
                new TypeReference<List<Restaurant>>() {}
            );
            
            logger.info("✅ Loaded {} restaurants from JSON file", restaurants.size());
            
        } catch (IOException e) {
            logger.error("❌ Failed to load restaurants from JSON", e);
            restaurants = new ArrayList<>();
        }
    }

    @Override
    public List<Restaurant> findAll() {
        logger.info("📄 JSON Adapter: Finding all restaurants");
        return new ArrayList<>(restaurants);
    }

    @Override
    public Optional<Restaurant> findById(Long id) {
        logger.info("📄 JSON Adapter: Finding restaurant with id {}", id);
        return restaurants.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        logger.info("📄 JSON Adapter: Save operation not supported for JSON source");
        throw new UnsupportedOperationException("Cannot save to JSON file - read-only source");
    }

    @Override
    public void deleteById(Long id) {
        logger.info("📄 JSON Adapter: Delete operation not supported for JSON source");
        throw new UnsupportedOperationException("Cannot delete from JSON file - read-only source");
    }

    @Override
    public boolean existsById(Long id) {
        logger.info("📄 JSON Adapter: Checking if restaurant {} exists in JSON file", id);
        return restaurants.stream()
                .anyMatch(r -> r.getId().equals(id));
    }
}
