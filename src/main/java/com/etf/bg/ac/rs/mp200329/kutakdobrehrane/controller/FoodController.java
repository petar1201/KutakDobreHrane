package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.controller;

import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.dto.FoodDto;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service.FoodService;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/food")
public class FoodController {

    private final RestaurantService restaurantService;
    private final FoodService foodService;


    @GetMapping("/all/restaurant/{idRes}")
    public List<FoodDto> findAllByRestaurant(
            @PathVariable Long idRes
    ){
        if(restaurantService.findById(idRes) == null){
            return null;
        };
        return foodService.findAllByRestaurant(restaurantService.findById(idRes));
    }


}
