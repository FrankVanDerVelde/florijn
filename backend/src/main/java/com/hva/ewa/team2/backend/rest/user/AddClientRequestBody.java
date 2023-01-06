package com.hva.ewa.team2.backend.rest.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class AddClientRequestBody {

    @Getter @Setter
    private String email;
    @Getter @Setter
    private String password;
    @Getter @Setter
    private MultipartFile avatarUrl;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private MultipartFile bannerSrc;

    public AddClientRequestBody() {}
}
