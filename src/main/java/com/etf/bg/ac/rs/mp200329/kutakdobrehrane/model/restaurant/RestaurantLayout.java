package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.restaurant;

import lombok.Data;

import java.util.List;

@Data
public class RestaurantLayout {
    private List<Table> tables;
    private List<Kitchen> kitchens;
    private List<Toalet> toalets;
    private int width;
    private int height;
}

