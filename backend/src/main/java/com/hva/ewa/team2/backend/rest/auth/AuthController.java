package com.hva.ewa.team2.backend.rest.auth;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hva.ewa.team2.backend.domain.models.user.User;
import com.hva.ewa.team2.backend.domain.usecases.auth.AuthBusinessLogic;
import com.hva.ewa.team2.backend.security.JWToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.NotAcceptableStatusException;
import com.hva.ewa.team2.backend.Config;

import javax.servlet.http.HttpServletRequest;

@RestController
@ResponseBody
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private final AuthBusinessLogic authBusinessLogic;

    @Autowired
    public AuthController(AuthBusinessLogic authBusinessLogic) {
        this.authBusinessLogic = authBusinessLogic;
    }

    @Autowired
    private Config apiConfig;


    @PostMapping(path = "/login")
    @JsonView(User.EssentialInfo.class)
    public ResponseEntity<User> login(@RequestBody ObjectNode body, HttpServletRequest request) {

        String email = body.get("email").asText();
        String password = body.get("password").asText();
        User user;

        String ipAddress = JWToken.getIpAddress(request);
        if (ipAddress == null) {
            throw new IllegalStateException("Cannot identify your source IP-Address.");
        }

        try {
            user = this.authBusinessLogic.getUserInfoByCredentials(email, password);
        } catch (Exception e){
            throw new NotAcceptableStatusException("Cannot authenticate account with email=" + email);
        }

        JWToken jwToken = new JWToken(user.getId(), user.getRole(), ipAddress);
        String tokenString = jwToken.encode(this.apiConfig.getIssuer(),
                this.apiConfig.getPassphrase(),
                this.apiConfig.getTokenDurationOfValidity());

        return ResponseEntity.accepted()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .body(user);
    }
}
