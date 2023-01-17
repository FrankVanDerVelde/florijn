package com.hva.ewa.team2.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hva.ewa.team2.backend.data.skill.SkillRepository;
import com.hva.ewa.team2.backend.data.user.UserRepository;
import com.hva.ewa.team2.backend.domain.models.skill.Skill;
import com.hva.ewa.team2.backend.domain.models.skill.SkillGroup;
import com.hva.ewa.team2.backend.domain.models.user.Address;
import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import com.hva.ewa.team2.backend.rest.skill.json.JsonUserSkill;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SkillsTest {

    List<Skill>  userSkillList = new ArrayList<>();
    Skill testSkill = new Skill();
    Specialist specialist;

    @Autowired
    public UserRepository userRepo;

    @Autowired
    public SkillRepository skillRepo;

    @BeforeEach
    void init() {
        testSkill = new Skill(100, "test-skill", "description");

        userSkillList = skillRepo.getAllSkills();
//        for (int i = 0; i < 5; i++) {
//            specialist = userRepo.save(new Specialist(0
//                    , "skillstest@test.com",
//                    "test",
//                    "users/avatars/1.avif",
//                    "Skills", "Test",
//                    new Address(
//                            "Hoorn",
//                            "Noorder Plantsoen",
//                            12,
//                            "",
//                            "1623AB"),
//                    "users/resumes/sample-resume.pdf"));
//        }

    }

    @Test
    void id() {
        assertEquals(testSkill.getId(), 100);
        testSkill.setId(101);
        assertEquals(testSkill.getId(), 101);
    }

    @Test
    void name() {
        assertEquals(testSkill.getName(), "test-skill");
        testSkill.setName("test-skill2");
        assertEquals(testSkill.getName(), "test-skill2");
    }

    @Test
    void description() {
        assertEquals(testSkill.getDescription(), "description");
        testSkill.setDescription("description2");
        assertEquals(testSkill.getDescription(), "description2");
    }

    @Test
    void constructor() {
        Skill testSkill2 = new Skill(100, "test-skill", "description");
        assertEquals(testSkill.toString(), testSkill2.toString());
    }

    @Test
    void checkToString() {
        assertEquals(testSkill.toString(), "{" +
                "id=" + testSkill.getId() +
                ", name='" + testSkill.getName() + '\'' +
                ", description='" + testSkill.getDescription() + '\'' +
                '}');
    }
}
