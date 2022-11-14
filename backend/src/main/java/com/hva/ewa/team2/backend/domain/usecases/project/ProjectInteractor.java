package com.hva.ewa.team2.backend.domain.usecases.project;

import com.fasterxml.jackson.databind.JsonNode;
import com.hva.ewa.team2.backend.data.Project.ProjectRepository;
import com.hva.ewa.team2.backend.data.Specialist.SpecialistRepository;
import com.hva.ewa.team2.backend.domain.models.Project.Project;
import com.hva.ewa.team2.backend.domain.models.Project.ProjectParticipant;
import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectInteractor implements ProjectBusinessLogic{

    // TODO: implement the project logic.
    private final SpecialistRepository specialistRepo;
    private final ProjectRepository projectRepo;

    @Autowired
    public ProjectInteractor(ProjectRepository projectRepository, SpecialistRepository specialistRepository) {
        this.projectRepo = projectRepository;
        this.specialistRepo = specialistRepository;
    }

    @Override
    public Project createProject(Project project) {
        if (project.getId() > 0) {
            throw new IllegalArgumentException("It is not possible to create a project with a specific ID. Please leave the ID empty or set it to <0.");
        }
        validateProject(project);

        return projectRepo.addProject(project);
    }

    @Override
    public Project getProjectInformation(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("The project ID cannot be negative.");
        }

        final Project project = projectRepo.findById(id);
        if (project == null) {
            throw new IllegalArgumentException("The project with ID " + id + " does not exist.");
        }

        return project;
    }

    @Override
    public boolean deleteProject(int id) {
        if (projectRepo.findById(id) == null) {
            throw new IllegalArgumentException("The project with ID " + id + " does not exist.");
        }

        return projectRepo.deleteProject(id);
    }

    @Override
    public void updateProject(int pId, Project project) {
        if (pId < 0) {
            throw new IllegalArgumentException("The project ID cannot be negative.");
        }
        validateProject(project);

        project.setId(pId);
        projectRepo.updateProject(project);
    }

    @Override
    public List<ProjectParticipant> getProjectParticipants(int id) {
        final Project project = projectRepo.findById(id);
        if (project == null) {
            throw new IllegalArgumentException("The project with ID " + id + " does not exist.");
        }

        return project.getParticipants();
    }

    @Override
    public ProjectParticipant getProjectParticipant(int projectId, int userId) {
        final Project project = projectRepo.findById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("The project with ID " + projectId + " does not exist.");
        }

        final ProjectParticipant participant = project.getParticipantById(userId);
        if (participant == null) {
            throw new IllegalArgumentException("The user with ID " + userId + " is not a participant of the project with ID " + projectId + ".");
        }

        return participant;
    }

    @Override
    public ProjectParticipant removeProjectParticipant(int projectId, int userId) {
        final Project project = projectRepo.findById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("The project with ID " + projectId + " does not exist.");
        }

        final ProjectParticipant participant = project.getParticipantById(userId);
        if (participant == null) {
            throw new IllegalArgumentException("The user with ID " + userId + " is not a participant of the project with ID " + projectId + ".");
        }

        final boolean removed = project.removeSpecialist(participant);
        return removed ? participant : null;
    }

    @Override
    public ProjectParticipant addProjectParticipant(int projectId, JsonNode body) {
        final Project project = projectRepo.findById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("The project with ID " + projectId + " does not exist.");
        }
        final int userId = body.get("userId").asInt(-1);
        if (userId < 0) {
            throw new IllegalArgumentException("The provided user ID is invalid.");
        }
        final String role = body.get("role").asText("");
        if (role.isBlank()) {
            throw new IllegalArgumentException("The provided role is invalid (not provided/empty).");
        }
        final double hourlyRate = body.get("hourlyRate").asDouble(-1);
        if (hourlyRate < 0) {
            throw new IllegalArgumentException("The provided hourly rate is invalid (not provided/negative number).");
        }

        final Specialist specialist = specialistRepo.findById(userId);
        if (specialist == null) {
            // todo check if retrieved user is a specialist?
            throw new IllegalArgumentException("The user with ID " + userId + " does not exist or is not a specialist.");
        }
        if (project.getParticipantById(userId) != null) {
            throw new IllegalArgumentException("User " + userId + " is already participating this project.");
        }

        return new ProjectParticipant(specialist, role, hourlyRate);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepo.findAll();
    }

    private void validateProject(Project project) {
        if (project.getTitle() == null || project.getTitle().isBlank()) {
            throw new IllegalArgumentException("The project title cannot be empty.");
        } else if (project.getClient() == null) {
            throw new IllegalArgumentException("The project must have a client.");
        } else if (project.getDescription() == null || project.getDescription().isBlank()) {
            throw new IllegalArgumentException("The project description cannot be empty.");
        }
    }

}
