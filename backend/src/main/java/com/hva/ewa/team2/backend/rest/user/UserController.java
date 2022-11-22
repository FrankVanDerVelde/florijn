package com.hva.ewa.team2.backend.rest.user;

import com.hva.ewa.team2.backend.data.user.UserRepository;
import com.hva.ewa.team2.backend.domain.models.user.Admin;
import com.hva.ewa.team2.backend.domain.models.user.Client;
import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import com.hva.ewa.team2.backend.domain.models.user.User;
import com.hva.ewa.team2.backend.rest.user.json.JsonCredentials;
import com.hva.ewa.team2.backend.rest.user.json.JsonUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(path = "/specialists", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> findUserByRole() {
        List<User> users = this.repository.getSpecialists();

        if (users.size() != 0) {
            return ResponseEntity.ok().body(users);
        }
        return null;
    }


}
