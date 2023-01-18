package com.hva.ewa.team2.backend.repos;


import com.hva.ewa.team2.backend.data.skill.InMemorySkillRepository;
import com.hva.ewa.team2.backend.data.skill.SkillRepository;
import com.hva.ewa.team2.backend.domain.models.skill.Skill;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SkillRepositoryTest {

    @Autowired
    SkillRepository skillRepo;

    Skill testSkill;


    @BeforeEach
    void init() {
        testSkill = new Skill(0, "MS Office Access", "Your ability to use MS Office Access");
    }

    @Test
    void repoExists() {
        assertNotNull(skillRepo);
    }

    @Test
    void  addSkill() {
        Skill savedSkill = skillRepo.save(testSkill);
        assertTrue(skillRepo.findById(savedSkill.getId()).isPresent());
        testSkill.setId(savedSkill.getId());
        assertEquals(savedSkill.toString(),testSkill.toString());
    }

    @Test
    void getSkillById() {
        Skill skill = skillRepo.getAllSkills().get(0);
        assertEquals(testSkill.getName(), skillRepo.getSkillById(skill.getId()).getName()
        );
    }

    @Test
    void getAllSkills() {
        assertNotNull(skillRepo.getAllSkills());

        assertEquals(testSkill.getName(), skillRepo.getAllSkills().get(0).getName());
    }

    @Test
    void updateSkill() {
        Skill savedSkill2 = skillRepo.save(new Skill(testSkill.getId(), "updated", testSkill.getDescription()));
        assertEquals(savedSkill2.getName(), "updated");
    }

    @Test
    void deleteSkill() {
        skillRepo.delete(testSkill);
        assertFalse(skillRepo.findById(testSkill.getId()).isPresent());
    }

}
