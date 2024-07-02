package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_id_gen")
    @SequenceGenerator(name = "reservation_id_gen", sequenceName = "reservation_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_guest", nullable = false)
    private User idGuest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_waiter")
    private User idWaiter;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_restaurant", nullable = false)
    private Restaurant idRestaurant;

    @Column(name = "date_time", nullable = false)
    private Instant dateTime;

    @Column(name = "description")
    private String description;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "num_of_people", nullable = false)
    private Long numOfPeople;

}