package com.hva.ewa.team2.backend.domain.models.skill;

import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class UserSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;

    @Getter @Setter
    @OneToOne(targetEntity = Specialist.class)
    @JoinColumn(name = "user_id")
    private Integer userId;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @Getter
    @Setter
    private int rating;

    public UserSkill() {
    }

    public UserSkill(int id, Skill skill, int rating) {
        this.id = id;
        this.skill = skill;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", skill=" + skill +
                ", rating=" + rating +
                '}';
    }
}
