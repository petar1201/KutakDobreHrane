package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service.scheduled;

import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.Delivery;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.enums.DeliveryStatus;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class DeliveryScheduledService {

    private final DeliveryService deliveryService;

    @Scheduled(cron = "* * * * * *")
    public void scheduledCheckForDeliveries(){
        Instant now = Instant.now();
        for(Delivery delivery: deliveryService.findAll()){
            if(delivery.getStatus().equals(DeliveryStatus.ACCEPTED.name())){
                long minutesElapsed = ChronoUnit.MINUTES.between(delivery.getDateTime(), now);
                long minutesLeft = delivery.getDeliveryTime() - minutesElapsed;
                if(minutesLeft<=0){
                    delivery.setStatus(DeliveryStatus.DELIVERED.name());
                    deliveryService.save(delivery);
                }
            }

        }
    }
}
