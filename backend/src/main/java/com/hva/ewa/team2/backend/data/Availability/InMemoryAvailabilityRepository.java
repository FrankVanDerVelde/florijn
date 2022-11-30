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

        User user1 = userRepository.getUserById(1);
        User user2 = userRepository.getUserById(2);

        this.availabilities.addAll(List.of(
                new Availability(
                        0,
                        user1,
                        LocalDate.of(2022, 3, 4),
                        dateService.currentDay(3, 0).toLocalTime(),
                        dateService.currentDay(4, 0).toLocalTime()
                ), new Availability(
                        1,
                        user2,
                        LocalDate.of(2022, 3, 4),
                        dateService.currentDay(6, 0).toLocalTime(),
                        dateService.currentDay(9, 0).toLocalTime()
                )
        ));

    }


    @Override
    public List<Availability> fetchAllAvailabilityByUser(int userId, int weekNumber) {
        return availabilities.stream()
                .filter(a -> a.getUser().getId() == userId)
                //TODO filter weeknumber
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
    public Availability deleteAvailability(int id) throws Exception {
        Availability availability = availabilities.get(id);
        availabilities.remove(availability);
        return availability;
    }

    private int nextId() {
        Optional<Integer> highestId = availabilities.stream().map(Availability::getId).max(Integer::compareTo);
        return highestId.map(id -> id + 1).orElse(0);
    }
}


