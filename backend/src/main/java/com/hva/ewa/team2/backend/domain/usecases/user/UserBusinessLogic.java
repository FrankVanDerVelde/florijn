package com.hva.ewa.team2.backend.domain.usecases.user;

import com.hva.ewa.team2.backend.domain.models.user.User;
import com.hva.ewa.team2.backend.rest.user.json.*;

import java.util.List;

public interface UserBusinessLogic {
    JsonUserInfo findUserInfoByCredentials(String email, String password);

    JsonUserRole findRoleByUserId(int id);

    User findById(int id);

    List<User> getAllUsers();

    List<User> getUsersByRole(String role);

    User saveAdmin(JsonAdminInfo admin);

    User saveSpecialist(JsonSpecialistInfo specialist);

    User saveClient(JsonClientInfo client);

    User deleteById(int id);
}
