package com.speed_liv.menu.services;

import com.speed_liv.menu.model.entity.Restaurant;
import com.speed_liv.menu.model.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for Restaurant business logic (Application Layer in Hexagonal Architecture)
 */
@Service
@Transactional
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    /**
     * Get all restaurants with their dishes
     * @return List of all restaurants
     */
    @Transactional(readOnly = true)
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    /**
     * Get a restaurant by its ID
     * @param id the restaurant ID
     * @return Optional containing the restaurant if found
     */
    @Transactional(readOnly = true)
    public Optional<Restaurant> getRestaurantById(Long id) {
        return restaurantRepository.findById(id);
    }

    /**
     * Save a restaurant
     * @param restaurant the restaurant to save
     * @return the saved restaurant
     */
    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    /**
     * Delete a restaurant by its ID
     * @param id the restaurant ID to delete
     */
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }

    /**
     * Check if a restaurant exists
     * @param id the restaurant ID
     * @return true if exists, false otherwise
     */
    @Transactional(readOnly = true)
    public boolean restaurantExists(Long id) {
        return restaurantRepository.existsById(id);
    }
}
