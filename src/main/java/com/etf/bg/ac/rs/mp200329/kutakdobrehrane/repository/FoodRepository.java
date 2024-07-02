package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.repository;

import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.Food;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    public List<Food> findAllByIdRestaurantEquals(Restaurant idRestaurant);
}
