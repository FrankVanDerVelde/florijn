package com.hva.ewa.team2.backend.rest;

import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import com.hva.ewa.team2.backend.domain.models.user.User;
import com.hva.ewa.team2.backend.rest.availability.CreateAvailabilityRequestBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;

import static java.time.LocalDate.of;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AvailabilityRestTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getAvailabilityForUser() {
        ResponseEntity<String> response = restTemplate.getForEntity("/users/1/availability", String.class);
        assertEquals(200, response.getStatusCodeValue());

    }
    @Test
    void addNewAvailability() {
        //create request for new availability
        CreateAvailabilityRequestBody request = new CreateAvailabilityRequestBody( of(2025, 1, 1), LocalTime.of(10, 0), LocalTime.of(11, 0));

        //send request
        ResponseEntity<String> response = restTemplate.postForEntity("/users/1/availability", request, String.class);
        assertEquals(200, response.getStatusCodeValue());

        //check if request is added
        ResponseEntity<String> response2 = restTemplate.getForEntity("/users/1/availability", String.class);
        assertEquals(200, response2.getStatusCodeValue());
    }

    @Test
    void deleteAvailability() {
        //create request for new availability
        CreateAvailabilityRequestBody request = new CreateAvailabilityRequestBody( of(2025, 1, 1), LocalTime.of(10, 0), LocalTime.of(11, 0));

        //send request
        ResponseEntity<String> response = restTemplate.postForEntity("/users/1/availability", request, String.class);
        assertEquals(200, response.getStatusCodeValue());

        //check if request is added
        ResponseEntity<String> response2 = restTemplate.getForEntity("/users/1/availability", String.class);
        assertEquals(200, response2.getStatusCodeValue());

        //delete request
        restTemplate.delete("/availability/144/delete");
        ResponseEntity<String> response3 = restTemplate.getForEntity("/availability/144", String.class);
        assertEquals(404, response3.getStatusCodeValue());
    }

    @Test
    void updateAvailability() {
        //create request for new availability
        CreateAvailabilityRequestBody request = new CreateAvailabilityRequestBody( of(2025, 1, 1), LocalTime.of(10, 0), LocalTime.of(11, 0));

        //send request
        ResponseEntity<String> response = restTemplate.postForEntity("/users/1/availability", request, String.class);
        assertEquals(200, response.getStatusCodeValue());

        //check if request is added
        ResponseEntity<String> response2 = restTemplate.getForEntity("/users/1/availability", String.class);
        assertEquals(200, response2.getStatusCodeValue());

        //update request
        CreateAvailabilityRequestBody request2 = new CreateAvailabilityRequestBody( of(2025, 1, 1), LocalTime.of(10, 0), LocalTime.of(12, 0));
        restTemplate.put("/availability/150/update", request2);
        ResponseEntity<String> response3 = restTemplate.getForEntity("/availability/150", String.class);
        assertEquals(200, response3.getStatusCodeValue());
    }

    @Test
    void availabilityCanNotBeInThePast() {
        //create request for new availability
        CreateAvailabilityRequestBody request = new CreateAvailabilityRequestBody( of(2020, 1, 1), LocalTime.of(10, 0), LocalTime.of(11, 0));

        //send request
        ResponseEntity<String> response = restTemplate.postForEntity("/users/1/availability", request, String.class);
        assertEquals(400, response.getStatusCodeValue());
    }


}
