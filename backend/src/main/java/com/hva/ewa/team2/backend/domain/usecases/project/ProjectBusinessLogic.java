package com.hva.ewa.team2.backend.domain.usecases.project;

import com.fasterxml.jackson.databind.JsonNode;
import com.hva.ewa.team2.backend.domain.models.project.Project;
import com.hva.ewa.team2.backend.domain.models.project.ProjectParticipant;
import com.hva.ewa.team2.backend.domain.models.project.ProjectReport;
import com.hva.ewa.team2.backend.rest.project.json.JsonProjectInfo;
import com.hva.ewa.team2.backend.rest.project.json.JsonProjectParticipantAddInfo;

import java.util.List;
import java.util.Optional;

public interface ProjectBusinessLogic {

    Project createProject(JsonProjectInfo project);

    Project getProjectInformation(int id);

    boolean deleteProject(int id);

    Project updateProjectInformation(int pId, JsonProjectInfo jsonBody);

    List<ProjectParticipant> getProjectParticipants(int id);

    ProjectParticipant getProjectParticipant(int projectId, int userId);

    ProjectParticipant removeProjectParticipant(int projectId, int userId);

    ProjectParticipant addProjectParticipant(int projectId, JsonProjectParticipantAddInfo jsonBody);

    List<ProjectReport> getProjectReports(int projectId, JsonNode body);

    List<Project> getAllProjects(Optional<String> searchQuery);

}
