package com.hva.ewa.team2.backend.domain.usecases.project;

import com.fasterxml.jackson.databind.JsonNode;
import com.hva.ewa.team2.backend.common.services.asset.AssetService;
import com.hva.ewa.team2.backend.common.services.date.DateServiceLogic;
import com.hva.ewa.team2.backend.data.hourregistration.HourRegistrationRepository;
import com.hva.ewa.team2.backend.data.project.ProjectRepository;
import com.hva.ewa.team2.backend.data.user.UserRepository;
import com.hva.ewa.team2.backend.domain.models.hourregistration.HourRegistration;
import com.hva.ewa.team2.backend.domain.models.project.Project;
import com.hva.ewa.team2.backend.domain.models.project.ProjectFilter;
import com.hva.ewa.team2.backend.domain.models.project.ProjectParticipant;
import com.hva.ewa.team2.backend.domain.models.project.ProjectReport;
import com.hva.ewa.team2.backend.domain.models.user.Client;
import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import com.hva.ewa.team2.backend.domain.models.user.User;
import com.hva.ewa.team2.backend.rest.asset.json.FileResult;
import com.hva.ewa.team2.backend.rest.project.request.ProjectEditVerificationRequest;
import com.hva.ewa.team2.backend.rest.project.request.ProjectInfoRequest;
import com.hva.ewa.team2.backend.rest.project.request.ProjectParticipantAddInfoRequest;
import com.hva.ewa.team2.backend.rest.project.request.ProjectTransferRequest;
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
    public Project createProject(ProjectInfoRequest projectInfo) throws IOException {
        final String title = projectInfo.getTitle();
        final Optional<Integer> clientId = projectInfo.getClient();
        final String description = projectInfo.getDescription();
        final MultipartFile logoUpload = projectInfo.getLogoFile();

        validateProjectInformation(title, description);
        final Client client = validateClient(clientId.orElse(-1));

        // creating temp project to update.
        Project newProject = new Project(0, title, description, client);
        final Project savedProject = projectRepo.save(newProject);

        // TODO:
        //  If we generate the a random UUID/hash for the logo file name,
        //  we can prevent to save the object twice and save the logo to the system before saving the project to the database,
        //  passing along the logo url instantly.
        if (logoUpload != null) {
            // uploading the logo to the assets.
            String extension = FilenameUtils.getExtension(logoUpload.getName());
            final FileResult fileResult = assetService.uploadAsset(logoUpload, "projects/" + savedProject.getId() + "." + extension);

            newProject.setLogoSrc(fileResult.getPath());
            // returning the updated project with the generated logo upload src.
            return projectRepo.save(newProject);
        }
        // no logo uploaded, returning the project without a logo (default).
        return savedProject;
    }

    @Override
    public Project getProjectInformation(int id) {
        return getProjectOrThrowError(id);
    }

    @Override
    public boolean deleteProject(int id) {
        // throws error if project does not exist, therefore does not delete it.
        getProjectOrThrowError(id);

        projectRepo.deleteById(id);
        return true;
    }

    @Override
    public Project updateProjectInformation(int pId, ProjectInfoRequest body) throws IOException {
        final String title = body.getTitle();
        final String description = body.getDescription();
        final MultipartFile logoUpload = body.getLogoFile();

        validateProjectInformation(
                title,
                description
        );

        final Project project = getProjectOrThrowError(pId);
        project.setTitle(title);
        project.setDescription(description);

        if (logoUpload != null) {
            String extension = FilenameUtils.getExtension(logoUpload.getOriginalFilename());
            final FileResult fileResult = assetService.uploadAsset(logoUpload, "projects/logo-" + pId + "." + extension, true);

            project.setLogoSrc(fileResult.getPath());
        }

        return projectRepo.save(project);
    }

    @Override
    public Project archiveProject(int id, ProjectEditVerificationRequest body, boolean unarchive) {
        Project project = getProjectOrThrowError(id);
        String archiveWord = unarchive ? "unarchive" : "archive";

        if (!project.getTitle().equalsIgnoreCase(body.getTitle())) {
            throw new IllegalArgumentException("Confirmation title does not match the project title.");
        }

        if (project.isArchived() != unarchive) {
            throw new IllegalArgumentException("Could not " + archiveWord + " project because it is already " + archiveWord + "d.");
        }

        project.setArchived(!unarchive);
        return project;
    }

    @Override
    public Project transferOwnership(int id, ProjectTransferRequest body) {
        Project project = getProjectOrThrowError(id);

        if (!project.getTitle().equalsIgnoreCase(body.getTitle())) {
            throw new IllegalArgumentException("Confirmation title does not match the project title.");
        }

        int clientId = body.getClientId();
        if (project.getClient().getId() == clientId) {
            throw new IllegalArgumentException("That client is already assigned to this project.");
        }

        final Client client = validateClient(clientId);
        project.setClient(client);

        return project;
    }

    @Override
    public List<ProjectParticipant> getProjectParticipants(int id) {
        final Project project = getProjectOrThrowError(id);
        return project.getParticipants();
    }

    @Override
    public ProjectParticipant getProjectParticipant(int projectId, int userId) {
        final Project project = getProjectOrThrowError(projectId);

        final ProjectParticipant participant = project.getParticipantByUserId(userId);
        if (participant == null) {
            throw new IllegalArgumentException("The user with ID " + userId + " is not a participant of the project with ID " + projectId + ".");
        }

        return participant;
    }

    @Override
    public ProjectParticipant removeProjectParticipant(int projectId, int userId) {
        final Project project = getProjectOrThrowError(projectId);

        final ProjectParticipant participant = project.getParticipantByUserId(userId);
        if (participant == null) {
            throw new IllegalArgumentException("The user with ID " + userId + " is not a participant of the project with ID " + projectId + ".");
        }

        final boolean removed = project.removeSpecialist(participant);
        return removed ? participant : null;
    }

    @Override
    public ProjectParticipant addProjectParticipant(int projectId, ProjectParticipantAddInfoRequest body) {
        final Project project = getProjectOrThrowError(projectId);
        final String role = body.getRole();
        if (role.isBlank()) {
            throw new IllegalArgumentException("The provided role is invalid (not provided/empty).");
        }
        final double hourlyRate = body.getHourlyRate();
        if (hourlyRate < 0) {
            throw new IllegalArgumentException("The provided hourly rate is invalid (not provided/negative number).");
        }

        final int userId = body.getUserId();
        if (!(userRepo.findById(userId).orElse(null) instanceof Specialist specialist)) {
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
        final Project project = getProjectOrThrowError(projectId);

        // TODO: Integrate backend authorisation here.
        if (!body.has("userId")) {
            throw new IllegalArgumentException("No user id was provided.");
        }

        final int userId = body.get("userId").asInt(-1);
        final Optional<User> user = userRepo.findById(userId);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("The user with ID " + userId + " does not exist.");
        }

        List<ProjectReport> reports = new ArrayList<>();

        if (user.get() instanceof Specialist specialist) {
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
    public List<Project> getAllProjects(Optional<String> searchQuery, Optional<ProjectFilter> filter) {
        if (filter.isEmpty()) {
            return projectRepo.findAll();
        }

        try {
            return projectRepo.findAll(filter.get(), searchQuery.orElse(""));
        } catch (Exception e) {
            throw new IllegalArgumentException("An invalid filter was entered.");
        }
    }

    private void validateProjectInformation(String title, String description) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("The project title cannot be empty.");
        } else if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("The project description cannot be empty.");
        }
    }

    private Client validateClient(int clientId) {
        User client = userRepo.findById(clientId).orElse(null);
        if (!(client instanceof Client casted)) {
            throw new IllegalArgumentException("There is no user with that ID or the user is not a client.");
        }
        return casted;
    }

    private Project getProjectOrThrowError(int id) throws IllegalArgumentException {
        final Optional<Project> project = projectRepo.findById(id);
        if (project.isEmpty()) {
            throw new IllegalArgumentException("The project with ID " + id + " does not exist.");
        }
        return project.get();
    }

}
