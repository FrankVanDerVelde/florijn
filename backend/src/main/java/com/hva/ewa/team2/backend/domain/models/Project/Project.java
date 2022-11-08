package com.hva.ewa.team2.backend.domain.models.Project;

import lombok.Getter;
import lombok.Setter;

public class Project {

    @Getter @Setter
    private final int id;
    @Getter @Setter
    private final int projectId;
    @Getter @Setter
    private final int projectDescription;

    Project(int id, int projectId) {
        this.id = id;
        this.projectId = projectId;
        this.projectDescription = description;
    }
}
