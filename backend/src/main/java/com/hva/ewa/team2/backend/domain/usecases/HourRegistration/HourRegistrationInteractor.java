package com.hva.ewa.team2.backend.domain.usecases.HourRegistration;

import com.hva.ewa.team2.backend.data.HourRegistration.HourRegistrationRepository;
import com.hva.ewa.team2.backend.domain.models.HourRegistration.CreateHourRegistrationRequest;
import com.hva.ewa.team2.backend.domain.models.HourRegistration.HourRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Primary
public class HourRegistrationInteractor implements HourRegistrationBusinessLogic {

    private final HourRegistrationRepository hourRegistrationRepository;

    @Autowired
    public HourRegistrationInteractor(HourRegistrationRepository hourRegistrationRepository) {
        this.hourRegistrationRepository = hourRegistrationRepository;
    }

    public List<HourRegistration> handleFetchHourRegistrationsByUser(long userId) {
        return hourRegistrationRepository.fetchAllHourRegistrationsByUser(userId);
    }

    @Override
    public List<HourRegistration> handleFetchHourRegistrationsForProject(long projectId) {
        return hourRegistrationRepository.fetchAllHourRegistrationByProject(projectId);
    }

    @Override
    public Optional<HourRegistration> handleFetchHourRegistrationById(long id) {
        return hourRegistrationRepository.fetchHourRegistrationById(id);
    }

    @Override
    public Optional<HourRegistration> handleDeleteHourRegistrationById(long id) {
        try {
            return hourRegistrationRepository.deleteHourRegistration(id);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public HourRegistration handleCreateHourRegistration(CreateHourRegistrationRequest request) throws Exception {
        return hourRegistrationRepository.createHourRegistration(request);
    }

    @Override
    public HourRegistration handleUpdateHourRegistration(long id, HourRegistration hourRegistration) throws Exception {
        return hourRegistrationRepository.updateHourRegistration(id, hourRegistration);
    }

    @Override
    public Optional<HourRegistration> handleAcceptHourRegistration(long id) {
        // TODO: Check user permission, only admins or clients can accept.
        Optional<HourRegistration> hr = hourRegistrationRepository.fetchHourRegistrationById(id);
        if (hr.isPresent()) {
            try {
                updateStatusForHourRegistration(hr.get(), true);
            } catch (Exception e) {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<HourRegistration> handleRejectHourRegistration(long id) {
        // TODO: Check user permission, only admins or clients can reject.
        Optional<HourRegistration> hr = hourRegistrationRepository.fetchHourRegistrationById(id);
        if (hr.isPresent()) {
            try {
                updateStatusForHourRegistration(hr.get(), false);
            } catch (Exception e) {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }

    private void updateStatusForHourRegistration(HourRegistration hr, boolean isAccepted) throws Exception {
        hr.setAccepted(isAccepted);
        hourRegistrationRepository.updateHourRegistration(hr.getId(), hr);
    }
}
