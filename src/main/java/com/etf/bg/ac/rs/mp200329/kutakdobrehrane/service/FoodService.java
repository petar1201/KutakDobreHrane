package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service;

import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.Food;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.Restaurant;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.dto.FoodDto;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.processing.Find;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;

    public void addFood(Food food){
        foodRepository.save(food);
    }

    public List<FoodDto> findAllByRestaurant(Restaurant restaurant){
        List<Food> foodList = foodRepository.findAllByIdRestaurantEquals(restaurant);
        List<FoodDto> foodDtoList = new ArrayList<>();
        for(Food food: foodList){
            FoodDto foodDto = new FoodDto();
            foodDto.setId(food.getId());
            foodDto.setName(food.getName());
            foodDto.setPrice(food.getPrice());
            foodDto.setIngredients(food.getIngredients());
            foodDto.setPhoto(food.getPhoto());

            foodDtoList.add(foodDto);
        }
        return foodDtoList;
    }

}
