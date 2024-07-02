package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.request;


import lombok.Data;

@Data
public class CreateDeliveryRequest {

    private Long idGuest;
    private Long idRestaurant;
    private String cart;

}
