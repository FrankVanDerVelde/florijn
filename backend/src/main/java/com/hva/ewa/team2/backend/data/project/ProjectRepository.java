package com.hva.ewa.team2.backend.data.project;

import com.hva.ewa.team2.backend.domain.models.project.Project;

import java.util.List;

public interface ProjectRepository {

    List<Project> findAll();

    Project findById(int id);

    Project addProject(Project project);

    Project updateProject(Project project);

    boolean deleteProject(int id);

    boolean projectExists(int id);

    List<Project> getProjectsByClient(int clientId);

    List<Project> getProjectsBySpecialist(int specialistId);

}
