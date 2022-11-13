package com.hva.ewa.team2.backend.domain.usecases.project;

import com.hva.ewa.team2.backend.domain.models.Project.Project;
import com.hva.ewa.team2.backend.domain.models.Project.ProjectParticipant;

import java.util.List;

public interface ProjectBusinessLogic {

    Project createProject(Project project);

    Project getProjectInformation(int id);

    boolean deleteProject(int id);

    Project updateProject(Project project);

    List<ProjectParticipant> getProjectParticipants(int id);

    boolean removeProjectParticipant(int projectId, int userId);

    ProjectParticipant addProjectParticipant(int projectId, int userId, String role, double hourlyRate);

}
