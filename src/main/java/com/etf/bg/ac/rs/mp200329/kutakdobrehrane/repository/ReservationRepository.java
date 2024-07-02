package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.repository;

import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.Reservation;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    public List<Reservation> findAllByStatusEquals(String status);

    public List<Reservation> findAllByIdRestaurantEqualsAndStatusEquals(Restaurant idRestaurant, String status);
}

