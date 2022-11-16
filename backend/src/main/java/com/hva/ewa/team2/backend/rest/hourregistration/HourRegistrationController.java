package com.hva.ewa.team2.backend.rest.hourregistration;

import com.hva.ewa.team2.backend.domain.models.hourregistration.CreateHourRegistrationRequest;
import com.hva.ewa.team2.backend.domain.models.hourregistration.HourRegistration;
import com.hva.ewa.team2.backend.domain.usecases.hourregistration.HourRegistrationBusinessLogic;
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

    @PostMapping(path= "/users/{userId}/hour-registrations/")
    public ResponseEntity<HourRegistration> createHourRegistration(
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

    @PutMapping(path= "/hour-registrations/{id}/update")
    public ResponseEntity<HourRegistration> updateHourRegistration(@PathVariable long id, @RequestBody HourRegistration hr) {
        try {
            HourRegistration createdHR = interactor.handleUpdateHourRegistration(id, hr);
            return ResponseEntity.ok(createdHR);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(path= "/hour-registrations/{id}/delete")
    public ResponseEntity<HourRegistration> deleteHourRegistration(@PathVariable long id) {
        Optional<HourRegistration> deletedHR = interactor.handleDeleteHourRegistrationById(id);
        return deletedHR.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(path= "/hour-registrations/{id}/accept")
    public ResponseEntity<HourRegistration> acceptHourRegistration(@PathVariable long id) {
        Optional<HourRegistration> acceptedHR = interactor.handleAcceptHourRegistration(id);
        return acceptedHR.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(path= "/hour-registrations/{id}/reject")
    public ResponseEntity<HourRegistration> rejectHourRegistration(@PathVariable long id) {
        Optional<HourRegistration> rejectedHR = interactor.handleRejectHourRegistration(id);
        return rejectedHR.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
