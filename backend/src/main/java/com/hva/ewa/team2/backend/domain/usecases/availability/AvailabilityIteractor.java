package com.hva.ewa.team2.backend.domain.usecases.availability;

import com.hva.ewa.team2.backend.data.Availability.AvailabilityRepository;
import com.hva.ewa.team2.backend.domain.models.availability.Availability;
import com.hva.ewa.team2.backend.domain.models.availability.CreateAvailabilityRequest;
import com.hva.ewa.team2.backend.domain.models.availability.UpdateAvailabilityRequest;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
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
        if (weekNumber.isPresent()) {
            int week = weekNumber.get();
            return availabilityRepository.fetchAllAvailabilityByUser(userId, week);
        }

        //TODO make sure weeknumbers work
        return availabilityRepository.fetchAllAvailabilityByUser(userId, 0);

    }

    @Override
    public Availability handleCreateAvailability(CreateAvailabilityRequest request) throws Exception {
        return availabilityRepository.createAvailability(request);
    }

    @Override
    public Availability handleDeleteAvailability(int id) throws Exception {
        Optional<Availability> availability = availabilityRepository.fetchAvailabilityById(id);

        if (availability.isEmpty()) {
            throw new Exception("Could not find availability by given id: " + id);
        }
        return availabilityRepository.deleteAvailability(id);


    }

    @Override
    public Availability handleUpdateAvailability(int id, UpdateAvailabilityRequest request) throws Exception {
        Optional<Availability> availability = availabilityRepository.fetchAvailabilityById(id);

        if (availability.isEmpty())
            throw new Exception("Could not find availability by given id: " + id);

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
}
