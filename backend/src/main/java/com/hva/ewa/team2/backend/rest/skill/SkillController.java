package com.hva.ewa.team2.backend.rest.skill;

import com.hva.ewa.team2.backend.domain.models.project.Project;
import com.hva.ewa.team2.backend.domain.models.skill.SkillGroup;
import com.hva.ewa.team2.backend.domain.models.skill.UserSkill;
import com.hva.ewa.team2.backend.domain.usecases.project.ProjectBusinessLogic;
import com.hva.ewa.team2.backend.domain.usecases.skill.SkillBusinessLogic;
import com.hva.ewa.team2.backend.rest.project.json.JsonProjectInfo;
import com.hva.ewa.team2.backend.rest.skill.json.JsonUserSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/skills")
public class SkillController {

    private final SkillBusinessLogic skillBusinessLogic;

    @Autowired
    public SkillController(SkillBusinessLogic skillBusinessLogic) {
        this.skillBusinessLogic = skillBusinessLogic;
    }

    @GetMapping(path = "/skill-groups", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SkillGroup>> getSkillGroups() {
        return ResponseEntity.ok(skillBusinessLogic.getAllSkillGroups());
    }

    @GetMapping(path = "/{userId}/user-skills", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserSkill>> getUserSkills(@PathVariable int userId) {
        return ResponseEntity.ok(skillBusinessLogic.getUserSkills(userId));
    }

    @PutMapping(path = "/{userId}/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserSkill> updateProject(@PathVariable int userId,  @RequestBody JsonUserSkill body) {
        return ResponseEntity.ok(skillBusinessLogic.updateUserSkill(userId, body));
    }
}
