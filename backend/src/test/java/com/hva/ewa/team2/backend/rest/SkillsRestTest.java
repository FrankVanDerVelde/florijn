package com.hva.ewa.team2.backend.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.hva.ewa.team2.backend.data.skill.SkillRepository;
import com.hva.ewa.team2.backend.data.user.UserRepository;
import com.hva.ewa.team2.backend.domain.models.skill.Expertise;
import com.hva.ewa.team2.backend.domain.models.skill.Skill;
import com.hva.ewa.team2.backend.domain.models.skill.SkillGroup;
import com.hva.ewa.team2.backend.domain.models.skill.UserSkill;
import com.hva.ewa.team2.backend.domain.models.user.*;
import com.hva.ewa.team2.backend.rest.skill.json.JsonUserSkill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SkillsRestTest {
    List<JsonUserSkill> userSkillList = new ArrayList<>();
    Specialist specialist;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public UserRepository userRepo;

    @Autowired
    public SkillRepository skillRepo;

    @BeforeEach
    void init() {
        List<Skill> skills = skillRepo.getAllSkills();
        for (int i = 0; i < 5; i++) {
            userSkillList.add(new JsonUserSkill(skills.get(i).getId(),i));
            specialist = userRepo.save(new Specialist(0
                    , "skillstest@test.com",
                    "test",
                    "users/avatars/1.avif",
                    "Skills", "Test",
                    new Address(
                            "Hoorn",
                            "Noorder Plantsoen",
                            12,
                            "",
                            "1623AB"),
                    "users/resumes/sample-resume.pdf"));
//            userRepo.findUsersByRole(User.Role.SPECIALIST, Specialist.class).get(0)

        }

    }

    @Autowired
    private TestRestTemplate restTemplate;
    @Test
    void getSkillGroups() {
        ResponseEntity<String> response = restTemplate.getForEntity("/skills/groups", String.class);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getUserSkills() {
        ResponseEntity<String> response = restTemplate.getForEntity("/skills/user-skills/" + specialist.getId(), String.class);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getAllExpertises() {
        ResponseEntity<String> response = restTemplate.getForEntity("/skills/expertises", String.class);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getExpertise() {
        ResponseEntity<String> response = restTemplate.getForEntity("/skills/expertise/" + specialist.getId(), String.class);
        assertEquals(200, 200);
    }

    @Test
    void getUserExpertises() {
        ResponseEntity<String> response = restTemplate.getForEntity("/skills/user-expertises/" + specialist.getId(), String.class);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void updateUserSkill() throws JsonProcessingException {
        //send request
        restTemplate.put("/skills/update-user-skill/" + specialist.getId(), userSkillList.get(0));

        //check if request is added
        ResponseEntity<String> response1 = restTemplate.getForEntity("/skills/user-skills/" + specialist.getId(), String.class);
        assertEquals(200, response1.getStatusCodeValue());
        List<UserSkill> userSkill = mapper.readValue(response1.getBody(), new TypeReference<List<UserSkill>>(){});
        assertEquals(new JsonUserSkill((userSkill.get(0).getSkill().getId()), userSkill.get(0).getRating()).toString(), userSkillList.get(0).toString());

        //update request
        JsonUserSkill updatedSkill = new JsonUserSkill(userSkillList.get(0).getId(), 5);
        restTemplate.put("/skills/update-user-skill/" + specialist.getId(), updatedSkill);
        ResponseEntity<String> response3 = restTemplate.getForEntity("/skills/user-skills/" + specialist.getId(), String.class);
        assertEquals(200, response3.getStatusCodeValue());
        List<UserSkill> userSkill2 = mapper.readValue(response3.getBody(), new TypeReference<List<UserSkill>>(){});
        assertEquals(new JsonUserSkill((userSkill2.get(0).getSkill().getId()), userSkill2.get(0).getRating()).toString(), updatedSkill.toString());

    }


//    @PutMapping(path = "/update-user-skill-group/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<UserSkill>> updateUserSkillGroup(@PathVariable int userId, @RequestBody List<JsonUserSkill> body) {
//        return ResponseEntity.ok(skillBusinessLogic.updateUserSkillGroup(userId, body));
//    }

//    @PutMapping(path = "/update-user-expertise/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<ArrayList<Expertise>> updateUserExpertise(@PathVariable int userId, @RequestBody ArrayList<Expertise> body) {
//        return ResponseEntity.ok(skillBusinessLogic.updateUserExpertise(userId, body));
//    }
}
