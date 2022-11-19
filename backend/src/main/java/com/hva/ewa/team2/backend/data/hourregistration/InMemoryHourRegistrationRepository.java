package com.hva.ewa.team2.backend.data.hourregistration;

import com.hva.ewa.team2.backend.common.Services.DateService.DateServiceLogic;
import com.hva.ewa.team2.backend.domain.models.hourregistration.CreateHourRegistrationRequest;
import com.hva.ewa.team2.backend.domain.models.hourregistration.HourRegistration;
import com.hva.ewa.team2.backend.domain.models.project.Project;
import com.hva.ewa.team2.backend.domain.models.user.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Primary
public class InMemoryHourRegistrationRepository implements HourRegistrationRepository {

    private ArrayList<HourRegistration> hourRegistrations;

    private final DateServiceLogic dateService;

    private Project testProject;
    private Client testClient;

    @Autowired
    public InMemoryHourRegistrationRepository(DateServiceLogic dateService) {
        this.dateService = dateService;
        this.hourRegistrations = new ArrayList<>();
        setup();
    }

    private void setup() {
        setupTestClient();
        setupTestProject();
        setupHourRegistrations();
    }

    private void setupTestClient() {
        this.testClient = new Client(
                0,
                "test_client@florijn.nl",
                "password",
                "",
                "Test"
        );
    }

    private void setupTestProject() {
        testProject = new Project(0, "test", "a", testClient);
    }

    private void setupHourRegistrations() {
        hourRegistrations = new ArrayList<>();
        hourRegistrations.addAll(List.of(
                new HourRegistration(
                        testProject,
                        0,
                        dateService.currentDay(-3, 10, 0),
                        dateService.currentDay(-3, 12, 0),
                        "Gewerkt aan het project"
                ),
                new HourRegistration(
                        1,
                        testProject,
                        0,
                        dateService.currentDay(-2, 8, 30),
                        dateService.currentDay(-2, 12, 0),
                        "Gewerkt aan het project"
                ),
                new HourRegistration(
                        2,
                        testProject,
                        0,
                        dateService.currentDay(-1, 12, 15),
                        dateService.currentDay(-1, 16, 0),
                        "Gewerkt aan het project"
                ),
                new HourRegistration(
                        3,
                        testProject,
                        0,
                        dateService.currentDay(8, 30),
                        dateService.currentDay(12, 0),
                        "Gewerkt aan het project"
                ),
                new HourRegistration(
                        4,
                        testProject,
                        0,
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
                .filter(h -> h.getUserId() == userId)
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
                .filter(h -> h.getProject().getId() == projectId)
                .toList();
    }

    @Override
    public List<HourRegistration> fetchAllAcceptedHoursForProject(int projectId) {
        return hourRegistrations.stream()
                .filter(h -> h.getProject().getId() == projectId)
                .filter(HourRegistration::isAccepted)
                .toList();
    }

    @Override // Adding to an array will never crash, but the interface needs it
    public HourRegistration createHourRegistration(CreateHourRegistrationRequest request, Project project) throws Exception {
        HourRegistration newHR = request.toDomainModel(nextId(), project);
        hourRegistrations.add(newHR);
        return newHR;
    }

    @Override
    public HourRegistration updateHourRegistration(int id, HourRegistration hourRegistration) throws Exception {
        hourRegistrations.removeIf(h -> h.getId() == id);
        hourRegistrations.add(hourRegistration);
        return hourRegistration;
    }

    @Override
    public Optional<HourRegistration> deleteHourRegistration(int id) throws Exception {
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
