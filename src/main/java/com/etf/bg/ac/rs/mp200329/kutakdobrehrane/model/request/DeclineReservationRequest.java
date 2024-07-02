package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.request;


import lombok.Data;

@Data
public class DeclineReservationRequest {

    private Long idReservation;
    private Long idWaiter;
    private String description;

}
