package com.hva.ewa.team2.backend.domain.usecases.hourregistration;

import com.hva.ewa.team2.backend.domain.models.hourregistration.CreateHourRegistrationRequest;
import com.hva.ewa.team2.backend.domain.models.hourregistration.HourRegistration;

import java.util.List;
import java.util.Optional;

public interface HourRegistrationBusinessLogic {

    List<HourRegistration> handleFetchHourRegistrationsByUser(long userId);
    List<HourRegistration> handleFetchHourRegistrationsForProject(long projectId);
    Optional<HourRegistration> handleFetchHourRegistrationById(long id);
    Optional<HourRegistration> handleDeleteHourRegistrationById(long id);

    HourRegistration handleCreateHourRegistration(CreateHourRegistrationRequest request) throws Exception;
    HourRegistration handleUpdateHourRegistration(long id, HourRegistration hourRegistration) throws Exception;

    Optional<HourRegistration> handleAcceptHourRegistration(long id);
    Optional<HourRegistration> handleRejectHourRegistration(long id);
}
