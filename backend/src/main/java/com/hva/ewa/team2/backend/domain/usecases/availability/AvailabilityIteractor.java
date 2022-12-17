package com.hva.ewa.team2.backend.domain.usecases.availability;

import com.hva.ewa.team2.backend.data.Availability.AvailabilityRepository;
import com.hva.ewa.team2.backend.data.user.UserRepository;
import com.hva.ewa.team2.backend.domain.models.availability.Availability;
import com.hva.ewa.team2.backend.domain.models.availability.CreateAvailabilityRequest;
import com.hva.ewa.team2.backend.domain.models.availability.UpdateAvailabilityRequest;
import com.hva.ewa.team2.backend.domain.models.user.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.*;

@Component
@Primary
public class AvailabilityIteractor implements AvailabilityBusinessLogic {

    private final AvailabilityRepository availabilityRepository;
    private final UserRepository userRepository;

    public AvailabilityIteractor(AvailabilityRepository availabilityRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.availabilityRepository = availabilityRepository;
    }


    @Override
    public List<Availability> handleFetchAvailabilityByUser(int userId, Optional<Integer> weekNumber, Optional<Integer> year) {
        int selectedWeek = LocalDate.now().get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
        int selectedYear = LocalDate.now().getYear();

        if (weekNumber.isPresent()) {
            selectedWeek = weekNumber.get();
        }

        if (year.isPresent()) {
            selectedYear = year.get();
        }
        System.out.println("Week: " + selectedWeek + " Year: " + selectedYear);
        return availabilityRepository.fetchAllAvailabilityByUser(userId, selectedWeek, selectedYear);
    }

    @Override
    public Availability handleCreateAvailability(CreateAvailabilityRequest request) throws Exception {

        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new Exception("User not found"));

        List<Availability> availabilityList = availabilityRepository.fetchAllAvailabilityByUser(
                request.getUserId(),
                request.getDate().get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()),
                request.getDate().getYear()
        );

        DateOverlapRequest dateOverlapRequest = new DateOverlapRequest(request.getDate(), request.getFrom(), request.getTo());

        for (Availability availability : availabilityList) {
            if (isOverlapping(availability, dateOverlapRequest)) {
                throw new Exception("Availability is overlapping");
            }
        }

        if (checkIfTimeIsCorrect(request.getFrom(), request.getTo())) {
            throw new Exception("From time is after to time");
        }

        if (checkIfAvailabilityIsInThePast(request.getDate())) {
            throw new Exception("Date is in the past");
        }

        Availability newAvailability = new Availability(
                user,
                request.getDate(),
                request.getFrom(),
                request.getTo()
        );

        return availabilityRepository.save(newAvailability);
    }

    @Override
    public Optional<Availability> handleDeleteAvailability(int id) throws Exception {
        Optional<Availability> availability = availabilityRepository.findById(id);

        if (availability.isEmpty()) {
            throw new Exception("Could not find availability by given id: " + id);
        }

        if (checkIfTimeIsCorrect(availability.get().getFrom(), availability.get().getTo())) {
            throw new Exception("From time is after to time");
        }

        if (checkIfAvailabilityIsInThePast(availability.get().getDate())) {
            throw new Exception("Date is in the past");
        }
        availabilityRepository.deleteById(id);

        return availability;
    }

    @Override
    public Availability handleUpdateAvailability(int id, UpdateAvailabilityRequest request) throws Exception {
        Optional<Availability> availability = availabilityRepository.findById(id);
        DateOverlapRequest dateOverlapRequest = new DateOverlapRequest(request.getDate(), request.getFrom(), request.getTo());

        if (availability.isEmpty())
            throw new Exception("Could not find availability by given id: " + id);

        if (isOverlapping(availability.get(), dateOverlapRequest)) {
            throw new Exception("Availability is overlapping");
        }

        if (checkIfTimeIsCorrect(request.getFrom(), request.getTo())) {
            throw new Exception("From time is after to time");
        }

        if (checkIfAvailabilityIsInThePast(request.getDate())) {
            throw new Exception("Date is in the past");
        }

        Availability updatedAvailability = availability.get();
        updatedAvailability.setDate(request.getDate());
        updatedAvailability.setFrom(request.getFrom());
        updatedAvailability.setTo(request.getTo());

        return availabilityRepository.save(updatedAvailability);
    }


    @Override
    public Optional<Availability> handleGetAvailabilityById(int id) throws Exception {
        return availabilityRepository.findById(id);

    }

    @Override
    public List<Availability> handleCopyAvailabilityToNextWeek(int userId, Optional<Integer> weekNumber, Optional<Integer> year) throws Exception {
        List<Availability> availabilitiesThisWeek = handleFetchAvailabilityByUser(userId, weekNumber, year);

        Calendar calendar = Calendar.getInstance();
        calendar.setWeekDate(
                year.orElse(LocalDate.now().getYear()),
                weekNumber.orElse(LocalDate.now().get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear())),
                Calendar.MONDAY);
        calendar.add(Calendar.WEEK_OF_YEAR, 1);

        Optional<Integer> nextWeek = Optional.of(calendar.get(Calendar.WEEK_OF_YEAR));
        Optional<Integer> nextYear = Optional.of(calendar.get(Calendar.YEAR));

        List<Availability> oldAvailabilitiesNextWeek = handleFetchAvailabilityByUser(userId, nextWeek, nextYear);
        oldAvailabilitiesNextWeek.forEach(a -> {
            availabilityRepository.deleteById(a.getId());
        });

        List<Availability> newAvailabilities = new ArrayList<>();


        for (Availability availability : availabilitiesThisWeek) {
            Availability availabilityNextWeek = new Availability(
                    availability.getUser(),
                    availability.getDate().plusDays(7),
                    availability.getFrom(),
                    availability.getTo()
            );
            newAvailabilities.add(availabilityNextWeek);
            availabilityRepository.save(availabilityNextWeek);
        }
        return newAvailabilities;
    }


    private boolean isOverlapping(Availability availability, DateOverlapRequest request) {
        return availability.getFrom().isBefore(request.getFrom()) && availability.getTo().isAfter(request.getTo()) ||
                availability.getFrom().isAfter(request.getFrom()) && availability.getTo().isBefore(request.getTo()) ||
                availability.getFrom().isBefore(request.getFrom()) && availability.getTo().isAfter(request.getFrom()) ||
                availability.getFrom().isBefore(request.getTo()) && availability.getTo().isAfter(request.getTo()) ||
                availability.getFrom().equals(request.getFrom()) && availability.getTo().equals(request.getTo());
    }

    private boolean checkIfAvailabilityIsInThePast(LocalDate date) {
        return date.isBefore(LocalDate.now());
    }

    private boolean checkIfTimeIsCorrect(LocalTime from, LocalTime to) {
        return from.isAfter(to);
    }
}
