package com.hva.ewa.team2.backend.domain.usecases.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.hva.ewa.team2.backend.data.user.UserRepository;
import com.hva.ewa.team2.backend.domain.models.user.Address;
import com.hva.ewa.team2.backend.domain.models.user.User;
import com.hva.ewa.team2.backend.rest.user.AddClientRequestBody;
import com.hva.ewa.team2.backend.rest.user.AddSpecialistRequestBody;
import com.hva.ewa.team2.backend.rest.user.json.JsonSkills;
import com.hva.ewa.team2.backend.rest.user.json.JsonUserData;

import java.io.IOException;
import java.util.List;

public interface UserBusinessLogic {
    User getUserById(int id);

    List<User> getAllUsers();

    <U extends User> List<U> getUsersByRole(User.Role role, Class<U> clazz);

    User updateUser(int id, JsonUserData body) throws IOException;

    User addAdmin(JsonNode body);
    User addClient(AddClientRequestBody body) throws IOException;
    User addSpecialist(AddSpecialistRequestBody body) throws IOException;

    User deleteUserById(int id);

    Address getUsersAddressById(int id);

    String updateResume(int id, JsonUserData body) throws IOException;

    List<UserRepository.UserCount> getUserCounts();

    String getResume(int id);

    List<User> getSpecialistsBySkills(List<Integer> skills);
}
