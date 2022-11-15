package com.hva.ewa.team2.backend.domain.usecases.HourRegistration;

import com.hva.ewa.team2.backend.domain.models.HourRegistration.CreateHourRegistrationRequest;
import com.hva.ewa.team2.backend.domain.models.HourRegistration.HourRegistration;

import java.util.List;
import java.util.Optional;

public interface HourRegistrationBusinessLogic {

    List<HourRegistration> handleFetchHourRegistrationsByUser(long userId);
    List<HourRegistration> handleFetchHourRegistrationsForProject(long projectId);
    Optional<HourRegistration> handleFetchHourRegistrationById(long id);
    Optional<HourRegistration> handleDeleteHourRegistrationById(long id);

    HourRegistration handleCreateHourRegistration(CreateHourRegistrationRequest request) throws Exception;
    HourRegistration handleUpdateHourRegistration(long id, HourRegistration hourRegistration) throws Exception;

    HourRegistration handleAcceptHourRegistration(long id);
    HourRegistration handleRejectHourRegistration(long id);
}
