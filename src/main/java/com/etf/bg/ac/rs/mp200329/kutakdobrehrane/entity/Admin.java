package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "admin")
public class Admin {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin_gen")
    @SequenceGenerator(
            name = "admin_gen",
            sequenceName = "admin_seq",
            allocationSize = 1
    )
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

}