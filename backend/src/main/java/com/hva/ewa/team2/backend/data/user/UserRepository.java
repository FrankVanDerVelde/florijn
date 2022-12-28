package com.hva.ewa.team2.backend.data.user;

import com.hva.ewa.team2.backend.domain.models.user.User;
import com.hva.ewa.team2.backend.rest.user.json.JsonSkills;
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

    @Query(value = "SELECT DISTINCT u FROM Specialist u INNER JOIN UserSkill us ON u.id = us.specialist.id " +
            "WHERE us.skill.id IN :skills AND us.rating >= 3")
    List<User> findSpecialistsBySkills(List<Integer> skills);

}
