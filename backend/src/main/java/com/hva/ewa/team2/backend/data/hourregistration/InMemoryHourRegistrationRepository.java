package com.hva.ewa.team2.backend.data.hourregistration;

import com.hva.ewa.team2.backend.common.Services.DateService.DateServiceLogic;
import com.hva.ewa.team2.backend.domain.models.hourregistration.CreateHourRegistrationRequest;
import com.hva.ewa.team2.backend.domain.models.hourregistration.HourRegistration;
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

    @Autowired
    public InMemoryHourRegistrationRepository(DateServiceLogic dateService) {
        this.dateService = dateService;
        this.hourRegistrations = new ArrayList<>();
        setup();
    }

    private void setup() {
        setupHourRegistrations();
    }

    private void setupHourRegistrations() {
        hourRegistrations = new ArrayList<>();
        hourRegistrations.addAll(List.of(
                new HourRegistration(
                        0,
                        0,
                        0,
                        dateService.currentDay(-3, 10, 0),
                        dateService.currentDay(-3, 12, 0),
                        "Gewerkt aan het project"
                        ),
                new HourRegistration(
                        1,
                        0,
                        0,
                        dateService.currentDay(-2, 8, 30),
                        dateService.currentDay(-2, 12, 0),
                        "Gewerkt aan het project"
                ),
                new HourRegistration(
                        2,
                        0,
                        0,
                        dateService.currentDay(-1, 12, 15),
                        dateService.currentDay(-1, 16, 0),
                        "Gewerkt aan het project"
                ),
                new HourRegistration(
                        3,
                        0,
                        0,
                        dateService.currentDay(8, 30),
                        dateService.currentDay(12, 0),
                        "Gewerkt aan het project"
                ),
                new HourRegistration(
                        4,
                        0,
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
    public List<HourRegistration> fetchAllHourRegistrationsByUser(long userId) {
        return hourRegistrations.stream()
                .filter(h -> h.getUserId() == userId)
                .toList();
    }

    @Override
    public Optional<HourRegistration> fetchHourRegistrationById(long id) {
        return hourRegistrations.stream()
                .filter(h -> h.getId() == id)
                .findFirst();
    }

    @Override
    public List<HourRegistration> fetchAllHourRegistrationByProject(long projectId) {
        return hourRegistrations.stream()
                .filter(h -> h.getProjectId() == projectId)
                .toList();
    }

    @Override
    public List<HourRegistration> fetchAllAcceptedHoursForProject(int projectId) {
        return hourRegistrations.stream()
                .filter(h -> h.getProjectId() == projectId)
                .filter(HourRegistration::isAccepted)
                .toList();
    }

    @Override // Adding to an array will never crash, but the interface needs it
    public HourRegistration createHourRegistration(CreateHourRegistrationRequest request) throws Exception {
        HourRegistration newHR = request.toDomainModel(nextId());
        hourRegistrations.add(newHR);
        return newHR;
    }

    @Override
    public HourRegistration updateHourRegistration(long id, HourRegistration hourRegistration) throws Exception {
        hourRegistrations.removeIf(h -> h.getId() == id);
        hourRegistrations.add(hourRegistration);
        return hourRegistration;
    }

    @Override
    public Optional<HourRegistration> deleteHourRegistration(long id) throws Exception {
        Optional<HourRegistration> hrToDelete = fetchHourRegistrationById(id);
        if (hrToDelete.isPresent()) {
            hourRegistrations.removeIf(h -> h.getId() == id);
            return hrToDelete;
        } else {
            return Optional.empty();
        }
    }

    private long nextId() {
        Optional<Long> highestId = hourRegistrations.stream().map(HourRegistration::getId).sorted().findFirst();
        return highestId.map(id -> id + 1).orElse(0L);
    }
}
