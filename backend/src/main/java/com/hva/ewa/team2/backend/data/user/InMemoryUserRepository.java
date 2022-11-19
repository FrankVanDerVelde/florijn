package com.hva.ewa.team2.backend.data.user;

import com.hva.ewa.team2.backend.domain.models.user.Admin;
import com.hva.ewa.team2.backend.domain.models.user.Client;
import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import com.hva.ewa.team2.backend.domain.models.user.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class InMemoryUserRepository implements UserRepository {

    private final ArrayList<User> users = new ArrayList<>();

    public InMemoryUserRepository() {
        this.users.add(new Specialist(0, "withneyk@florijn.com", "test", "/src/assets/avatars/avatar2.avif", "Withney", "Keulen"));
        this.users.add(new Specialist(1, "jant@florijn.com", "test", "/src/assets/avatars/avatar3.avif", "Jan", "Timmermans"));

        this.users.add(new Admin(2, "admin@test.com", "test", null, "Admin", "Test"));
        this.users.add(new Specialist(3, "specialist@test.com", "test", null, "Specialist", "Test"));
        this.users.add(new Client(4, "contact@ing.nl", "test", "/src/assets/ING-Bankieren-icoon.webp", "ING", "/src/assets/ing-banner.jpg"));
    }

    @Override
    public User findUserByCredentials(String email, String password) {
        return this.users.stream()
                .filter(user -> user.getEmail().equals(email))
                .filter(user -> user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User findById(int id) {
        return this.users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
