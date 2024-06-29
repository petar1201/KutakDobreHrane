package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"user\"")
@RequiredArgsConstructor
public class User {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    @SequenceGenerator(
            name = "user_gen",
            sequenceName = "user_seq",
            allocationSize = 1
    )
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "security_question", nullable = false)
    private String securityQuestion;

    @Column(name = "security_answer", nullable = false)
    private String securityAnswer;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "gender", nullable = false, length = Integer.MAX_VALUE)
    private String gender;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "profile_photo")
    private byte[] profilePhoto;

    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    public User(String securityQuestion, String securityAnswer, String name, String username, String password, String lastName, String gender, String address, String phoneNumber, String email, byte[] profilePhoto, String cardNumber, String status, String type) {
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.name = name;
        this.username = username;
        this.password = password;
        this.lastName = lastName;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.profilePhoto = profilePhoto;
        this.cardNumber = cardNumber;
        this.status = status;
        this.type = type;
    }

    @Column(name = "type", nullable = false, length = 50)
    private String type;

}