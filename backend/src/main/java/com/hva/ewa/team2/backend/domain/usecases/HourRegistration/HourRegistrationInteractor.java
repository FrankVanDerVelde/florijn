package com.hva.ewa.team2.backend.domain.usecases.HourRegistration;

import com.hva.ewa.team2.backend.data.HourRegistration.HourRegistrationRepository;
import com.hva.ewa.team2.backend.domain.models.HourRegistration.HourRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HourRegistrationInteractor implements HourRegistrationBusinessLogic {

    private final HourRegistrationRepository hourRegistrationRepository;

    @Autowired
    public HourRegistrationInteractor(HourRegistrationRepository hourRegistrationRepository) {
        this.hourRegistrationRepository = hourRegistrationRepository;
    }

    public List<HourRegistration> handleFetchHourRegistrationsByUser(long userId) {
        return hourRegistrationRepository.fetchAllHourRegistrationsByUser(userId);
    }

}
