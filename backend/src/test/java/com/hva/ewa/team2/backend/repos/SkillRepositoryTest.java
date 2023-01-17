package com.hva.ewa.team2.backend.repos;


import com.hva.ewa.team2.backend.data.skill.InMemorySkillRepository;
import com.hva.ewa.team2.backend.data.skill.SkillRepository;
import com.hva.ewa.team2.backend.domain.models.skill.Skill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SkillRepositoryTest {

    @Autowired
    SkillRepository skillRepo;

    Skill testSkill;

    @BeforeEach
    void init() {
        testSkill = new Skill(1, "MS Office Access", "Your ability to use MS Office Access");
        System.out.println(skillRepo);
    }

    @Test
    void repoExists() {
        assertNotNull(skillRepo);
    }

    @Test
    void getAllSkills() {
        assertNotNull(skillRepo.getAllSkills());

        assertEquals(skillRepo.getAllSkills().get(0).toString(), testSkill.toString());
    }

    @Test
    void getSkillById() {
        assertEquals(skillRepo.getSkillById(testSkill.getId()).toString(),testSkill.toString());
    }

}
