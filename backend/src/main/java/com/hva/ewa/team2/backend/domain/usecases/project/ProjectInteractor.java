package com.hva.ewa.team2.backend.domain.usecases.project;

import com.hva.ewa.team2.backend.common.services.asset.AssetService;
import com.hva.ewa.team2.backend.common.services.date.DateServiceLogic;
import com.hva.ewa.team2.backend.data.hourregistration.HourRegistrationRepository;
import com.hva.ewa.team2.backend.data.project.ProjectRepository;
import com.hva.ewa.team2.backend.data.user.UserRepository;
import com.hva.ewa.team2.backend.domain.models.project.Project;
import com.hva.ewa.team2.backend.domain.models.project.ProjectFilter;
import com.hva.ewa.team2.backend.domain.models.project.ProjectParticipant;
import com.hva.ewa.team2.backend.domain.models.project.ProjectReport;
import com.hva.ewa.team2.backend.domain.models.user.Admin;
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
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;

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
        return projectRepo.save(project);
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

        return projectRepo.save(project);
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
    public List<ProjectReport> getProjectReports(int projectId, int userId) {
        final Project project = getProjectOrThrowError(projectId);

        // TODO: Integrate backend authorisation here.
        final Optional<User> user = userRepo.findById(userId);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("The user with ID " + userId + " does not exist.");
        }

        List<ProjectReport> reports = new ArrayList<>();

        final TemporalField weekField = WeekFields.of(Locale.GERMANY).weekOfWeekBasedYear();

        LocalDateTime startMonth = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime endMonth = LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth());
        LocalDateTime startWeek = LocalDateTime.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDateTime endWeek = LocalDateTime.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        if (user.get() instanceof Specialist specialist) {
            reports.add(new ProjectReport(
                    "Totaal gemaakte uren",
                    hourRegistrationRepo.getTotalHoursForProject(projectId, userId) + ""
            ));

            reports.add(new ProjectReport(
                    "Gemaakte uren deze maand",
                    hourRegistrationRepo.getTotalHoursForProjectBetween(projectId, userId, startMonth, endMonth) + ""
            ));

            reports.add(new ProjectReport(
                    "Gemaakte uren deze week",
                    hourRegistrationRepo.getTotalHoursForProjectBetween(projectId, userId, startWeek, endWeek) + ""
            ));

            reports.add(new ProjectReport(
                    "Verdiensten",
                    String.format("€%,.2f", hourRegistrationRepo.getTotalRevenueForProject(projectId, userId)
                    ).replace(".", ",")
            ));
        } else {
            final Object totalHoursForProject = hourRegistrationRepo.getTotalHoursForProject(projectId);
            System.out.println(totalHoursForProject);
            reports.add(new ProjectReport(
                    "Totaal gemaakte uren",
                    totalHoursForProject + ""
            ));

            reports.add(new ProjectReport(
                    "Gemaakte uren deze maand",
                    hourRegistrationRepo.getTotalHoursForProjectBetween(projectId, startMonth, endMonth) + ""
            ));

            reports.add(new ProjectReport(
                    "Gemaakte uren deze week",
                    hourRegistrationRepo.getTotalHoursForProjectBetween(projectId, startWeek, endWeek) + ""
            ));

            reports.add(new ProjectReport(
                    "Ontwikkelkosten",
                    String.format("€%.2f", hourRegistrationRepo.getTotalCostsForProject(projectId))
                            .replace(".", ",")
            ));
        }

        return reports;
    }

    @Override
    public List<Project> getAllProjects(Optional<String> searchQuery, Optional<String> filter, Optional<Integer> userId) {
        boolean queryPresent = searchQuery.isPresent();
        boolean filterPresent = filter.isPresent();
        boolean userIdPresent = userId.isPresent();

        // setting the userId option to null if it is an admin that makes the request.
        // otherwise this method will check if the user is part of the project.
        if (userIdPresent && userRepo.findById(userId.get()).orElse(null) instanceof Admin) {
            userId = Optional.empty();
            userIdPresent = false;
        }

        if (queryPresent && filterPresent) {
            if (userIdPresent) return projectRepo.findAllByQuery(ProjectFilter.valueOf(filter.get()), searchQuery.get(), userId.get());
            return projectRepo.findAllByQuery(ProjectFilter.valueOf(filter.get()), searchQuery.get());
        } else if (queryPresent) {
            if (userIdPresent) return projectRepo.findAllByQuery(searchQuery.get(), userId.get());
            return projectRepo.findAllByQuery(searchQuery.get());
        } else if (filterPresent) {
            if (userIdPresent) return projectRepo.findAll(ProjectFilter.valueOf(filter.get()), userId.get());
            return projectRepo.findAll(ProjectFilter.valueOf(filter.get()));
        } else {
            if (userIdPresent) return projectRepo.findAll(userId.get());
            return projectRepo.findAll();
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
    @Override
    public int getProjectCount(Optional<Integer> userId) {
        if (userId.isPresent()) return projectRepo.getCount(userId.get());
        return projectRepo.getCount();
    }

    @Override
    public Double getEarnings(Integer userId) {
        final Double totalRevenueForUser = hourRegistrationRepo.getTotalRevenueForUser(userId);
        System.out.println("getEarnings: " + totalRevenueForUser);
        return totalRevenueForUser;
    }

    @Override
    public Double getHours(Integer userId) {
        return hourRegistrationRepo.getTotalHoursForUser(userId);
    }
}
