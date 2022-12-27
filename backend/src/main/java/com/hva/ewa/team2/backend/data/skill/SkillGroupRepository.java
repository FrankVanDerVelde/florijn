package com.hva.ewa.team2.backend.data.skill;

import com.hva.ewa.team2.backend.domain.models.skill.SkillGroup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
public interface SkillGroupRepository extends CrudRepository<SkillGroup, Integer>  {

    @Query(value = "SELECT g FROM SkillGroup g")
    ArrayList<SkillGroup> findAllSkillGroups();

    @Query(value = "SELECT g FROM SkillGroup g WHERE g.id = :id")
    SkillGroup findGroupById(int id);
}
