package com.hva.ewa.team2.backend.data.hourregistration;

import com.hva.ewa.team2.backend.domain.models.hourregistration.CreateHourRegistrationRequest;
import com.hva.ewa.team2.backend.domain.models.hourregistration.HourRegistration;
import com.hva.ewa.team2.backend.domain.models.project.Project;

import java.util.List;
import java.util.Optional;

public interface HourRegistrationRepository {

    List<HourRegistration> fetchAllHourRegistrations();

    List<HourRegistration> fetchAllHourRegistrationsByUser(int userId);
    Optional<HourRegistration> fetchHourRegistrationById(int id);
    List<HourRegistration> fetchAllHourRegistrationByProject(int projectId);
    List<HourRegistration> fetchAllHourRegistrationByProjectUser(int projectId, int userId);

    List<HourRegistration> fetchAllAcceptedHoursForProject(int projectId);

    HourRegistration createHourRegistration(CreateHourRegistrationRequest request, Project project) throws Exception;

    HourRegistration updateHourRegistration(int id, HourRegistration hourRegistration) throws Exception;
    Optional<HourRegistration> deleteHourRegistration(int id) throws Exception;
}
