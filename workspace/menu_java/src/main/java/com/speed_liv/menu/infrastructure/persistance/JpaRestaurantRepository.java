package com.speed_liv.menu.infrastructure.persistance;

import com.speed_liv.menu.model.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository implementation for Restaurant (Adapter in Hexagonal Architecture)
 * This is the infrastructure layer that implements Spring Data JPA
 */
@Repository
public interface JpaRestaurantRepository extends JpaRepository<Restaurant, Long> {
    // Spring Data JPA will automatically provide implementations for:
    // - findAll()
    // - findById(Long id)
    // - save(Restaurant restaurant)
    // - deleteById(Long id)
    // - existsById(Long id)
    
    // Additional custom queries can be added here if needed
}
