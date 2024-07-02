package com.etf.bg.ac.rs.mp200329.kutakdobrehrane;

import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.Delivery;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.Reservation;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.enums.DeliveryStatus;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class Demo {

    private final FoodService foodService;
    private final RestaurantService restaurantService;
    private final ReservationService reservationService;
    private final DeliveryService deliveryService;
    private final UserService userService;

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
    @Scheduled(cron = "* * * * * *")
    public void scheduledCheckForExpiry(){
        Instant now = Instant.now();
        for(Reservation reservation: reservationService.findAllCreated()){
            if(reservation.getDateTime().isAfter(now)){
                try {
                    reservationService.expiredReservation(reservation.getId());
                } catch (Exception e) {
                    System.out.println(e.toString());
                    break;
                }
            }
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fun() {
//        for(Delivery delivery: deliveryService.findAll()){
//            delivery.setDeliveryTime(2L);
//            delivery.setDateTime(Instant.now());
//            delivery.setStatus(DeliveryType.ACCEPTED.name());
//            deliveryService.save(delivery);
//            break;
//        }
//        userService.updateMikipromax();
//        System.out.println(Instant.now());
//        addMealsToRestaurant(1L);
//        addMealsToRestaurant(2L);
    }

//    private void addMealsToRestaurant(Long restaurantId) {
//        Restaurant restaurant = restaurantService.findById(restaurantId);
//
//        if (restaurant != null) {
//            Food[] meals = {
//                    new Food(restaurant, "Spaghetti Carbonara", null, 120L, "Pasta, Eggs, Cheese, Bacon"),
//                    new Food(restaurant, "Margherita Pizza", null, 100L, "Dough, Tomatoes, Mozzarella, Basil"),
//                    new Food(restaurant, "Caesar Salad", null, 90L, "Romaine Lettuce, Croutons, Parmesan, Caesar Dressing"),
//                    new Food(restaurant, "Grilled Chicken Sandwich", null, 110L, "Chicken Breast, Lettuce, Tomato, Mayo, Bun"),
//                    new Food(restaurant, "Beef Burger", null, 130L, "Beef Patty, Lettuce, Tomato, Cheese, Bun"),
//                    new Food(restaurant, "Chocolate Cake", null, 80L, "Chocolate, Sugar, Flour, Eggs, Butter")
//            };
//
//            for (Food meal : meals) {
//                foodService.addFood(meal);
//            }
//        }
//    }
}
