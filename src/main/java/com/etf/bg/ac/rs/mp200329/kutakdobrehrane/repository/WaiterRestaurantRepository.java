package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.repository;

import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.WaiterRestaurant;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.WaiterRestaurantId;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service.WaiterRestaurantService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaiterRestaurantRepository extends JpaRepository<WaiterRestaurant, WaiterRestaurantId> {

}
