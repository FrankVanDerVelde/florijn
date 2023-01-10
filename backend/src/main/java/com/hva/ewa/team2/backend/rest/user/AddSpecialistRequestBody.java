package com.hva.ewa.team2.backend.rest.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class AddSpecialistRequestBody {
    @Getter @Setter
    private String firstname;
    @Getter @Setter
    private String lastname;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private String password;

    @Getter @Setter
    private MultipartFile avatarUrl;

    public AddSpecialistRequestBody() {
    }
}
