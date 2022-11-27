package com.hva.ewa.team2.backend.rest.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.hva.ewa.team2.backend.domain.models.user.Address;
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

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return ResponseEntity.ok(this.userBusinessLogic.getUserById(id));
    }

    @PutMapping(path = "/{id}/edit", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody JsonNode body) {
        return ResponseEntity.ok(this.userBusinessLogic.updateUser(id, body));
    }

    @PostMapping(path = "/add/{role}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addUser(@PathVariable String role, @RequestBody JsonNode body) {
        return ResponseEntity.ok(this.userBusinessLogic.addUser(role, body));
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> deleteUserById(@PathVariable int id) {
        return ResponseEntity.ok(this.userBusinessLogic.deleteUserById(id));
    }

    @GetMapping(path = "/address/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Address> getUsersAddressById(@PathVariable int id) {
        return ResponseEntity.ok(this.userBusinessLogic.getUsersAddressById(id));
    }
}
