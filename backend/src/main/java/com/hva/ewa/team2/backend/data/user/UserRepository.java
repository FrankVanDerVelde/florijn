package com.hva.ewa.team2.backend.data.user;

import com.hva.ewa.team2.backend.domain.models.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer>  {
    User getUserInfoByCredentials(String email, String password);

    User getUserById(int id);

    String getRoleByUser(User user);

    List<User> getAllUsers();

    List<User> getUsersByRole(String role);

    User updateUser(User user);

    User addUser(String role, User user);

    User deleteUserById(int id);
}
