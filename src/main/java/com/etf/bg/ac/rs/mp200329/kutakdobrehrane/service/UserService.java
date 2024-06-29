package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.service;

import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.User;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.exception.UserNotFoundException;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.enums.UserStatus;
import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public void setUserStatus(long idUser, UserStatus userStatus ) throws UserNotFoundException {
        User user = userRepository.findById(idUser).isPresent() ? userRepository.findById(idUser).get() : null;
        if (user == null){
            throw new UserNotFoundException();
        }
        user.setStatus(userStatus.name());
        userRepository.save(user);
    }


}
