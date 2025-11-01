package com.speed_liv.menu.infrastructure.persistance;

import com.speed_liv.menu.model.entity.Restaurant;
import com.speed_liv.menu.model.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * H2 Database implementation of RestaurantRepository
 * Uses JPA to interact with H2 in-memory database
 * This is the PRIMARY adapter (default)
 */
@Component("h2RestaurantAdapter")
@Primary  // This is the default adapter
public class RestaurantRepositoryAdapter implements RestaurantRepository {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantRepositoryAdapter.class);
    
    private final JpaRestaurantRepository jpaRepository;

    @Autowired
    public RestaurantRepositoryAdapter(JpaRestaurantRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Restaurant> findAll() {
        logger.info("💾 H2 Adapter: Finding all restaurants from database");
        return jpaRepository.findAll();
    }

    @Override
    public Optional<Restaurant> findById(Long id) {
        logger.info("💾 H2 Adapter: Finding restaurant with id {} from database", id);
        return jpaRepository.findById(id);
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        logger.info("💾 H2 Adapter: Saving restaurant to database");
        return jpaRepository.save(restaurant);
    }

    @Override
    public void deleteById(Long id) {
        logger.info("💾 H2 Adapter: Deleting restaurant with id {} from database", id);
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        logger.info("💾 H2 Adapter: Checking if restaurant with id {} exists in database", id);
        return jpaRepository.existsById(id);
    }
}
