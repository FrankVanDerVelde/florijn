package com.hva.ewa.team2.backend.rest.user;

import com.hva.ewa.team2.backend.domain.models.user.User;
import com.hva.ewa.team2.backend.domain.usecases.user.UserBusinessLogic;
import com.hva.ewa.team2.backend.rest.user.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/users")
public class UserController {

    private UserBusinessLogic userBusinessLogic;

    @Autowired
    public UserController(UserBusinessLogic userBusinessLogic) {
        this.userBusinessLogic = userBusinessLogic;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(this.userBusinessLogic.getAllUsers());
    }

    @GetMapping(path = "/role/{role}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String role) {
        return ResponseEntity.ok(this.userBusinessLogic.getUsersByRole(role));
    }

    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonUserInfo> getUserInfoByCredentials(@RequestBody JsonCredentials credentials) {
        return ResponseEntity.ok(this.userBusinessLogic.getUserInfoByCredentials(credentials.getEmail(),
                credentials.getPassword()));
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return ResponseEntity.ok(this.userBusinessLogic.getUserById(id));
    }

    @PostMapping(path = "/save/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> saveAdmin(@RequestBody JsonAdminInfo body) {
        return ResponseEntity.ok(this.userBusinessLogic.saveAdmin(body));
    }

    @PostMapping(path = "/save/specialist", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> saveSpecialist(@RequestBody JsonSpecialistInfo body) {
        return ResponseEntity.ok(this.userBusinessLogic.saveSpecialist(body));
    }

    @PostMapping(path = "/save/client", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> saveClient(@RequestBody JsonClientInfo body) {
        return ResponseEntity.ok(this.userBusinessLogic.saveClient(body));
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> deleteUserById(@PathVariable int id) {
        return ResponseEntity.ok(this.userBusinessLogic.deleteUserById(id));
    }
}
