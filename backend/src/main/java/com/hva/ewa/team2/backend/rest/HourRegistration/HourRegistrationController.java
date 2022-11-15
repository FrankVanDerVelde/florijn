package com.hva.ewa.team2.backend.rest.HourRegistration;

import com.hva.ewa.team2.backend.domain.models.HourRegistration.CreateHourRegistrationRequest;
import com.hva.ewa.team2.backend.domain.models.HourRegistration.HourRegistration;
import com.hva.ewa.team2.backend.domain.usecases.HourRegistration.HourRegistrationBusinessLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@ResponseBody
public class HourRegistrationController {

    private final HourRegistrationBusinessLogic interactor;

    @Autowired
    public HourRegistrationController(HourRegistrationBusinessLogic interactor) {
        this.interactor = interactor;
    }

    @GetMapping(path= "/users/{userId}/hour-registrations/")
    public ResponseEntity<List<HourRegistration>> getHourRegistrationsByUser(@PathVariable long userId) {
        List<HourRegistration> hourRegistrations = interactor.handleFetchHourRegistrationsByUser(userId);
        return ResponseEntity.ok(hourRegistrations);
    }

    @GetMapping(path= "/projects/{projectId}/hour-registrations/")
    public ResponseEntity<List<HourRegistration>> getHourRegistrationsByProject(@PathVariable long projectId) {
        List<HourRegistration> hourRegistrations = interactor.handleFetchHourRegistrationsForProject(projectId);
        return ResponseEntity.ok(hourRegistrations);
    }

    @GetMapping(path= "/hour-registrations/{id}")
    public ResponseEntity<HourRegistration> getHourRegistrationById(@PathVariable long id) {
        Optional<HourRegistration> hourRegistrations = interactor.handleFetchHourRegistrationById(id);
        return hourRegistrations.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(path= "/hour-registrations/{id}")
    public ResponseEntity<HourRegistration> deleteHourRegistrationById(@PathVariable long id) {
        Optional<HourRegistration> hourRegistrations = interactor.handleDeleteHourRegistrationById(id);
        return hourRegistrations.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(path= "/users/{userId}/hour-registrations/")
    public ResponseEntity<HourRegistration> deleteHourRegistrationById(
            @PathVariable long userId,
            @RequestBody CreateHourRegistrationRequestBody body)
    {
        CreateHourRegistrationRequest request = new CreateHourRegistrationRequest(
                body.getProjectId(),
                userId,
                body.getFrom(),
                body.getTo(),
                body.getDescription()
        );

        try {
            HourRegistration createdHR = interactor.handleCreateHourRegistration(request);
            return ResponseEntity.ok(createdHR);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(path= "/hour-registrations/{id}")
    public ResponseEntity<HourRegistration> updateHourRegistration(@PathVariable long id, @RequestBody HourRegistration hr) {
        try {
            HourRegistration createdHR = interactor.handleUpdateHourRegistration(id, hr);
            return ResponseEntity.ok(createdHR);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(path= "/hour-registrations/{id}")
    public ResponseEntity<HourRegistration> deleteHourRegistration(@PathVariable long id, @RequestBody HourRegistration hr) {
        try {
            HourRegistration createdHR = interactor.handleUpdateHourRegistration(id, hr);
            return ResponseEntity.ok(createdHR);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(path= "/hour-registrations/{id}/accept")
    public ResponseEntity<HourRegistration> acceptHourRegistration(@PathVariable long id) {
        HourRegistration acceptedHR = interactor.handleAcceptHourRegistration(id);
        return ResponseEntity.ok(acceptedHR);
    }

    @PostMapping(path= "/hour-registrations/{id}/reject")
    public ResponseEntity<HourRegistration> rejectHourRegistration(@PathVariable long id) {
        HourRegistration acceptedHR = interactor.handleRejectHourRegistration(id);
        return ResponseEntity.ok(acceptedHR);
    }
}
