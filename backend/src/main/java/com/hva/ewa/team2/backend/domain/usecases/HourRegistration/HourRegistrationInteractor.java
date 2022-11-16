package com.hva.ewa.team2.backend.domain.usecases.HourRegistration;

import com.hva.ewa.team2.backend.domain.models.HourRegistration.HourRegistration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HourRegistrationInteractor implements HourRegistrationBusinessLogic {


    public List<HourRegistration> handleFetchHourRegistrationsByUser(long userId) {
        return new ArrayList<>();
    }
}
