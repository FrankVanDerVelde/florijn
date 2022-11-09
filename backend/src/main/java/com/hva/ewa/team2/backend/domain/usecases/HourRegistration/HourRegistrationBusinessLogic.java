package com.hva.ewa.team2.backend.domain.usecases.HourRegistration;

import com.hva.ewa.team2.backend.domain.models.HourRegistration.HourRegistration;

import java.util.List;

public interface HourRegistrationBusinessLogic {

    List<HourRegistration> handleFetchHourRegistrationsByUser(long userId);
}
