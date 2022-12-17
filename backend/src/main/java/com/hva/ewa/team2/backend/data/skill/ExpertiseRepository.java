package com.hva.ewa.team2.backend.data.skill;

import com.hva.ewa.team2.backend.domain.models.skill.Expertise;
import com.hva.ewa.team2.backend.domain.models.skill.Skill;
import com.hva.ewa.team2.backend.domain.models.skill.SkillGroup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ExpertiseRepository extends CrudRepository<Expertise, Integer>  {
    @Query(value = "SELECT g FROM Expertise g")
    ArrayList<Expertise> findAllExpertises();

    @Query(value = "SELECT g FROM Expertise g WHERE g.id = :id")
    Expertise findExpertiseById(int id);
}
