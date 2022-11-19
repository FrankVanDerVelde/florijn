package com.hva.ewa.team2.backend.domain.models.user;

import lombok.Getter;
import lombok.Setter;

public class Client extends User {

    @Getter @Setter
    private String name;
    @Getter @Setter
    private String bannerSrc;

    public Client(int id, String email, String password, String profilePictureURL, String name) {
        this(id, email, password, profilePictureURL, name, "/src/assets/banner-default.webp");
    }

    public Client(int id, String email, String password, String profilePictureURL, String name, String bannerURL) {
        super(id, email, password, profilePictureURL);
        this.name = name;
        this.bannerSrc = bannerURL;
    }

}
