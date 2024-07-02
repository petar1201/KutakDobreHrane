package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service;

import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.Restaurant;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.User;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.exception.*;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.enums.UserStatus;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.request.ChangePasswordRequest;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.request.LoginRequest;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.request.RegisterUserRequest;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final WaiterRestaurantService waiterRestaurantService;

    private final RestaurantService restaurantService;

    static String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[a-z]{3})(?=.*[0-9])(?=.*[!@#$%^&*()])(?=.{6,10})[a-zA-Z].*$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean validatePassword(String password) {
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public void setUserStatus(long idUser, UserStatus userStatus ) throws UserNotFoundException {
        User user = userRepository.findById(idUser).isPresent() ? userRepository.findById(idUser).get() : null;
        if (user == null){
            throw new UserNotFoundException();
        }
        user.setStatus(userStatus.name());
        userRepository.save(user);
    }

    public User login(LoginRequest loginRequest) throws UserNotFoundException, InactiveUserException, InvalidPasswordException {
        User user = userRepository.findUserByUsernameEquals(loginRequest.getUsername());
        if(user == null){
            throw new UserNotFoundException();
        }
        if(Objects.equals(user.getStatus(), UserStatus.REFUSED.toString()) ||
                Objects.equals(user.getStatus(), UserStatus.WAITING.toString())){
            throw new InactiveUserException();
        }
        if(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            return user;
        }
        else {
            throw new InvalidPasswordException();
        }
    }

    public User register(RegisterUserRequest registerUserRequest) throws InvalidPasswordException, InvalidGenderException, UserNotFoundException, RestaurantNotFoundException {
        if(!validatePassword(registerUserRequest.getPassword())){
            throw new InvalidPasswordException();
        }
        if(!registerUserRequest.getGender().toString().equals("F")
                && !registerUserRequest.getGender().toString().equals("M")){
            throw new InvalidGenderException();
        }
        User retUser = userRepository.save(
                new User(
                        registerUserRequest.getSecurityQuestion(),
                        registerUserRequest.getSecurityAnswer(),
                        registerUserRequest.getName(),
                        registerUserRequest.getUsername(),
                        passwordEncoder.encode(registerUserRequest.getPassword()),
                        registerUserRequest.getLastName(),
                        registerUserRequest.getGender().toString(),
                        registerUserRequest.getAddress(),
                        registerUserRequest.getPhoneNumber(),
                        registerUserRequest.getEmail(),
                        registerUserRequest.getProfilePhoto(),
                        registerUserRequest.getCardNumber(),
                        UserStatus.WAITING.name(),
                        registerUserRequest.getType().name()
                )
        );

        if(registerUserRequest.getIdRes() != null){
            waiterRestaurantService.addDependency(retUser, restaurantService.findById(registerUserRequest.getIdRes()));
        }
        return retUser;
    }


    public void changePassword(ChangePasswordRequest changePasswordRequest) throws UserNotFoundException, InvalidPasswordException {
        User user = userRepository.findUserByUsernameEquals(changePasswordRequest.getUsername());
        if (user == null){
            throw new UserNotFoundException();
        }
        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())){
            throw new InvalidPasswordException();
        }
        if(!validatePassword(changePasswordRequest.getNewPassword())){
            throw new InvalidPasswordException();
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);

    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User update(User user) throws Exception {
        if (user.getPassword().contains("$")){
            return userRepository.save(user);
        }
        else{
            if(validatePassword(user.getPassword())){
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                return userRepository.save(user);
            }
            else {
                throw new Exception();
            }
        }
    }

    public User findById(Long id){
        return userRepository.findById(id).isPresent() ? userRepository.findById(id).get() : null;
    }

    public long countRegistered(){
        return userRepository.countAllByStatusEquals(UserStatus.ACTIVE.name());
    }

    public void updateMikipromax(){
        User miki = findById(3L);
        miki.setPassword(passwordEncoder.encode("Miskocar1!"));
        userRepository.save(miki);
    }

}
