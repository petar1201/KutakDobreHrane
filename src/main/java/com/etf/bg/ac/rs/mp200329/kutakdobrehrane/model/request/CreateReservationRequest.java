package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.request;

import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.enums.ReservationStatus;
import lombok.Data;

import java.time.Instant;

@Data
public class CreateReservationRequest {
    private Long idGuest;
    private Long idRestaurant;
    private Instant dateTime;
    private String description;
    private Long numOfPersons;
}
