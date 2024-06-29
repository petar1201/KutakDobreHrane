package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.controller;

import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.User;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.exception.InactiveUserException;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.exception.InvalidGenderException;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.exception.InvalidPasswordException;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.exception.UserNotFoundException;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.request.ChangePasswordRequest;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.request.LoginRequest;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.request.RegisterUserRequest;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;


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


}
