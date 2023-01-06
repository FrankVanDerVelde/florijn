package com.hva.ewa.team2.backend.rest.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.JsonNode;
import com.hva.ewa.team2.backend.domain.models.user.Address;
import com.hva.ewa.team2.backend.domain.models.user.User;
import com.hva.ewa.team2.backend.domain.usecases.user.UserBusinessLogic;
import com.hva.ewa.team2.backend.rest.user.json.*;
import com.hva.ewa.team2.backend.security.JWToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Iterable<User>> getAllUsers() {
        return ResponseEntity.ok(this.userBusinessLogic.getAllUsers());
    }

    @GetMapping(path = "/role/{role}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<? extends User>> getUsersByRole(@PathVariable User.Role role) {
//        System.out.println(jwtToken);
        return ResponseEntity.ok(this.userBusinessLogic.getUsersByRole(role, role.getUserClass()));
    }

    @JsonView(User.EssentialInfo.class)
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return ResponseEntity.ok(this.userBusinessLogic.getUserById(id));
    }

    @JsonView(User.EssentialInfo.class)
    @PutMapping(path = "/{id}/edit", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@PathVariable int id, @ModelAttribute JsonUserData body) throws IOException {
        return ResponseEntity.ok(this.userBusinessLogic.updateUser(id, body));
    }

    @GetMapping(path = "/{id}/resume", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String, String>> getResume(@PathVariable int id) throws IOException {
//        return ResponseEntity.ok(this.userBusinessLogic.getResume(id));
        return ResponseEntity.ok(Collections.singletonMap("resumeURL", this.userBusinessLogic.getResume(id)));
    }

    @PutMapping(path = "/{id}/resume", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateResume(@PathVariable int id, @ModelAttribute JsonUserData body) throws IOException {
        return ResponseEntity.ok(Collections.singletonMap("resumeURL", this.userBusinessLogic.updateResume(id, body)));
    }

    @PostMapping(path = "/add/client")
    public ResponseEntity<User> addUser(@ModelAttribute AddClientRequestBody body) throws IOException {
        return ResponseEntity.ok(this.userBusinessLogic.addClient(body));
    }

    @PostMapping(path = "/add/specialist")
    public ResponseEntity<User> addUser(@ModelAttribute AddSpecialistRequestBody body) throws IOException {
        return ResponseEntity.ok(this.userBusinessLogic.addSpecialist(body));
    }

    @PostMapping(path = "/add/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addUser(@RequestBody JsonNode body) {
        return ResponseEntity.ok(this.userBusinessLogic.addAdmin(body));
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
