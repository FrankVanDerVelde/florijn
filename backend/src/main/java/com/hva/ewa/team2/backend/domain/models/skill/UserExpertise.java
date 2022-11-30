package com.hva.ewa.team2.backend.domain.models.skill;

import lombok.Getter;
import lombok.Setter;

public class UserExpertise {

    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private int userId;

    public UserExpertise() {
    }

    public UserExpertise(int id, int userId) {
        this.id = id;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", userId=" + userId +
                '}';
    }
}
