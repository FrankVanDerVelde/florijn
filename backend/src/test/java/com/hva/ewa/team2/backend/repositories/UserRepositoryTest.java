package com.hva.ewa.team2.backend.repositories;

import com.hva.ewa.team2.backend.data.user.UserRepository;
import com.hva.ewa.team2.backend.domain.models.user.Admin;
import com.hva.ewa.team2.backend.domain.models.user.Client;
import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import com.hva.ewa.team2.backend.domain.models.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private List<User> allUsers;
    private List<User> someUsers;

    @BeforeEach
    public void setup() {
        this.allUsers = this.userRepository.findAll();
        this.someUsers = this.allUsers.subList(0, 7);
    }

    @Test
    public void testFindAll() {
        // check users have been loaded
        assertTrue(this.allUsers.size() > 0);
        assertTrue(this.someUsers.size() < this.allUsers.size());
    }

    @Test
    public void newUserCanBeAdded() {
        Admin newAdmin = new Admin(0, "admin#@test.com", "test#1", "users/avatars/admin1.jpeg", "Jan", "de Vlaag");
        Specialist newSpecialist = new Specialist(0, "specialist#@test.com", "test#2", "users/avatars/admin1.jpeg", "Johan", "Klaassen");
        Client newClient = new Client(0, "client#@test.com", "test#3", "users/avatars/admin1.jpeg", "KPN", null);

        Admin savedAdmin = userRepository.save(newAdmin);
        Specialist savedSpecialist = userRepository.save(newSpecialist);
        Client savedClient = userRepository.save(newClient);

        assertEquals(newAdmin.getFirstName(), savedAdmin.getFirstName());
        assertEquals(newSpecialist.getAddress(), savedSpecialist.getAddress());
        assertEquals(newClient.getRole(), savedClient.getRole());
    }

    @Test
    public void testFindUsersByRole() {
        List<User> admins = this.userRepository.findUsersByRole(User.Role.ADMIN, null);
        List<User> specialists = this.userRepository.findUsersByRole(User.Role.SPECIALIST, null);
        List<User> clients = this.userRepository.findUsersByRole(User.Role.CLIENT, null);

        List<User> adminList = new ArrayList<>();
        List<User> specialistList = new ArrayList<>();
        List<User> clientList = new ArrayList<>();

        for (User allUser : this.allUsers) {
            if (allUser instanceof Admin) {
                adminList.add(allUser);
            } else if (allUser instanceof Specialist) {
                specialistList.add(allUser);
            } else {
                clientList.add(allUser);
            }
        }

        assertEquals(adminList.size(), admins.size());
        assertEquals(specialistList.size(), specialists.size());
        assertEquals(clientList.size(), clients.size());
    }

    @Test
    public void findUserByIdReturnsCorrectUser() {
        for (User someUser : this.someUsers) {
            User user = userRepository.findById(someUser.getId()).get();
            assertEquals(someUser.getId(), user.getId());
            assertEquals(someUser.getEmail(), user.getEmail());
        }

        // check non-existing user
        User user = userRepository.findById(1000000).orElse(null);
        assertNull(user);
    }


    @Test
    public void getUserInfoByCredentialsReturnsCorrectUser() {
        for (User someUser : this.someUsers) {
            User user = userRepository.getUserInfoByCredentials(someUser.getEmail(), someUser.getPassword());
            assertEquals(someUser.getId(), user.getId());
        }

        // check non-existing user
        User user = userRepository.getUserInfoByCredentials("hello", "test");
        assertNull(user);
    }
}