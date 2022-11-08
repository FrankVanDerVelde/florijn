package com.hva.ewa.team2.backend.domain.models.Project;

import com.hva.ewa.team2.backend.domain.models.Specialist.Specialist;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Project {

    @Getter @Setter
    private final int id;
    @Getter @Setter
    private final String name;
    @Getter @Setter
    private final String description;
    @Getter @Setter
    private List<Specialist> specialists;

    public Project(int id, String name, String description, List<Specialist> specialists) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.specialists = specialists;
    }
}
