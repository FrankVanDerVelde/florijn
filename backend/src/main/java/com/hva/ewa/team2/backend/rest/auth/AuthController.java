package com.hva.ewa.team2.backend.rest.auth;

import com.fasterxml.jackson.annotation.JsonView;
import com.hva.ewa.team2.backend.domain.models.user.User;
import com.hva.ewa.team2.backend.domain.usecases.auth.AuthBusinessLogic;
import com.hva.ewa.team2.backend.rest.user.json.JsonCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("/auth")
public class AuthController {

    private AuthBusinessLogic authBusinessLogic;

    @Autowired
    public AuthController(AuthBusinessLogic authBusinessLogic) {
        this.authBusinessLogic = authBusinessLogic;
    }

    @JsonView(User.EssentialInfo.class)
    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserInfoByCredentials(@RequestBody JsonCredentials credentials) {
        return ResponseEntity.ok(this.authBusinessLogic.getUserInfoByCredentials(credentials.getEmail(),
                credentials.getPassword()));
    }
}
