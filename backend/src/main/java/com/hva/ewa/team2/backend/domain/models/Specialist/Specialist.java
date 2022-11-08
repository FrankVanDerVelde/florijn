package com.hva.ewa.team2.backend.domain.models.Specialist;

import lombok.Getter;
import lombok.Setter;

public class Specialist {

    @Getter @Setter
    private final int id;
    @Getter @Setter
    private final String name;

    public Specialist(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
