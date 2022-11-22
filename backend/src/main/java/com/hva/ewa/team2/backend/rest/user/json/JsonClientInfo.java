package com.hva.ewa.team2.backend.rest.user.json;

import lombok.Getter;
import lombok.Setter;

public class JsonClientInfo {
    @Getter
    @Setter
    protected int id;
    @Getter
    @Setter
    protected String email;
    @Getter
    @Setter
    protected String avatarUrl;
    @Getter
    @Setter
    protected String password;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String bannerSrc;

    public JsonClientInfo(){}

    public JsonClientInfo(int id, String email, String password, String profilePictureURL, String name, String bannerSrc) {
        this.id = id;
        this.email = email;
        this.avatarUrl = profilePictureURL;
        this.password = password;
        this.name = name;
        this.bannerSrc = bannerSrc;
    }
}
