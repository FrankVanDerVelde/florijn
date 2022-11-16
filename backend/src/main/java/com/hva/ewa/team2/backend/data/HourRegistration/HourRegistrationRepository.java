package com.hva.ewa.team2.backend.data.HourRegistration;

import com.hva.ewa.team2.backend.domain.models.HourRegistration.CreateHourRegistrationRequest;
import com.hva.ewa.team2.backend.domain.models.HourRegistration.HourRegistration;

import java.util.List;
import java.util.Optional;

public interface HourRegistrationRepository {

    List<HourRegistration> fetchAllHourRegistrations();

    List<HourRegistration> fetchAllHourRegistrationsByUser(long userId);
    Optional<HourRegistration> fetchHourRegistrationById(long id);
    List<HourRegistration> fetchAllHourRegistrationByProject(long projectId);

    List<HourRegistration> fetchAllAcceptedHoursForProject(int projectId);

    HourRegistration createHourRegistration(CreateHourRegistrationRequest request) throws Exception;
    HourRegistration updateHourRegistration(long id, HourRegistration hourRegistration) throws Exception;
    Optional<HourRegistration> deleteHourRegistration(long id) throws Exception;
}
