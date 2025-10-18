package com.speed_liv.menu.infrastructure.persistance;

import com.speed_liv.menu.model.entity.Restaurant;
import com.speed_liv.menu.model.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter that bridges the domain RestaurantRepository interface with JPA implementation
 * This follows the Hexagonal Architecture pattern (Adapter)
 */
@Component
public class RestaurantRepositoryAdapter implements RestaurantRepository {

    private final JpaRestaurantRepository jpaRepository;

    @Autowired
    public RestaurantRepositoryAdapter(JpaRestaurantRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Restaurant> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Optional<Restaurant> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return jpaRepository.save(restaurant);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }
}
