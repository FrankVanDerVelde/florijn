package com.hva.ewa.team2.backend.domain.models.skill;

import lombok.Getter;
import lombok.Setter;

public class Expertise {

        @Getter
        @Setter
        private int id;

        @Getter @Setter
        private String name;

//        @Getter @Setter
//        private String description;


    public Expertise(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
        public String toString() {
            return "{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }

}
