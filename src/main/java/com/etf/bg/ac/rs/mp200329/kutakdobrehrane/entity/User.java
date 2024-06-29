package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"user\"")
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

    @Column(name = "id_korisnik")
    private Long idKorisnik;

    @Column(name = "security_question", nullable = false)
    private String securityQuestion;

    @Column(name = "security_answer", nullable = false)
    private String securityAnswer;

    @Column(name = "name", nullable = false)
    private String name;

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

    @Column(name = "type", nullable = false, length = 50)
    private String type;

}