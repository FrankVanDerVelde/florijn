package com.hva.ewa.team2.backend.rest.HourRegistration;

import com.hva.ewa.team2.backend.domain.models.HourRegistration.HourRegistration;
import com.hva.ewa.team2.backend.domain.usecases.HourRegistration.HourRegistrationBusinessLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@ResponseBody
public class HourRegistrationController {

    private final HourRegistrationBusinessLogic interactor;

    @Autowired
    public HourRegistrationController(HourRegistrationBusinessLogic interactor) {
        this.interactor = interactor;
    }

    @GetMapping(path= "/users/{userId}/hour-registrations/")
    public ResponseEntity<List<HourRegistration>> getCabinById(@PathVariable long userId) {
        List<HourRegistration> hourRegistrations = interactor.handleFetchHourRegistrationsByUser(userId);
        return ResponseEntity.ok(hourRegistrations);
    }


}
