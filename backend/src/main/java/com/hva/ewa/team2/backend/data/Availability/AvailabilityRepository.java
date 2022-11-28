package com.hva.ewa.team2.backend.data.Availability;

import com.hva.ewa.team2.backend.domain.models.availability.Availability;
import com.hva.ewa.team2.backend.domain.models.availability.CreateAvailabilityRequest;


import java.util.List;
import java.util.Optional;

public interface AvailabilityRepository {

    List<Availability> fetchAllAvailabilityByUser(int userId, int weekNumber);
    Optional<Availability> fetchAvailabilityById(int id);

    Availability createAvailability(CreateAvailabilityRequest request) throws Exception;

    Availability updateAvailability(int id, Availability availabilityRegistration) throws Exception;

    Availability deleteAvailability(int id) throws Exception;



}
