package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.repository;

import com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findUserByUsernameEquals(String username);

    public Long countAllByStatusEquals(String status);

}
