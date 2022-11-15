package com.hva.ewa.team2.backend.data.HourRegistration;

import com.hva.ewa.team2.backend.common.Services.DateService.DateServiceLogic;
import com.hva.ewa.team2.backend.domain.models.HourRegistration.HourRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjuster;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
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
                        0,
                        0,
                        0,
                        dateService.currentDay(-2, 8, 30),
                        dateService.currentDay(-2, 12, 0),
                        "Gewerkt aan het project"
                ),
                new HourRegistration(
                        0,
                        0,
                        0,
                        dateService.currentDay(-1, 12, 15),
                        dateService.currentDay(-1, 16, 0),
                        "Gewerkt aan het project"
                ),
                new HourRegistration(
                        0,
                        0,
                        0,
                        dateService.currentDay(8, 30),
                        dateService.currentDay(12, 0),
                        "Gewerkt aan het project"
                ),
                new HourRegistration(
                        0,
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
    public Optional<HourRegistration> fetchHourRegistrationById(int id) {
        return hourRegistrations.stream()
                .filter(h -> h.getId() == id)
                .findFirst();
    }

    @Override
    public List<HourRegistration> fetchAllHourRegistrationByProject(int projectId) {
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
    public void createHourRegistration(HourRegistration hourRegistration) throws Exception {
        hourRegistrations.add(hourRegistration);
    }

    @Override
    public void updateHourRegistration(int id, HourRegistration hourRegistration) throws Exception {
        hourRegistrations.removeIf(h -> h.getId() == id);
        hourRegistrations.add(hourRegistration);
    }

    @Override
    public void deleteHourRegistration(int id) throws Exception {
        hourRegistrations.removeIf(h -> h.getId() == id);
    }
}
