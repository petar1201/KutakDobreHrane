package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "restaurant")
@RequiredArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "res_gen")
    @SequenceGenerator(
            name = "res_gen",
            sequenceName = "res_seq",
            allocationSize = 1
    )
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "contact_person", nullable = false)
    private String contactPerson;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "working_hours")
    private String workingHours;

    @Column(name = "restaurant_layout", nullable = false, length = 1024)
    private String restaurantLayout;

    public Restaurant(String name, String address, String contactPerson, String description, String type, String restaurantLayout) {
        this.name = name;
        this.address = address;
        this.contactPerson = contactPerson;
        this.description = description;
        this.type = type;
        this.restaurantLayout = restaurantLayout;
    }
}