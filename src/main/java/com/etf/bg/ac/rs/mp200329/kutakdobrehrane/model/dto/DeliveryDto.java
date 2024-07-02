package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.dto;


import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
public class DeliveryDto {

    private Long id;
    private Long deliveryTime = null;
    private String status;
    private Long expectedArrival = null;
    private Instant dateTime;
    private String restaurantName;
    private String cart = null;


}
