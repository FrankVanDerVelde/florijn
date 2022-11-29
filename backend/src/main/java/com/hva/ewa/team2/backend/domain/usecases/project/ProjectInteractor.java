package com.hva.ewa.team2.backend.domain.usecases.project;

import com.fasterxml.jackson.databind.JsonNode;
import com.hva.ewa.team2.backend.common.services.asset.AssetService;
import com.hva.ewa.team2.backend.common.services.date.DateServiceLogic;
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
import com.hva.ewa.team2.backend.rest.asset.json.FileResult;
import com.hva.ewa.team2.backend.rest.project.json.JsonProjectInfo;
import com.hva.ewa.team2.backend.rest.project.json.JsonProjectParticipantAddInfo;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProjectInteractor implements ProjectBusinessLogic {

    private final UserRepository userRepo;
    private final ProjectRepository projectRepo;
    private final HourRegistrationRepository hourRegistrationRepo;
    private final DateServiceLogic dateService;
    private final AssetService assetService;

    @Autowired
    public ProjectInteractor(ProjectRepository projectRepository,
                             UserRepository userRepo,
                             HourRegistrationRepository hourRegistrationRepo,
                             DateServiceLogic dateService,
                             AssetService assetService) {
        this.projectRepo = projectRepository;
        this.userRepo = userRepo;
        this.hourRegistrationRepo = hourRegistrationRepo;
        this.dateService = dateService;
        this.assetService = assetService;
    }

    @Override
    public Project createProject(JsonProjectInfo projectInfo) throws IOException {
        final String title = projectInfo.getTitle();
        final int clientId = projectInfo.getClient();
        final String description = projectInfo.getDescription();
        final MultipartFile logoUpload = projectInfo.getLogoFile();

        validateProjectInformation(title, clientId, description);

        // creating temp project to update.
        Project project;
        final int id = projectRepo.findAll().size() + 1;
        if (logoUpload == null) {
            project = new Project(
                    id, title, description,
                    (Client) userRepo.getUserById(clientId)
            );
        } else {
            // uploading the logo to the assets.
            String extension = FilenameUtils.getExtension(logoUpload.getName());
            final FileResult fileResult = assetService.uploadAsset(logoUpload, "projects/" + id + "." + extension);

            project = new Project(
                    id, title, description,
                    (Client) userRepo.getUserById(clientId),
                    fileResult.getPath()
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
    public Project updateProjectInformation(int pId, JsonProjectInfo body) throws IOException {
        final String title = body.getTitle();
        final int client = body.getClient();
        final String description = body.getDescription();
        final MultipartFile logoUpload = body.getLogoFile();

        validateProjectInformation(
                title,
                client,
                description
        );

        final Project existingProject = projectRepo.findById(pId);
        if (existingProject == null) {
            throw new IllegalArgumentException("The project with ID " + pId + " does not exist.");
        }

        System.out.println(logoUpload);

        // creating temp project to update.
        Project project;
        if (logoUpload == null) {
            project = new Project(
                    pId, title, description,
                    (Client) userRepo.getUserById(client),
                    existingProject.getLogoSrc()
            );
        } else {
            String extension = FilenameUtils.getExtension(logoUpload.getOriginalFilename());
            final FileResult fileResult = assetService.uploadAsset(logoUpload, "projects/logo-" + pId + "." + extension, true);

            project = new Project(
                    pId, title, description,
                    (Client) userRepo.getUserById(client),
                    fileResult.getPath()
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
        if (!(userRepo.getUserById(userId) instanceof Specialist specialist)) {
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
        final User user = userRepo.getUserById(userId);

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
    public List<Project> getAllProjects(Optional<String> searchQuery) {
        if (searchQuery.orElse("").isBlank()) {
            return projectRepo.findAll();
        } else {
            String search = searchQuery.get().toLowerCase();
            return projectRepo.findAll().stream()
                    .filter(project -> project.getTitle().toLowerCase().contains(search) ||
                            project.getDescription().toLowerCase().contains(search)
                    ).toList();
        }
    }

    private void validateProjectInformation(String title, int clientId, String description) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("The project title cannot be empty.");
        } else if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("The project description cannot be empty.");
        }

        User client = userRepo.getUserById(clientId);
        if (!(client instanceof Client)) {
            throw new IllegalArgumentException("There is no user with that ID or the user is not a client.");
        }
    }

}
