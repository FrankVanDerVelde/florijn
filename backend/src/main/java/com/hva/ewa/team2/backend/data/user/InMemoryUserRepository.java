package com.hva.ewa.team2.backend.data.user;

import com.hva.ewa.team2.backend.domain.models.user.Admin;
import com.hva.ewa.team2.backend.domain.models.user.Client;
import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import com.hva.ewa.team2.backend.domain.models.user.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class InMemoryUserRepository implements UserRepository {

    private ArrayList<User> users = new ArrayList<>();

    public InMemoryUserRepository() {
        this.users.add(new Admin(0, "admin@test.com", "test", null, "Admin", "Test"));
        this.users.add(new Specialist(1, "specialist@test.com", "test", null, "Specialist", "Test"));
        this.users.add(new Client(2, "client@test.com", "test", null, "Client", "Test"));
    }

    @Override
    public User findUserByCredentials(String email, String password) {
        return this.users.stream()
                .filter(user -> user.getEmail().equals(email))
                .filter(user -> user.getPassword().equals(password))
                .findFirst().orElse(null);
    }
}
