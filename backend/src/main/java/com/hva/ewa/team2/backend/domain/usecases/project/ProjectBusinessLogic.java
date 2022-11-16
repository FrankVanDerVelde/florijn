package com.hva.ewa.team2.backend.domain.usecases.project;

import com.hva.ewa.team2.backend.domain.models.Project.Project;
import com.hva.ewa.team2.backend.domain.models.Project.ProjectParticipant;
import com.hva.ewa.team2.backend.rest.project.json.JsonProjectInfo;
import com.hva.ewa.team2.backend.rest.project.json.JsonProjectParticipantAddInfo;

import java.util.List;

public interface ProjectBusinessLogic {

    Project createProject(JsonProjectInfo project);

    Project getProjectInformation(int id);

    boolean deleteProject(int id);

    Project updateProjectInformation(int pId, JsonProjectInfo jsonBody);

    List<ProjectParticipant> getProjectParticipants(int id);

    ProjectParticipant getProjectParticipant(int projectId, int userId);

    ProjectParticipant removeProjectParticipant(int projectId, int userId);

    ProjectParticipant addProjectParticipant(int projectId, JsonProjectParticipantAddInfo jsonBody);

    List<Project> getAllProjects();

}
