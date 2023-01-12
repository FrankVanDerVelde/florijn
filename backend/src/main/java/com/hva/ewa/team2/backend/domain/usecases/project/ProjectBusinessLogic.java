package com.hva.ewa.team2.backend.domain.usecases.project;

import com.hva.ewa.team2.backend.domain.models.project.Project;
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

    Project createProject(ProjectInfoRequest project, Integer userId) throws IOException;

    Project getProjectInformation(int id, Integer userId);

    boolean deleteProject(int id, Integer userId);

    Project updateProjectInformation(int pId, ProjectInfoRequest jsonBody, Integer userId) throws IOException;

    Project updateProjectInformation(Project project, ProjectInfoRequest jsonBody) throws IOException;

    Project archiveProject(int pId, ProjectEditVerificationRequest body, boolean unarchive, Integer userId);

    Project transferOwnership(int pId, ProjectTransferRequest body, Integer userId);

    List<ProjectParticipant> getProjectParticipants(int id, Integer userId);

    ProjectParticipant getProjectParticipant(int projectId, Integer requesterId, int userId);

    ProjectParticipant removeProjectParticipant(int projectId, Integer requesterId, int userId);

    ProjectParticipant addProjectParticipant(int projectId, Integer userId, ProjectParticipantAddInfoRequest jsonBody);

    List<ProjectReport> getProjectReports(int projectId, Integer userId);

    List<Project> getAllProjects(Optional<String> searchQuery, Optional<String> filter, Integer userId);

    List<Project> getProjectsByUser(int id, Integer userId);

    int getProjectCount(Integer userId);

    Double getEarnings(Integer userId);

    Double getHours(Integer userId);
}
