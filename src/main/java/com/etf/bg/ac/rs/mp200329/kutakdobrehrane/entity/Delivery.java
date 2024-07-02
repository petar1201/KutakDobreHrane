package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "delivery")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_id_gen")
    @SequenceGenerator(name = "delivery_id_gen", sequenceName = "delivery_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_guest", nullable = false)
    private User idGuest;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_restaurant", nullable = false)
    private Restaurant idRestaurant;

    @Column(name = "delivery_time", nullable = true)
    private Long deliveryTime;

    @Column(name = "date_time", nullable = true)
    private Instant dateTime;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "cart", nullable = true)
    private String cart;


}