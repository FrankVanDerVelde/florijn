package com.hva.ewa.team2.backend.repositories;

import com.hva.ewa.team2.backend.data.Availability.AvailabilityRepository;
import com.hva.ewa.team2.backend.data.Availability.InMemoryAvailabilityRepository;
import com.hva.ewa.team2.backend.domain.models.availability.Availability;
import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import com.hva.ewa.team2.backend.domain.models.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;


import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AvailabilityRepositoryTest {
    @Autowired
    AvailabilityRepository repository;

    private Availability availability;
    User user = new Specialist(1, "test@test.com", "test", null, "jan", "Bertens");

    @BeforeEach
    public void setUp() {
        availability = new Availability(user, LocalDate.of(2029, 1, 1), LocalTime.of(10, 0), LocalTime.of(11, 0));
    }

    @Test
    void checkIfRepositoryIsNotNull() {
        assertNotNull(repository);
    }

    @Test
    void testAddAvailability() {
        Availability save = repository.save(availability);
        System.out.println(save);
        assertTrue(repository.findById(save.getId()).isPresent());
    }

    @Test
    void testDeleteAvailability() {
        Availability save = repository.save(availability);
        repository.deleteById(save.getId());
        assertFalse(repository.findById(save.getId()).isPresent());
    }

    @Test
    void testGetAvailability() {
        Availability save = repository.save(availability);
        assertEquals(save.getId(), repository.findById(save.getId()).get().getId());
    }

    @Test
    void testUpdateAvailability() {
        Availability save = repository.save(availability);
        save.setFrom(LocalTime.of(12, 0));
        repository.save(save);
        assertEquals(save.getId(), repository.findById(save.getId()).get().getId());
    }

}
