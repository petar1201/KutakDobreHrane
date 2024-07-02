package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.controller;


import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.Restaurant;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.WaiterRestaurant;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.exception.InvalidLayoutException;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.dto.RestaurantsDTO;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.request.CreateRestaurantRequest;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service.RestaurantService;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service.WaiterRestaurantService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final WaiterRestaurantService waiterRestaurantService;

    @GetMapping("/all")
    public List<Restaurant> getAll(){
        return restaurantService.getAll();
    }

    @PostMapping("/create")
    public ResponseEntity<Restaurant> create(
            @RequestBody CreateRestaurantRequest createRestaurantRequest
            ){
        try {
            return new ResponseEntity<>(restaurantService.addRestaurant(createRestaurantRequest), HttpStatus.OK);
        } catch (InvalidLayoutException e) {
            return new ResponseEntity<>(HttpStatus.valueOf(301));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.valueOf(303));
        }
    }

    @GetMapping("/update/hours/{id}/{hours}")
    public ResponseEntity<Restaurant> updateHours(
            @PathVariable Long id,
            @PathVariable String hours
    ){
        try {
            return new ResponseEntity<>(restaurantService.setWorkingHours(id, hours), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.valueOf(303));
        }
    }

    @GetMapping("/all/waiters")
    public List<RestaurantsDTO> getAllRestaurants(){
        List<WaiterRestaurant> waiterRestaurantList = waiterRestaurantService.getAll();
        return restaurantService.getAllRestaurants(waiterRestaurantList);
    }


    @GetMapping("/count")
    public long countRestaurants(){
        return restaurantService.countAll();
    }

}
