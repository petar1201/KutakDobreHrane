package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.response;


import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.dto.ReservationDto;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.dto.ReservationRestaurantDto;
import lombok.Data;

import java.util.List;

@Data
public class ReservationArchive {

    private List<ReservationRestaurantDto> active;//active showed up created
    private List<ReservationDto> expired;// expired
    private List<ReservationDto> refused;//declined didnt showup

}
