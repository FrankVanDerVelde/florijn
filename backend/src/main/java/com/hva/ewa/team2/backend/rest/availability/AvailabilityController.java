package com.hva.ewa.team2.backend.rest.availability;

import com.hva.ewa.team2.backend.domain.models.availability.Availability;
import com.hva.ewa.team2.backend.domain.models.availability.UpdateAvailabilityRequest;
import com.hva.ewa.team2.backend.domain.usecases.availability.AvailabilityBusinessLogic;
import com.hva.ewa.team2.backend.domain.models.availability.CreateAvailabilityRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@ResponseBody
public class AvailabilityController {

    private final AvailabilityBusinessLogic interactor;

    @Autowired
    public AvailabilityController(AvailabilityBusinessLogic interactor) {
        this.interactor = interactor;
    }

    @GetMapping(path = "/users/{userId}/availability")
    public ResponseEntity<List<Availability>> getAvailabilityByUser(@PathVariable int userId, @RequestParam Optional<Integer> weekNumber, @RequestParam Optional<Integer> year) {
        List<Availability> availabilities = interactor.handleFetchAvailabilityByUser(userId, weekNumber, year);
        return ResponseEntity.ok(availabilities);
    }

    @PostMapping(path = "/users/{userId}/availability")
    public ResponseEntity<Availability> createAvailability(@PathVariable int userId, @RequestBody CreateAvailabilityRequestBody body) throws Exception {
        CreateAvailabilityRequest request = new CreateAvailabilityRequest(
                userId,
                body.getDate(),
                body.getFrom(),
                body.getTo()
        );
        System.out.println(request);

        try {
            Availability availability = interactor.handleCreateAvailability(request);
            return ResponseEntity.ok(availability);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(path = "availability/{id}/update")
    public ResponseEntity<Availability> updateAvailability(@PathVariable int id, @RequestBody UpdateAvailabilityRequestBody body) throws Exception {

        UpdateAvailabilityRequest request = new UpdateAvailabilityRequest(
                id,
                body.getDate(),
                body.getFrom(),
                body.getTo()
        );
        try {
            Availability availability = interactor.handleUpdateAvailability(id, request);
            return ResponseEntity.ok(availability);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(path = "availability/{id}/delete")
    public ResponseEntity<Availability> deleteAvailability(@PathVariable int id) throws Exception {
        Optional<Availability> availability = interactor.handleDeleteAvailability(id);
        return availability.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/availability/{id}")
    public ResponseEntity<Availability> getAvailabilityById(@PathVariable int id) throws Exception {
        Optional<Availability> availability = interactor.handleGetAvailabilityById(id);
        return availability.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("users/{userId}/availability/set-on-next-week")
    public ResponseEntity<List<Availability>> copyAvailabilityToNextWeek(@PathVariable int userId,
                                                                         @RequestParam Optional<Integer> weekNumber,
                                                                         @RequestParam Optional<Integer> year) throws Exception {
        List<Availability> availabilities = interactor.handleCopyAvailabilityToNextWeek(userId, weekNumber, year);
        return ResponseEntity.ok(availabilities);
    }


}
