package com.hva.ewa.team2.backend.data.user;

import com.hva.ewa.team2.backend.domain.models.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    @Query(value = "SELECT u FROM User u")
    List<User> findAll();

    @Query(value = "SELECT u FROM User u WHERE u.role = :role")
    <U extends User> List<U> findUsersByRole(User.Role role, Class<U> clazz);

    @Query(value = "SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
    User getUserInfoByCredentials(String email, String password);

}
