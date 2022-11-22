package com.hva.ewa.team2.backend.domain.usecases.project;

import com.fasterxml.jackson.databind.JsonNode;
import com.hva.ewa.team2.backend.common.Services.DateService.DateServiceLogic;
import com.hva.ewa.team2.backend.data.hourregistration.HourRegistrationRepository;
import com.hva.ewa.team2.backend.data.project.ProjectRepository;
import com.hva.ewa.team2.backend.data.user.UserRepository;
import com.hva.ewa.team2.backend.domain.models.hourregistration.HourRegistration;
import com.hva.ewa.team2.backend.domain.models.project.Project;
import com.hva.ewa.team2.backend.domain.models.project.ProjectParticipant;
import com.hva.ewa.team2.backend.domain.models.project.ProjectReport;
import com.hva.ewa.team2.backend.domain.models.user.Client;
import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import com.hva.ewa.team2.backend.domain.models.user.User;
import com.hva.ewa.team2.backend.rest.project.json.JsonProjectInfo;
import com.hva.ewa.team2.backend.rest.project.json.JsonProjectParticipantAddInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectInteractor implements ProjectBusinessLogic {

    private final UserRepository userRepo;
    private final ProjectRepository projectRepo;
    private final HourRegistrationRepository hourRegistrationRepo;
    private final DateServiceLogic dateService;

    @Autowired
    public ProjectInteractor(ProjectRepository projectRepository,
                             UserRepository userRepo,
                             HourRegistrationRepository hourRegistrationRepo,
                             DateServiceLogic dateService) {
        this.projectRepo = projectRepository;
        this.userRepo = userRepo;
        this.hourRegistrationRepo = hourRegistrationRepo;
        this.dateService = dateService;
    }

    @Override
    public Project createProject(JsonProjectInfo projectInfo) {
        final String title = projectInfo.getTitle();
        final int clientId = projectInfo.getClient();
        final String description = projectInfo.getDescription();
        final String logoSrc = projectInfo.getLogoSrc();

        validateProjectInformation(title, clientId, description);

        // creating temp project to update.
        Project project;
        if (logoSrc == null) {
            project = new Project(
                    projectRepo.findAll().size() + 1, title, description,
                    (Client) userRepo.findById(clientId)
            );
        } else {
            project = new Project(
                    projectRepo.findAll().size() + 1, title, description,
                    (Client) userRepo.findById(clientId), logoSrc
            );
        }

        return projectRepo.addProject(project);
    }

    @Override
    public Project getProjectInformation(int id) {
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
    public Project updateProjectInformation(int pId, JsonProjectInfo body) {
        final String title = body.getTitle();
        final int client = body.getClient();
        final String description = body.getDescription();
        final String logoSrc = body.getLogoSrc();

        validateProjectInformation(
                title,
                client,
                description
        );

        if (projectRepo.findById(pId) == null) {
            throw new IllegalArgumentException("The project with ID " + pId + " does not exist.");
        }

        // creating temp project to update.
        Project project;
        if (logoSrc == null) {
            project = new Project(
                    pId, title, description,
                    (Client) userRepo.findById(client)
            );
        } else {
            project = new Project(
                    pId, title, description,
                    (Client) userRepo.findById(client), logoSrc
            );
        }

        return projectRepo.updateProject(project);
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

        final ProjectParticipant participant = project.getParticipantByUserId(userId);
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

        final ProjectParticipant participant = project.getParticipantByUserId(userId);
        if (participant == null) {
            throw new IllegalArgumentException("The user with ID " + userId + " is not a participant of the project with ID " + projectId + ".");
        }

        final boolean removed = project.removeSpecialist(participant);
        return removed ? participant : null;
    }

    @Override
    public ProjectParticipant addProjectParticipant(int projectId, JsonProjectParticipantAddInfo body) {
        final Project project = projectRepo.findById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("The project with ID " + projectId + " does not exist.");
        }
        final String role = body.getRole();
        if (role.isBlank()) {
            throw new IllegalArgumentException("The provided role is invalid (not provided/empty).");
        }
        final double hourlyRate = body.getHourlyRate();
        if (hourlyRate < 0) {
            throw new IllegalArgumentException("The provided hourly rate is invalid (not provided/negative number).");
        }

        final int userId = body.getUserId();
        if (!(userRepo.findById(userId) instanceof Specialist specialist)) {
            throw new IllegalArgumentException("The user with ID " + userId + " does not exist or is not a specialist.");
        }
        if (project.getParticipantByUserId(userId) != null) {
            throw new IllegalArgumentException("User " + userId + " is already participating this project.");
        }

        final ProjectParticipant created = new ProjectParticipant(specialist, role, hourlyRate);
        project.addSpecialist(created);
        return created;
    }

    @Override
    public List<ProjectReport> getProjectReports(int projectId, JsonNode body) {
        final Project project = projectRepo.findById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("The project with ID " + projectId + " does not exist.");
        }

        if (!body.has("userId")) {
            throw new IllegalArgumentException("No user id was provided.");
        }

        final int userId = body.get("userId").asInt(-1);
        final User user = userRepo.findById(userId);

        if (user == null) {
            throw new IllegalArgumentException("The user with ID " + userId + " does not exist.");
        }

        List<ProjectReport> reports = new ArrayList<>();

        if (user instanceof Specialist specialist) {
            List<HourRegistration> projectRegistrations = hourRegistrationRepo.fetchAllHourRegistrationByProjectUser(projectId, userId);

            reports.add(new ProjectReport(
                    "Totaal gemaakte uren",
                    String.format("%.2f", projectRegistrations.stream()
                            .filter(reg -> reg.getStatus().isEmpty() || reg.getStatus().get() == HourRegistration.Status.ACCEPTED)
                            .mapToDouble(HourRegistration::getHoursSpent).sum())
            ));

            reports.add(new ProjectReport(
                    "Gemaakte uren deze maand",
                    String.format("%.2f", projectRegistrations.stream()
                            .filter(reg -> dateService.isThisMonth(reg.getFrom()))
                            .filter(reg -> reg.getStatus().isEmpty() || reg.getStatus().get() == HourRegistration.Status.ACCEPTED)
                            .mapToDouble(HourRegistration::getHoursSpent)
                            .sum()
                    )
            ));

            reports.add(new ProjectReport(
                    "Gemaakte uren deze week",
                    String.format("%.2f", projectRegistrations.stream()
                            .filter(reg -> dateService.isThisWeek(reg.getFrom()))
                            .filter(reg -> reg.getStatus().isEmpty() || reg.getStatus().get() == HourRegistration.Status.ACCEPTED)
                            .mapToDouble(HourRegistration::getHoursSpent)
                            .sum()
                    )
            ));

            reports.add(new ProjectReport(
                    "Verdiensten",
                    String.format("€%,.2f", projectRegistrations.stream()
                            .filter(reg -> reg.getStatus().isEmpty() || reg.getStatus().get() == HourRegistration.Status.ACCEPTED)
                            .mapToDouble(reg -> reg.getHoursSpent() * reg.getProjectParticipant().getHourlyRate())
                            .sum()
                    ).replace(".", ",")
            ));
        } else {
            List<HourRegistration> projectRegistrations = hourRegistrationRepo.fetchAllHourRegistrationByProject(projectId);

            reports.add(new ProjectReport(
                    "Totaal gemaakte uren",
                    String.format("%.2f", projectRegistrations.stream()
                            .filter(reg -> reg.getStatus().isEmpty() || reg.getStatus().get() == HourRegistration.Status.ACCEPTED)
                            .mapToDouble(HourRegistration::getHoursSpent).sum())
            ));

            reports.add(new ProjectReport(
                    "Gemaakte uren deze maand",
                    String.format("%.2f", projectRegistrations.stream()
                            .filter(reg -> dateService.isThisMonth(reg.getFrom()))
                            .filter(reg -> reg.getStatus().isEmpty() || reg.getStatus().get() == HourRegistration.Status.ACCEPTED)
                            .mapToDouble(HourRegistration::getHoursSpent)
                            .sum()
                    )
            ));

            reports.add(new ProjectReport(
                    "Gemaakte uren deze week",
                    String.format("%.2f", projectRegistrations.stream()
                            .filter(reg -> dateService.isThisWeek(reg.getFrom()))
                            .filter(reg -> reg.getStatus().isEmpty() || reg.getStatus().get() == HourRegistration.Status.ACCEPTED)
                            .mapToDouble(HourRegistration::getHoursSpent)
                            .sum()
                    )
            ));

            reports.add(new ProjectReport(
                    "Ontwikkelkosten",
                    String.format("€%,.2f", projectRegistrations.stream()
                            .filter(reg -> reg.getStatus().isEmpty() || reg.getStatus().get() == HourRegistration.Status.ACCEPTED)
                            .mapToDouble(reg -> reg.getHoursSpent() * reg.getProjectParticipant().getHourlyRate())
                            .sum()
                    ).replace(".", ",")
            ));
        }

        return reports;
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepo.findAll();
    }

    private void validateProjectInformation(String title, int clientId, String description) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("The project title cannot be empty.");
        } else if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("The project description cannot be empty.");
        }

        User client = userRepo.findById(clientId);
        if (!(client instanceof Client)) {
            throw new IllegalArgumentException("There is no user with that ID or the user is not a client.");
        }
    }

}
