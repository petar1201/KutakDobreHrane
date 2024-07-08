package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.dto;

import lombok.Data;

@Data
public class RestaurantsDTO {

    private String name;
    private String address;
    private String workingHours;
    private String type;
    private String waiters;
    private Long id;
    private String contactPerson;
    private String restaurantLayout;

}
