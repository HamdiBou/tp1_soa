package com.speed_liv.menu.controllers;

import com.speed_liv.menu.model.entity.Restaurant;
import com.speed_liv.menu.services.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * REST Controller for Restaurant endpoints (API Layer in Hexagonal Architecture)
 * Frontend-agnostic: Uses strategy pattern to switch data sources via configuration
 */
@RestController
@RequestMapping("/restaurants")
@Tag(name = "Restaurant", description = "Restaurant management API - data source configured via app.datasource.strategy")
public class RestaurantController {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantController.class);
    
    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    /**
     * GET /restaurants
     * Get all restaurants using configured strategy (JSON, H2, or BOTH)
     * Frontend doesn't need to know which data source is used
     */
    @Operation(summary = "Get all restaurants", description = "Returns restaurants from configured data source (see app.datasource.strategy in application.properties)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of restaurants",
                     content = @Content(mediaType = "application/json", 
                                       schema = @Schema(implementation = Restaurant.class)))
    })
    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        logger.info("📡 API: GET /restaurants (using configured strategy)");
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        logger.info("📡 API: Returning {} restaurants", restaurants.size());
        return ResponseEntity.ok(restaurants);
    }

    /**
     * GET /restaurants/{id}
     * Get a specific restaurant by ID using configured strategy
     * Frontend doesn't need to know which data source is used
     */
    @Operation(summary = "Get restaurant by ID", description = "Returns a single restaurant from configured data source")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved restaurant",
                     content = @Content(mediaType = "application/json", 
                                       schema = @Schema(implementation = Restaurant.class))),
        @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        logger.info("📡 API: GET /restaurants/{} (using configured strategy)", id);
        return restaurantService.getRestaurantById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /restaurants
     * Create a new restaurant
     * 
     * @param restaurant the restaurant to create
     * @return the created restaurant
     */
    @Operation(summary = "Create a new restaurant", description = "Creates a new restaurant with its dishes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Restaurant created successfully",
                     content = @Content(mediaType = "application/json", 
                                       schema = @Schema(implementation = Restaurant.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody Restaurant restaurant) {
        Restaurant savedRestaurant = restaurantService.saveRestaurant(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRestaurant);
    }

    /**
     * DELETE /restaurants/{id}
     * Delete a restaurant
     * 
     * @param id the restaurant ID to delete
     * @return no content
     */
    @Operation(summary = "Delete a restaurant", description = "Deletes a restaurant by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Restaurant deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Restaurant not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        if (!restaurantService.restaurantExists(id)) {
            return ResponseEntity.notFound().build();
        }
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }
}
