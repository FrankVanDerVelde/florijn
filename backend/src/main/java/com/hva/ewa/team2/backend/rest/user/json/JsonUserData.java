package com.hva.ewa.team2.backend.rest.user.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hva.ewa.team2.backend.domain.models.user.Address;
import com.hva.ewa.team2.backend.domain.models.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

//avatarFile
public class JsonUserData {

    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String user;

    @Getter
    @Setter
    private String address;

    @Getter
    @Setter
    private MultipartFile avatarFile;

    @Getter
    @Setter
    private MultipartFile bannerFile;

    public JsonUserData() {
    }

    public JsonUserData(int id, String user, String address, MultipartFile avatarFile, MultipartFile bannerFile) {
        this.id = id;
        this.user = user;
        this.address = address;
        this.avatarFile = avatarFile;
        this.bannerFile = bannerFile;
    }

    @Override
    public String toString() {
        return "{" +
                "user='" + user + '\'' +
                ", address='" + address + '\'' +
                ", avatarFile=" + avatarFile +
                ", bannerFile=" + bannerFile +
                '}';
    }
}
