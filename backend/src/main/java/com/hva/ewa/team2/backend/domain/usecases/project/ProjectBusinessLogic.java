package com.hva.ewa.team2.backend.domain.usecases.project;

import com.fasterxml.jackson.databind.JsonNode;
import com.hva.ewa.team2.backend.domain.models.Project.Project;
import com.hva.ewa.team2.backend.domain.models.Project.ProjectParticipant;

import java.util.List;

public interface ProjectBusinessLogic {

    Project createProject(Project project);

    Project getProjectInformation(int id);

    boolean deleteProject(int id);

    void updateProject(int pId, Project project);

    List<ProjectParticipant> getProjectParticipants(int id);

    ProjectParticipant getProjectParticipant(int projectId, int userId);

    ProjectParticipant removeProjectParticipant(int projectId, int userId);

    ProjectParticipant addProjectParticipant(int projectId, JsonNode jsonBody);

    List<Project> getAllProjects();

}
