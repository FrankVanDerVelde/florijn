package com.hva.ewa.team2.backend.data.skill;

import com.hva.ewa.team2.backend.domain.models.skill.Expertise;
import com.hva.ewa.team2.backend.domain.models.skill.Skill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface SkillRepository extends CrudRepository<Skill, Integer> {

    @Query(value = "SELECT e FROM Expertise e WHERE e.id = :id")
    Expertise getExpertiseById(int id);

    @Query(value = "SELECT e FROM Expertise e")
    ArrayList<Expertise> getAllExpertises();
}
