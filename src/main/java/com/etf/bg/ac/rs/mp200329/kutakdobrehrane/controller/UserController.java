package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.controller;

import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.Restaurant;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.User;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.WaiterRestaurant;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.exception.InactiveUserException;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.exception.InvalidGenderException;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.exception.InvalidPasswordException;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.exception.UserNotFoundException;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.dto.RestaurantsDTO;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.request.ChangePasswordRequest;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.request.LoginRequest;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.request.RegisterUserRequest;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service.RestaurantService;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service.UserService;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service.WaiterRestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final RestaurantService restaurantService;
    private final WaiterRestaurantService waiterRestaurantService;

    @PostMapping("login")
    public ResponseEntity<User> login(
            @RequestBody LoginRequest loginRequest
            ){
        try{
            User user = userService.login(loginRequest);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(306));
        }
        catch (InactiveUserException e){
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(307));
        }
        catch (InvalidPasswordException e){
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(308));
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(304));
        }
    }

    @PostMapping("register")
    public ResponseEntity<User> register(
            @RequestBody RegisterUserRequest registerUserRequest
            ){
        try{
            return new ResponseEntity<>(userService.register(registerUserRequest), HttpStatus.OK);
        }
        catch (InvalidPasswordException e){
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(301));

        }
        catch (InvalidGenderException e){
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(305));

        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(303));
        }
    }

    @PostMapping("changePassword")
    public ResponseEntity<String> changePassword(
            @RequestBody ChangePasswordRequest changePasswordRequest
            ){
        try {
            userService.changePassword(changePasswordRequest);
            return new ResponseEntity<>("Password changed succesfully", HttpStatus.OK);

        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(301));
        } catch (InvalidPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(311));
        }
    }


    @GetMapping("all")
    public List<User> getAll(){
        return userService.getAll();
    }

    @PostMapping("/update")
    public ResponseEntity<User> update(
            @RequestBody User user,
            @RequestHeader(required = false) Long idRes
    ){
        try {
            User retUser = userService.update(user);
            if(idRes != null){
                Restaurant restaurant = restaurantService.findById(idRes);
                if(restaurant != null) {
                    waiterRestaurantService.addDependency(retUser, restaurant);
                }
            }
            return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatusCode.valueOf(312));
        }
    }

    @GetMapping("/restaurant/{idUser}")
    public ResponseEntity<Long> getRestaurant(
            @PathVariable Long idUser
    ){
        try {
            User user = userService.findById(idUser);
            return new ResponseEntity<>(waiterRestaurantService.getByUser(user), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.valueOf(302));
        }
    }

    @GetMapping("/restaurant/details/{idUser}")
    public ResponseEntity<RestaurantsDTO> getRestaurantDetails(
            @PathVariable Long idUser
    ){
        try {
            User user = userService.findById(idUser);
            return new ResponseEntity<>(restaurantService.toDto(restaurantService.findById(waiterRestaurantService.getByUser(user))), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.valueOf(302));
        }
    }

    @GetMapping("/count/registered")
    public long countRegistered(){
        return userService.countRegistered();
    }

}
