package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.controller;

import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.Restaurant;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.User;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.dto.DeliveryDto;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.request.ConfirmDeliveryRequest;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.request.CreateDeliveryRequest;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service.DeliveryService;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service.RestaurantService;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;
    private final RestaurantService restaurantService;
    private final UserService userService;


    @PostMapping("/create")
    public DeliveryDto createDelivery(
            @RequestBody CreateDeliveryRequest createDeliveryRequest
            ){
        User user = userService.findById(createDeliveryRequest.getIdGuest());
        Restaurant restaurant = restaurantService.findById(createDeliveryRequest.getIdRestaurant());
        if(user == null || restaurant == null){
            return null;
        }
        try {
            return deliveryService.createDelivery(user, restaurant, createDeliveryRequest.getCart());
        }
        catch (Exception e){
            return null;
        }
    }

    @GetMapping("/guest/{idGuest}")
    public List<DeliveryDto> findActiveDeliveriesForGuest(
            @PathVariable Long idGuest
    ){
        User user = userService.findById(idGuest);
        return deliveryService.findActiveDeliveriesForGuest(user);
    }


    @GetMapping("/restaurant/{idRes}")
    public List<DeliveryDto> findDeliveriesForRestaurant(
            @PathVariable Long idRes
    ){
        Restaurant restaurant = restaurantService.findById(idRes);
        return deliveryService.deliveriesForRestaurant(restaurant);
    }

    @PostMapping("/confirm")
    public DeliveryDto confirmDelivery(
            @RequestBody ConfirmDeliveryRequest confirmDeliveryRequest
            ){
            try{
                return deliveryService.confirmDelivery(confirmDeliveryRequest.getDeliveryTime(), confirmDeliveryRequest.getIdDelivery());
            }
            catch (Exception e){
                return null;
            }
    }

    @GetMapping("/refuse/{idDelivery}")
    public DeliveryDto refuseDelivery(
            @PathVariable Long idDelivery
    ){
        try{
            return deliveryService.refuseDelivery(idDelivery);
        }
        catch (Exception e){
            return null;
        }
    }

}
