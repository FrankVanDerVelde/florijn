package com.hva.ewa.team2.backend.domain.usecases.hourregistration;

import com.hva.ewa.team2.backend.domain.models.hourregistration.CreateHourRegistrationRequest;
import com.hva.ewa.team2.backend.domain.models.hourregistration.HourRegistration;

import java.util.List;
import java.util.Optional;

public interface HourRegistrationBusinessLogic {

    List<HourRegistration> handleFetchHourRegistrationsByUser(int userId);
    List<HourRegistration> handleFetchHourRegistrationsForProject(int projectId);
    Optional<HourRegistration> handleFetchHourRegistrationById(int id);
    Optional<HourRegistration> handleDeleteHourRegistrationById(int id);

    HourRegistration handleCreateHourRegistration(CreateHourRegistrationRequest request) throws Exception;
    HourRegistration handleUpdateHourRegistration(int id, HourRegistration hourRegistration) throws Exception;

    Optional<HourRegistration> handleAcceptHourRegistration(int id);
    Optional<HourRegistration> handleRejectHourRegistration(int id);
}
