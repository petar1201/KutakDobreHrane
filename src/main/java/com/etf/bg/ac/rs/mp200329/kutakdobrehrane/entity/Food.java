package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "food")
@RequiredArgsConstructor
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_id_gen")
    @SequenceGenerator(name = "food_id_gen", sequenceName = "food_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_restaurant", nullable = false)
    private Restaurant idRestaurant;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "photo")
    private String photo;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "ingredients")
    private String ingredients;

    public Food(Restaurant idRestaurant, String name, String photo, Long price, String ingredients) {
        this.idRestaurant = idRestaurant;
        this.name = name;
        this.photo = photo;
        this.price = price;
        this.ingredients = ingredients;
    }
}