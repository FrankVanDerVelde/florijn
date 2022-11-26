package com.hva.ewa.team2.backend.rest.auth;

import com.hva.ewa.team2.backend.domain.usecases.auth.AuthBusinessLogic;
import com.hva.ewa.team2.backend.rest.user.json.JsonCredentials;
import com.hva.ewa.team2.backend.rest.user.json.JsonUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
public class AuthController {

    private AuthBusinessLogic authBusinessLogic;

    @Autowired
    public AuthController(AuthBusinessLogic authBusinessLogic) {
        this.authBusinessLogic = authBusinessLogic;
    }

    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonUserInfo> getUserInfoByCredentials(@RequestBody JsonCredentials credentials) {
        return ResponseEntity.ok(this.authBusinessLogic.getAuthInfoByCredentials(credentials.getEmail(),
                credentials.getPassword()));
    }
}
