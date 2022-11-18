package com.hva.ewa.team2.backend.data.skill;

import com.hva.ewa.team2.backend.domain.models.skill.Skill;
import com.hva.ewa.team2.backend.domain.models.user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InMemorySkillRepository implements SkillRepository {

    private ArrayList<Skill> skills = new ArrayList<>();

    public InMemorySkillRepository() {
        List<String> list = Arrays.asList(new String[]{"Javascript", "PHP", "HTML", "CSS", "React", "VueJS", "Node", "Git", "Python", "C++", "c#", "Lua", "Swift", "Angular", "Wordpress"});

        for (String element : list) {
            this.skills.add(new Skill(0, element, "Your ability to use " + element, 5));
        }
    }

    @Override
    public ArrayList<Skill> findAll() {
        return this.skills;
    }

    @Override
    public Skill findById(int id) {
        return this.skills.stream().filter(skill -> skill.getId() == id)
                .findFirst().orElse(null);
    }
}
