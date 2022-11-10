package com.hva.ewa.team2.backend.data.HourRegistration;

import com.hva.ewa.team2.backend.domain.models.HourRegistration.HourRegistration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

public interface HourRegistrationRepository {

    List<HourRegistration> fetchAllHourRegistrations();

    List<HourRegistration> fetchAllHourRegistrationsByUser(long userId);
    Optional<HourRegistration> fetchHourRegistrationById(int id);
    List<HourRegistration> fetchAllHourRegistrationByProject(int projectId);

    List<HourRegistration> fetchAllAcceptedHoursForProject(int projectId);

    void createHourRegistration(HourRegistration hourRegistration) throws Exception;
    void updateHourRegistration(int id, HourRegistration hourRegistration) throws Exception;
    void deleteHourRegistration(int id) throws Exception;
}
