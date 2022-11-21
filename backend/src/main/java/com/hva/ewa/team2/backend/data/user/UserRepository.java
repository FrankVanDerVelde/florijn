package com.hva.ewa.team2.backend.data.user;

import com.hva.ewa.team2.backend.domain.models.user.Admin;
import com.hva.ewa.team2.backend.domain.models.user.Client;
import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import com.hva.ewa.team2.backend.domain.models.user.User;
import com.hva.ewa.team2.backend.rest.user.json.JsonAdminInfo;

import java.util.List;

public interface UserRepository {
    User findUserByCredentials(String email, String password);

    String findRoleByUser(User user);

    User findById(int id);

    List<User> getAllUsers();

    List<User> getUsersByRole(String role);

    User saveClient(Client client);

    User saveSpecialist(Specialist specialist);

    User saveAdmin(Admin admin);

    User deleteById(int id);
}
