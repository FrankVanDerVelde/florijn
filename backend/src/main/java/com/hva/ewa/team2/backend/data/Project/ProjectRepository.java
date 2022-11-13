package com.hva.ewa.team2.backend.data.Project;

import com.hva.ewa.team2.backend.domain.models.Project.Project;

import java.util.List;

public interface ProjectRepository {

    List<Project> findAll();

    Project findById(long id);

    void addProject(Project project);

    void updateProject(Project project);

    boolean deleteProject(Project project);

    List<Project> getProjectsByClient(int clientId);

    List<Project> getProjectsBySpecialist(int specialistId);

}
