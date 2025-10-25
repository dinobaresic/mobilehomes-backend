package com.example.mobilehomesbooking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "parcels")
@Getter
@Setter
@NoArgsConstructor
public class Parcel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Naziv lokacije / oglasa
    @Column(nullable = false)
    private String title;

    // Kratki opis (pogled, blizina mora itd.)
    @Column(columnDefinition = "TEXT")
    private String description;

    // Lokacija (mjesto ili kamp)
    @Column(nullable = false)
    private String location;

    // Kvadratura u m2
    private double sizeSquareMeters;

    // Godišnja cijena najma
    @Column(nullable = false)
    private double pricePerYear;

    // Dodatni podaci (struja, voda, priključci)
    private boolean hasElectricity;
    private boolean hasWater;
    private boolean hasWifi;

    // URL slike (za frontend prikaz)
    private String imageUrl;

    // Vlasnik parcele (Owner)
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
}
