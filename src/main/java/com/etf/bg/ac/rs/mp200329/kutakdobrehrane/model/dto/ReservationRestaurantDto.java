package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.dto;

import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;


@Value
public class ReservationRestaurantDto implements Serializable {
    Long id;
    LocalDateTime dateTime;
    String description;
    String status;
    String restaurantName;
    String restaurantAddress;
    Long numOfPeople;
    Long idRes;
    String restaurantLayout ;
}