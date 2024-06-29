package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.request;

import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.enums.UserType;
import lombok.Data;

@Data
public class RegisterUserRequest {
    private String securityQuestion;
    private String securityAnswer;
    private String username;
    private String name;
    private String lastName;
    private Character gender;
    private String password;
    private String address;
    private String phoneNumber;
    private String email;
    private String profilePhoto;
    private UserType userType;
    private String cardNumber;
}
