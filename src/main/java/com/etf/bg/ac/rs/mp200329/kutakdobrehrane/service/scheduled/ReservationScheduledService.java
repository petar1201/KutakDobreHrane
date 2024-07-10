package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service.scheduled;

import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.Reservation;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ReservationScheduledService {

    private final ReservationService reservationService;

    @Scheduled(cron = "* * * * * *")
    public void scheduledCheckForExpiry(){
        Instant now = Instant.now();
        for(Reservation reservation: reservationService.findAllCreated()){
            if(now.isAfter(reservation.getDateTime())){
                try {
                    reservationService.expiredReservation(reservation.getId());
                } catch (Exception e) {
                    System.out.println(e.toString());
                    break;
                }
            }
        }
    }

}
