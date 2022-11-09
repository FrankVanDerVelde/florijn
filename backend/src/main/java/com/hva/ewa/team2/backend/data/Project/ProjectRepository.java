package com.hva.ewa.team2.backend.data.Project;

import com.hva.ewa.team2.backend.domain.models.Project.Project;

import java.util.List;

public interface ProjectRepository {

    List<Project> findAll();

    Project findById(long id);

    Project insert();

    Project update();

    Project delete();

}
