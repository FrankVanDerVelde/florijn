package com.hva.ewa.team2.backend.data.hourregistration;

import com.hva.ewa.team2.backend.common.Services.DateService.DateServiceLogic;
import com.hva.ewa.team2.backend.data.project.ProjectRepository;
import com.hva.ewa.team2.backend.domain.models.hourregistration.CreateHourRegistrationRequest;
import com.hva.ewa.team2.backend.domain.models.hourregistration.HourRegistration;
import com.hva.ewa.team2.backend.domain.models.project.Project;
import com.hva.ewa.team2.backend.domain.models.project.ProjectParticipant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
@Primary
public class InMemoryHourRegistrationRepository implements HourRegistrationRepository {

    private ArrayList<HourRegistration> hourRegistrations;

    private final DateServiceLogic dateService;
    private final ProjectRepository projectRepository;

    private Project testProject;

    @Autowired
    public InMemoryHourRegistrationRepository(DateServiceLogic dateService, ProjectRepository projectRepository) {
        this.dateService = dateService;
        this.projectRepository = projectRepository;
        this.hourRegistrations = new ArrayList<>();
        setup();
    }

    private void setup() {
        setupTestProject();
        setupHourRegistrations();
    }

    private void setupTestProject() {
        testProject = projectRepository.findById(1);
    }

    private void setupHourRegistrations() {
        final ProjectParticipant developer = testProject.getParticipantByUserId(0);
        final ProjectParticipant designer = testProject.getParticipantByUserId(1);

        hourRegistrations = new ArrayList<>();
        hourRegistrations.addAll(List.of(
                new HourRegistration(
                        0,
                        testProject.getId(),
                        developer,
                        dateService.currentDay(-42, 10, 0),
                        dateService.currentDay(-42, 12, 0),
                        "Gewerkt aan het project",
                        HourRegistration.Status.ACCEPTED
                ),
                new HourRegistration(
                        1,
                        testProject.getId(),
                        designer,
                        dateService.currentDay(-39, 8, 30),
                        dateService.currentDay(-39, 12, 0),
                        "Gewerkt aan het project",
                        HourRegistration.Status.REJECTED
                ),
                new HourRegistration(
                        2,
                        testProject.getId(),
                        developer,
                        dateService.currentDay(-36, 12, 15),
                        dateService.currentDay(-36, 16, 0),
                        "Gewerkt aan het project",
                        HourRegistration.Status.ACCEPTED
                ),
                new HourRegistration(
                        3,
                        testProject.getId(),
                        developer,
                        dateService.currentDay(8, 30),
                        dateService.currentDay(12, 0),
                        "Gewerkt aan het project"
                ),
                new HourRegistration(
                        4,
                        testProject.getId(),
                        designer,
                        dateService.currentDay(13, 0),
                        dateService.currentDay(17, 30),
                        "Gewerkt aan het project"
                )
            )
        );
    }

    @Override
    public List<HourRegistration> fetchAllHourRegistrations() {
        return hourRegistrations;
    }

    @Override
    public List<HourRegistration> fetchAllHourRegistrationsByUser(int userId) {
        return hourRegistrations.stream()
                .filter(h -> h.getProjectParticipant().getSpecialist().getId() == userId)
                .sorted(Comparator.comparing(HourRegistration::getTo).reversed())
                .toList();
    }

    @Override
    public Optional<HourRegistration> fetchHourRegistrationById(int id) {
        return hourRegistrations.stream()
                .filter(h -> h.getId() == id)
                .findFirst();
    }

    @Override
    public List<HourRegistration> fetchAllHourRegistrationByProject(int projectId) {
        return hourRegistrations.stream()
                .filter(h -> h.getProjectId() == projectId)
                .sorted(Comparator.comparing(HourRegistration::getTo).reversed())
                .toList();
    }

    @Override
    public List<HourRegistration> fetchAllAcceptedHoursForProject(int projectId) {
        return hourRegistrations.stream()
                .filter(h -> h.getProjectId() == projectId)
                .filter(HourRegistration::isAccepted)
                .sorted(Comparator.comparing(HourRegistration::getTo).reversed())
                .toList();
    }

    @Override
    public List<HourRegistration> fetchAllHourRegistrationByProjectUser(int projectId, int userId) {
        return hourRegistrations.stream()
                .filter(h -> h.getProjectId() == projectId && h.getProjectParticipant().getSpecialist().getId() == userId)
                .filter(HourRegistration::isAccepted)
                .sorted(Comparator.comparing(HourRegistration::getTo).reversed())
                .toList();
    }

    @Override // Adding to an array will never crash, but the interface needs it
    public HourRegistration createHourRegistration(CreateHourRegistrationRequest request, Project project) {
        HourRegistration newHR = request.toDomainModel(nextId(), project);
        hourRegistrations.add(newHR);
        return newHR;
    }

    @Override
    public HourRegistration updateHourRegistration(int id, HourRegistration hourRegistration) {
        hourRegistrations.removeIf(h -> h.getId() == id);
        hourRegistrations.add(hourRegistration);
        return hourRegistration;
    }

    @Override
    public Optional<HourRegistration> deleteHourRegistration(int id) {
        Optional<HourRegistration> hrToDelete = fetchHourRegistrationById(id);
        if (hrToDelete.isPresent()) {
            hourRegistrations.removeIf(h -> h.getId() == id);
            return hrToDelete;
        } else {
            return Optional.empty();
        }
    }

    private int nextId() {
        Optional<Integer> highestId = hourRegistrations.stream().map(HourRegistration::getId).sorted().findFirst();
        return highestId.map(id -> id + 1).orElse(0);
    }
}
