package com.hva.ewa.team2.backend.domain.models.user;

public class Client extends User {

    private String name;
    private String bannerURL;

    public Client(int id, String email, String password, String profilePictureURL, String name) {
        this(id, email, password, profilePictureURL, name, null);
        // todo use the default banner.
    }

    public Client(int id, String email, String password, String profilePictureURL, String name, String bannerURL) {
        super(id, email, password, profilePictureURL);
        this.name = name;
        this.bannerURL = bannerURL;
    }

    public String getName() {
        return name;
    }

    public String getBannerURL() {
        return bannerURL;
    }
}
