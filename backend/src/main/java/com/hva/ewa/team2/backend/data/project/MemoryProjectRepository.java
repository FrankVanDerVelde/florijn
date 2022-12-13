package com.hva.ewa.team2.backend.data.project;

import com.hva.ewa.team2.backend.domain.models.project.Project;
import com.hva.ewa.team2.backend.domain.models.project.ProjectFilter;

import java.util.List;

@Deprecated
public interface MemoryProjectRepository {

    List<Project> findAll();

    List<Project> findAll(ProjectFilter filter);

    List<Project> findAll(ProjectFilter filter, String query);

    Project findById(int id);

    Project addProject(Project project);

    Project updateProject(Project project);

    boolean deleteProject(int id);

    boolean projectExists(int id);

    List<Project> getProjectsByClient(int clientId);

    List<Project> getProjectsBySpecialist(int specialistId);

}
