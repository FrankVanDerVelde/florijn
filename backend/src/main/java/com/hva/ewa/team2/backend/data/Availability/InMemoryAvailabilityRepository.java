package com.hva.ewa.team2.backend.data.Availability;


import com.hva.ewa.team2.backend.common.services.date.DateServiceLogic;
import com.hva.ewa.team2.backend.data.user.UserRepository;
import com.hva.ewa.team2.backend.domain.models.availability.Availability;
import com.hva.ewa.team2.backend.domain.models.availability.CreateAvailabilityRequest;
import com.hva.ewa.team2.backend.domain.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Primary
public class InMemoryAvailabilityRepository implements AvailabilityRepository {

    private List<Availability> availabilities;
    private final DateServiceLogic dateService;

    private final UserRepository userRepository;

    @Autowired
    InMemoryAvailabilityRepository(DateServiceLogic dateService, UserRepository userRepository) {
        this.dateService = dateService;
        this.userRepository = userRepository;
        this.availabilities = new ArrayList<>();
        setUp();
    }

    private void setUp() {

        User user1 = userRepository.getUserById(0);
        User user2 = userRepository.getUserById(1);

        this.availabilities.addAll(List.of(
                new Availability(
                        0,
                        user1,
                        LocalDate.now(),
                        dateService.currentDay(3, 0).toLocalTime(),
                        dateService.currentDay(8, 0).toLocalTime()
                ), new Availability(
                        1,
                        user2,
                        LocalDate.now().minusDays(7),
                        dateService.currentDay(6, 0).toLocalTime(),
                        dateService.currentDay(9, 0).toLocalTime()
                ), new Availability(
                        2,
                        user2,
                        LocalDate.now(),
                        dateService.currentDay(10, 0).toLocalTime(),
                        dateService.currentDay(12, 0).toLocalTime()
                ), new Availability(
                        3,
                        user2,
                        LocalDate.now(),
                        dateService.currentDay(14, 0).toLocalTime(),
                        dateService.currentDay(16, 0).toLocalTime()
                ), new Availability(
                        4,
                        user2,
                        LocalDate.now(),
                        dateService.currentDay(20, 0).toLocalTime(),
                        dateService.currentDay(22, 0).toLocalTime()
                )
        ));
    }


    @Override
    public List<Availability> fetchAllAvailabilityByUser(int userId, int weekNumber) {

        List<LocalDate> datesThisWeek = dateService.getAllDaysOfTheWeek(weekNumber);
        return availabilities.stream()
                .filter(a -> a.getUser().getId() == userId && datesThisWeek.contains(a.getDate()))
                .toList();
    }

    @Override
    public Optional<Availability> fetchAvailabilityById(int id) {
        return availabilities.stream()
                .filter(a -> a.getId() == id)
                .findFirst();
    }

    @Override
    public Availability createAvailability(CreateAvailabilityRequest request) throws Exception {
        Availability availability = new Availability(nextId(),
                userRepository.getUserById(request.getUserId()),
                request.getDate(),
                request.getFrom(),
                request.getTo());
        availabilities.add(availability);
        return availability;
    }

    @Override
    public Availability updateAvailability(int id, Availability availabilityRegistration) throws Exception {
        availabilities.removeIf(h -> h.getId() == id);
        availabilities.add(availabilityRegistration);
        return availabilityRegistration;
    }

    @Override
    public Optional<Availability> deleteAvailability(int id) throws Exception {
        Optional<Availability> availability = fetchAvailabilityById(id);

        if (availability.isPresent()) {
            availabilities.removeIf(h -> h.getId() == id);
            return availability;
        }

        return Optional.empty();
    }

    @Override
    public List<Availability> copyAvailabilityToNextWeek(int userId, int weekNumber) throws Exception {

        List<Availability> availabilitiesThisWeek = fetchAllAvailabilityByUser(userId, weekNumber);
        List<Availability> oldAvailabilitiesNextWeek = fetchAllAvailabilityByUser(userId, weekNumber + 1);
        List<Availability> newAvailabilitiesNextWeek = new ArrayList<>();

        oldAvailabilitiesNextWeek.forEach(a -> {
            availabilities.remove(a);
        });

        for (Availability availability : availabilitiesThisWeek) {
            Availability availabilityNextWeek = new Availability(
                    nextId(),
                    availability.getUser(),
                    availability.getDate().plusDays(7),
                    availability.getFrom(),
                    availability.getTo()
            );
            newAvailabilitiesNextWeek.add(availabilityNextWeek);
            availabilities.add(availabilityNextWeek);
        }

        return newAvailabilitiesNextWeek;
    }

    private int nextId() {
        Optional<Integer> highestId = availabilities.stream().map(Availability::getId).max(Integer::compareTo);
        return highestId.map(id -> id + 1).orElse(0);
    }
}


