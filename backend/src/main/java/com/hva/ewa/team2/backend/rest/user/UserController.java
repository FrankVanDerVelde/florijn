package com.hva.ewa.team2.backend.rest.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.JsonNode;
import com.hva.ewa.team2.backend.data.user.UserRepository;
import com.hva.ewa.team2.backend.domain.models.project.Project;
import com.hva.ewa.team2.backend.domain.models.user.Address;
import com.hva.ewa.team2.backend.domain.models.user.User;
import com.hva.ewa.team2.backend.domain.usecases.user.UserBusinessLogic;
import com.hva.ewa.team2.backend.rest.user.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/users")
public class UserController {

    private final UserBusinessLogic userBusinessLogic;

    @Autowired
    public UserController(UserBusinessLogic userBusinessLogic) {
        this.userBusinessLogic = userBusinessLogic;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<User>> getAllUsers() {
        return ResponseEntity.ok(this.userBusinessLogic.getAllUsers());
    }

    @GetMapping("/counts")
    public ResponseEntity<List<UserRepository.UserCount>> getUserCounts() {
        return ResponseEntity.ok(this.userBusinessLogic.getUserCounts());
    }

    @GetMapping(path = "/role/{role}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<? extends User>> getUsersByRole(@PathVariable User.Role role) {
        return ResponseEntity.ok(this.userBusinessLogic.getUsersByRole(role, role.getUserClass()));
    }

    @JsonView(User.EssentialInfo.class)
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return ResponseEntity.ok(this.userBusinessLogic.getUserById(id));
    }

    @PutMapping(path = "/{id}/edit", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@PathVariable int id, @ModelAttribute JsonUserData body) throws IOException {
        return ResponseEntity.ok(this.userBusinessLogic.updateUser(id, body));
    }

    @PutMapping(path = "/{id}/resume", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateResume(@PathVariable int id, @ModelAttribute JsonUserData body) throws IOException {
        return ResponseEntity.ok(this.userBusinessLogic.updateResume(id, body));
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
