package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.repository;

import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.Delivery;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.Restaurant;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    public List<Delivery> findAllByIdGuestEqualsAndStatusEquals(User idGuest, String status);

    public List<Delivery> findAllByIdRestaurantEqualsAndStatusEquals(Restaurant restaurant, String status);

}
