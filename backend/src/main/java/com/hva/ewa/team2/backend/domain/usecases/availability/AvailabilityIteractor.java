package com.hva.ewa.team2.backend.domain.usecases.availability;

import com.hva.ewa.team2.backend.data.Availability.AvailabilityRepository;
import com.hva.ewa.team2.backend.domain.models.availability.Availability;
import com.hva.ewa.team2.backend.domain.models.availability.CreateAvailabilityRequest;
import com.hva.ewa.team2.backend.domain.models.availability.UpdateAvailabilityRequest;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Component
@Primary
public class AvailabilityIteractor implements AvailabilityBusinessLogic{

    private final AvailabilityRepository availabilityRepository;

    public AvailabilityIteractor(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }


    @Override
    public List<Availability> handleFetchAvailabilityByUser(int userId, Optional<Integer> weekNumber) {
        int week = LocalDate.now().get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());

        if (weekNumber.isPresent()) {
            week = getCorrectWeekNumber(weekNumber.get());
            return availabilityRepository.fetchAllAvailabilityByUser(userId, week);
        }
        return availabilityRepository.fetchAllAvailabilityByUser(userId, week);

    }

    @Override
    public Availability handleCreateAvailability(CreateAvailabilityRequest request) throws Exception {

        List<Availability> availabilityList = availabilityRepository.fetchAllAvailabilityByUser(
                request.getUserId(), request.getDate().get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()));

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

        return availabilityRepository.createAvailability(request);
    }

    @Override
    public Availability handleDeleteAvailability(int id) throws Exception {
        Optional<Availability> availability = availabilityRepository.fetchAvailabilityById(id);

        if (availability.isEmpty()) {
            throw new Exception("Could not find availability by given id: " + id);
        }

        if (checkIfTimeIsCorrect(availability.get().getFrom(), availability.get().getTo())) {
            throw new Exception("From time is after to time");
        }

        if (checkIfAvailabilityIsInThePast(availability.get().getDate())) {
            throw new Exception("Date is in the past");
        }

        return availabilityRepository.deleteAvailability(id);
    }

    @Override
    public Availability handleUpdateAvailability(int id, UpdateAvailabilityRequest request) throws Exception {
        Optional<Availability> availability = availabilityRepository.fetchAvailabilityById(id);
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

        return availabilityRepository.updateAvailability(id, updatedAvailability);
    }

    @Override
    public void handleDuplicateToNextWeek(int userId, int weekNumber) throws Exception {

    }

    @Override
    public Availability handleGetAvailabilityById(int id) throws Exception {
        if ((availabilityRepository.fetchAvailabilityById(id).isPresent())) {
            return availabilityRepository.fetchAvailabilityById(id).get();
        }
        throw new Exception("Could not find availability with id: " + id);
    }

    @Override
    public List<Availability> handleCopyAvailabilityToNextWeek(int userId, int weekNumber) throws Exception {
        int week = getCorrectWeekNumber(weekNumber);
        return availabilityRepository.copyAvailabilityToNextWeek(userId, week);
    }

    private int getCorrectWeekNumber(int weekNumber) {
        int week = LocalDate.now().get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
        if (week > 0) {
            week = week + weekNumber;
        } else if (week < 0) {
            week = week - weekNumber;
        } else {
            week = weekNumber;
        }
        return week;
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
