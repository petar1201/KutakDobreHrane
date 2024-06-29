package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service;

import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.Admin;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.exception.InvalidPasswordException;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.exception.UserNotFoundException;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.enums.UserStatus;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.request.UserConfirmationRequest;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.repository.AdminRepository;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserService userService;

    private final AdminRepository adminRepository;


    private final PasswordEncoder passwordEncoder;


    private void addAdmin(){
        Admin admin = new Admin();
        admin.setUsername("petar");
        admin.setPassword(passwordEncoder.encode("Miskocar1!"));
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
        if(passwordEncoder.matches(userConfirmationRequest.getPassword(), passwordEncoder.encode(userConfirmationRequest.getPassword()))){
            userService.setUserStatus(userConfirmationRequest.getIdUser(), userStatus );
        }
        else {
            throw new InvalidPasswordException();
        }
    }




}
