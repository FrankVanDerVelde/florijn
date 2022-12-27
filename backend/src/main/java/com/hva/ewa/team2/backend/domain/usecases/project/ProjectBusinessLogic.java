package com.hva.ewa.team2.backend.domain.usecases.project;

import com.fasterxml.jackson.databind.JsonNode;
import com.hva.ewa.team2.backend.domain.models.project.Project;
import com.hva.ewa.team2.backend.domain.models.project.ProjectFilter;
import com.hva.ewa.team2.backend.domain.models.project.ProjectParticipant;
import com.hva.ewa.team2.backend.domain.models.project.ProjectReport;
import com.hva.ewa.team2.backend.rest.project.request.ProjectEditVerificationRequest;
import com.hva.ewa.team2.backend.rest.project.request.ProjectInfoRequest;
import com.hva.ewa.team2.backend.rest.project.request.ProjectParticipantAddInfoRequest;
import com.hva.ewa.team2.backend.rest.project.request.ProjectTransferRequest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProjectBusinessLogic {

    Project createProject(ProjectInfoRequest project) throws IOException;

    Project getProjectInformation(int id);

    boolean deleteProject(int id);

    Project updateProjectInformation(int pId, ProjectInfoRequest jsonBody) throws IOException;

    Project archiveProject(int pId, ProjectEditVerificationRequest body, boolean unarchive);

    Project transferOwnership(int pId, ProjectTransferRequest body);

    List<ProjectParticipant> getProjectParticipants(int id);

    ProjectParticipant getProjectParticipant(int projectId, int userId);

    ProjectParticipant removeProjectParticipant(int projectId, int userId);

    ProjectParticipant addProjectParticipant(int projectId, ProjectParticipantAddInfoRequest jsonBody);

    List<ProjectReport> getProjectReports(int projectId, int userId);

    List<Project> getAllProjects(Optional<String> searchQuery, Optional<String> filter);

}
