package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.dto;

import lombok.Data;
import org.springframework.jdbc.core.SqlReturnType;

@Data
public class FoodDto {

    private Long id;
    private String name;
    private Long price;
    private String ingredients;
    private String photo;
}
