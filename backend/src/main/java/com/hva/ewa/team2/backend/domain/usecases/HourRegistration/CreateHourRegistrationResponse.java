package com.hva.ewa.team2.backend.domain.usecases.HourRegistration;

import com.hva.ewa.team2.backend.domain.models.HourRegistration.HourRegistration;
import lombok.Getter;

import java.util.Optional;

public class CreateHourRegistrationResponse {

    @Getter
    private Optional<HourRegistration> createdHourRegistration;
    @Getter
    private Optional<String> errorMessage;

    public CreateHourRegistrationResponse(Optional<HourRegistration> createdHourRegistration, Optional<String> errorMessage) {
        this.createdHourRegistration = createdHourRegistration;
        this.errorMessage = errorMessage;
    }
}
