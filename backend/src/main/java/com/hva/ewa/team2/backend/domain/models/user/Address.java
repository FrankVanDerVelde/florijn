package com.hva.ewa.team2.backend.domain.models.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private int id;

    @Getter
    @Setter
    private String place;

    @Getter
    @Setter
    private String street;

    @Getter
    @Setter
    private int houseNumber;

    @Getter
    @Setter
    private String houseNumberAddition;

    @Getter
    @Setter
    private String postalCode;

    public Address() {
    }

    public Address(String place, String street, int houseNumber, String houseNumberAddition, String postalCode) {
        this.place = place;
        this.street = street;
        this.houseNumber = houseNumber;
        this.houseNumberAddition = houseNumberAddition;
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "{" +
                "place='" + place + '\'' +
                ", street='" + street + '\'' +
                ", houseNumber=" + houseNumber +
                ", houseNumberAddition='" + houseNumberAddition + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
