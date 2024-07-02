package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.request;

import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.enums.RestaurantType;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.restaurant.RestaurantLayout;
import lombok.Data;

@Data
public class CreateRestaurantRequest {
    private String name;
    private String address;
    private String contactPerson;
    private String description;
    private RestaurantType type;
    private String restaurantLayout;
}
