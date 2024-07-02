package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "table_usage")
public class TableUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "table_usage_id_gen")
    @SequenceGenerator(name = "table_usage_id_gen", sequenceName = "table_usage_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_reservation", nullable = false)
    private Reservation idReservation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_restaurant", nullable = false)
    private Restaurant idRestaurant;

    @Column(name = "table_num", nullable = false)
    private Long tableNum;

    @Column(name = "start_hour", nullable = false)
    private Long startHour;

    @Column(name = "datum", nullable = false)
    private Date datum;

}