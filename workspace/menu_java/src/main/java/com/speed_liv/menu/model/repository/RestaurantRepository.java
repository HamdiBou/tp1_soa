package com.speed_liv.menu.model.repository;

import com.speed_liv.menu.model.entity.Restaurant;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Restaurant domain (Port in Hexagonal Architecture)
 * This is the domain interface that defines the contract for data access
 */
public interface RestaurantRepository {
    
    /**
     * Find all restaurants
     * @return List of all restaurants
     */
    List<Restaurant> findAll();
    
    /**
     * Find a restaurant by its ID
     * @param id the restaurant ID
     * @return Optional containing the restaurant if found
     */
    Optional<Restaurant> findById(Long id);
    
    /**
     * Save a restaurant
     * @param restaurant the restaurant to save
     * @return the saved restaurant
     */
    Restaurant save(Restaurant restaurant);
    
    /**
     * Delete a restaurant by its ID
     * @param id the restaurant ID to delete
     */
    void deleteById(Long id);
    
    /**
     * Check if a restaurant exists by its ID
     * @param id the restaurant ID
     * @return true if exists, false otherwise
     */
    boolean existsById(Long id);
}
