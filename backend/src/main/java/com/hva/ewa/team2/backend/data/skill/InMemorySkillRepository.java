package com.hva.ewa.team2.backend.data.skill;

import com.hva.ewa.team2.backend.domain.models.skill.Skill;
import com.hva.ewa.team2.backend.domain.models.skill.SkillGroup;
import com.hva.ewa.team2.backend.domain.models.user.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class InMemorySkillRepository implements SkillRepository {

    public static void main(String[] args) {
        new InMemorySkillRepository();
    }

    private ArrayList<Skill> skills = new ArrayList<>();

    private ArrayList<SkillGroup> skillGroups = new ArrayList<>();

    public InMemorySkillRepository() {
        String[] skillNames = new String[]{"HTML", "CSS", "Javascript", "PHP", "Node", "Python", "Swift", "C++", "c#", "React", "VueJS", "Angular", "Wordpress", "Lua","Git"};
        String[] groupNames = new String[]{"Front-End", "Back-End", "Frameworks", "Other"};
        Integer[] skillPositions = {3, 6, 4, 2};

        for (int i = 0; i < skillNames.length; i++) {
            this.skills.add(new Skill(i, skillNames[i], "Your ability to use " + skillNames[i]));
        }

        int lastPosition = 0;
        for (int i = 0; i < groupNames.length; i++) {
            SkillGroup tempSkillGroup = new SkillGroup(i, groupNames[i], "This is the group for  " + groupNames[i]);

            for (int i2 = lastPosition; i2 < skillPositions[i]; i2++) {
                tempSkillGroup.add(skills.get(lastPosition));
                lastPosition++;
            }

            this.skillGroups.add(tempSkillGroup);

        }

        System.out.println(skillGroups);
    }



    @Override
    public ArrayList<Skill> findAllSkills() {
        return this.skills;
    }

    @Override
    public Skill findSkillById(int id) {
        return this.skills.stream().filter(skill -> skill.getId() == id)
                .findFirst().orElse(null);
    }

    @Override
    public ArrayList<SkillGroup> findAllSkillGroups() {
        return skillGroups;
    }

    @Override
    public SkillGroup findGroupById(int id) {
        return this.skillGroups.stream().filter(group -> group.getId() == id)
                .findFirst().orElse(null);
    }
}
