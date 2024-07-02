package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.controller;

import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.Admin;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.User;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.exception.InactiveUserException;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.exception.InvalidPasswordException;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.exception.UserNotFoundException;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.enums.UserStatus;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.request.LoginRequest;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.request.UserConfirmationRequest;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.restaurant.RestaurantLayout;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.repository.AdminRepository;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service.AdminService;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service.RestaurantValidationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;


    private final AdminRepository adminRepository;

    @PostMapping("/userConfirmation/{status}")
    public ResponseEntity<String> userConfirmation(
            @RequestBody UserConfirmationRequest userConfirmationRequest,
            @PathVariable UserStatus status
            ){
        try{
            adminService.userConfirmation(userConfirmationRequest, status);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("", HttpStatusCode.valueOf(309));
        }
        catch (InvalidPasswordException e) {
            return new ResponseEntity<>("", HttpStatusCode.valueOf(310));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Admin> login(
            @RequestBody LoginRequest loginRequest
            ){
        try {
            Admin admin = adminService.login(loginRequest);
            return new ResponseEntity<Admin>(admin, HttpStatus.OK);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(306));
        }
        catch (InvalidPasswordException e){
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(308));
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(304));
        }
    }

}
