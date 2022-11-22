package com.hva.ewa.team2.backend.domain.models.user;

import lombok.Getter;
import lombok.Setter;

public class Address {

    @Getter
    @Setter
    private String place;

    @Getter @Setter
    private String street;

    @Getter @Setter
    private int houseNumber;

    @Getter @Setter
    private String houseNumberAddition;

    @Getter @Setter
    private String postalCode;

    public Address(String place, String street, int houseNumber, String houseNumberAddition, String postalCode) {
        this.place = place;
        this.street = street;
        this.houseNumber = houseNumber;
        this.houseNumberAddition = houseNumberAddition;
        this.postalCode = postalCode;
    }

    public Address() {

    }
}
