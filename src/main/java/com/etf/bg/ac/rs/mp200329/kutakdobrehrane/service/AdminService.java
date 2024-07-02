package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service;

import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.Admin;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.User;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.exception.InactiveUserException;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.exception.InvalidPasswordException;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.exception.UserNotFoundException;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.enums.UserStatus;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.request.LoginRequest;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.request.UserConfirmationRequest;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.repository.AdminRepository;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserService userService;

    private final AdminRepository adminRepository;


    private final PasswordEncoder passwordEncoder;


    public Admin login(LoginRequest loginRequest) throws UserNotFoundException, InactiveUserException, InvalidPasswordException {
        Admin admin = adminRepository.findAdminByUsernameEquals(loginRequest.getUsername());
        if(admin == null){
            throw new UserNotFoundException();
        }
        if(passwordEncoder.matches(loginRequest.getPassword(), admin.getPassword())){
            return admin;
        }
        else {
            throw new InvalidPasswordException();
        }
    }

    public void addAdmin(String username, String password){
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(passwordEncoder.encode(password));
        adminRepository.save(admin);
    }

    private Admin getAdmin(Long id){
        return adminRepository.findById(id).isPresent() ? adminRepository.findById(id).get() : null;
    }

    public void userConfirmation(UserConfirmationRequest userConfirmationRequest, UserStatus userStatus) throws UserNotFoundException, InvalidPasswordException {
        Admin admin = getAdmin(userConfirmationRequest.getIdAdmin());
        if(admin == null){
            throw new UserNotFoundException();
        }
        userService.setUserStatus(userConfirmationRequest.getIdUser(), userStatus );
    }




}
