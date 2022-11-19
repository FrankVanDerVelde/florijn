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
    public ResponseEntity<List<HourRegistration>> getHourRegistrationsByUser(@PathVariable int userId) {
        List<HourRegistration> hourRegistrations = interactor.handleFetchHourRegistrationsByUser(userId);
        return ResponseEntity.ok(hourRegistrations);
    }

    @GetMapping(path= "/projects/{projectId}/hour-registrations/")
    public ResponseEntity<List<HourRegistration>> getHourRegistrationsByProject(@PathVariable int projectId) {
        List<HourRegistration> hourRegistrations = interactor.handleFetchHourRegistrationsForProject(projectId);
        return ResponseEntity.ok(hourRegistrations);
    }

    @GetMapping(path= "/hour-registrations/{id}")
    public ResponseEntity<HourRegistration> getHourRegistrationById(@PathVariable int id) {
        Optional<HourRegistration> hourRegistrations = interactor.handleFetchHourRegistrationById(id);
        return hourRegistrations.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(path= "/hour-registrations/{id}")
    public ResponseEntity<HourRegistration> deleteHourRegistrationById(@PathVariable int id) {
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
    public ResponseEntity<HourRegistration> updateHourRegistration(@PathVariable int id, @RequestBody HourRegistration hr) {
        try {
            HourRegistration createdHR = interactor.handleUpdateHourRegistration(id, hr);
            return ResponseEntity.ok(createdHR);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(path= "/hour-registrations/{id}/delete")
    public ResponseEntity<HourRegistration> deleteHourRegistration(@PathVariable int id) {
        Optional<HourRegistration> deletedHR = interactor.handleDeleteHourRegistrationById(id);
        return deletedHR.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(path= "/hour-registrations/{id}/accept")
    public ResponseEntity<HourRegistration> acceptHourRegistration(@PathVariable int id) {
        Optional<HourRegistration> acceptedHR = interactor.handleAcceptHourRegistration(id);
        return acceptedHR.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(path= "/hour-registrations/{id}/reject")
    public ResponseEntity<HourRegistration> rejectHourRegistration(@PathVariable int id) {
        Optional<HourRegistration> rejectedHR = interactor.handleRejectHourRegistration(id);
        return rejectedHR.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path= "projects/{projectId}/hour-registration/reports/total-accepted-hours")
    public ResponseEntity<Double> projectAcceptedHourRegistrations(@PathVariable int projectId) {
        return ResponseEntity.ok(400.0);
    }

    @GetMapping(path= "projects/{projectId}/hour-registration/reports/total-monthly-accepted-hours")
    public ResponseEntity<Double> monthlyAcceptedHourRegistrations(@PathVariable int projectId) {
        return ResponseEntity.ok(45.5);
    }

    @GetMapping(path= "projects/{projectId}/hour-registration/reports/total-weekly-accepted-hours")
    public ResponseEntity<Double> weeklyAcceptedHourRegistrations(@PathVariable int projectId) {
        return ResponseEntity.ok(12.5);
    }

    @GetMapping(path= "projects/{projectId}/hour-registration/reports/total-costs")
    public ResponseEntity<Double> totalProjectCostInEuros(@PathVariable int projectId) {
        return ResponseEntity.ok(1030.0);
    }
}