package com.hva.ewa.team2.backend.rest.User;

import com.hva.ewa.team2.backend.data.User.UserRepository;
import com.hva.ewa.team2.backend.domain.models.User.Admin;
import com.hva.ewa.team2.backend.domain.models.User.Client;
import com.hva.ewa.team2.backend.domain.models.User.Specialist;
import com.hva.ewa.team2.backend.domain.models.User.User;
import com.hva.ewa.team2.backend.rest.User.json.JsonCredentials;
import com.hva.ewa.team2.backend.rest.User.json.JsonUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
public class UserController {

    @Autowired
    private UserRepository repository;

    @PostMapping(path = "/login")
    public ResponseEntity<JsonUserInfo> findUser(@RequestBody JsonCredentials credentials) {
        User user = this.repository.findUserByCredentials(credentials.getEmail(), credentials.getPassword());

        if (user != null) {
            String role = findRoleByUser(user);
            return ResponseEntity.ok().body(new JsonUserInfo(user.getId(), role));
        }
        return null;
    }

    public String findRoleByUser(User user) {
        String role = null;
        if (user instanceof Admin) {
            role = "admin";
        } else if (user instanceof Specialist) {
            role = "specialist";
        } else if (user instanceof Client) {
            role = "client";
        }
        return role;
    }
}
