package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;


@Value
public class ReservationDto implements Serializable {
    Long id;
    LocalDateTime dateTime;
    String description;
    String status;
    Long numOfPeople;
}