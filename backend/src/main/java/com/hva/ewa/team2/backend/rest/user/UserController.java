package com.hva.ewa.team2.backend.rest.user;

import com.hva.ewa.team2.backend.data.user.UserRepository;
import com.hva.ewa.team2.backend.domain.models.user.Admin;
import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import com.hva.ewa.team2.backend.domain.models.user.Client;
import com.hva.ewa.team2.backend.domain.models.user.User;
import com.hva.ewa.team2.backend.rest.user.json.*;
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

    @GetMapping(path = "/users")
    public List<User> getAllUsers(){
        return this.repository.getAllUsers();
    }

    @GetMapping(path = "/users/{role}")
    public List<User> getUsersByRole(@PathVariable String role){
        return this.repository.getUsersByRole(role);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<JsonUserInfo> findUserByCredentials(@RequestBody JsonCredentials credentials) {
        User user = this.repository.findUserByCredentials(credentials.getEmail(), credentials.getPassword());

        if (user != null) {
            String role = this.repository.findRoleByUser(user);
            return ResponseEntity.ok().body(new JsonUserInfo(user.getId(), role));
        }
        return null;
    }

    @GetMapping("/user/{id}")
    public User findById(@PathVariable int id){
        return this.repository.findById(id);
    }

    @PostMapping("/users/save/admin")
    public ResponseEntity<User> saveAdmin(@RequestBody JsonAdminInfo body){
        User user = this.repository.saveAdmin(body);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/users/save/specialist")
    public ResponseEntity<User> saveSpecialist(@RequestBody JsonSpecialistInfo body){
        User user = this.repository.saveSpecialist(body);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/users/save/client")
    public ResponseEntity<User> saveClient(@RequestBody JsonClientInfo body){
        User user = this.repository.saveClient(body);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/user/{id}")
    public User deleteById(@PathVariable int id){
        return this.repository.deleteById(id);
    }
}
