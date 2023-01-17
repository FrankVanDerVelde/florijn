package com.hva.ewa.team2.backend.repositories;

import com.hva.ewa.team2.backend.data.Availability.AvailabilityRepository;
import com.hva.ewa.team2.backend.domain.models.availability.Availability;
import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import com.hva.ewa.team2.backend.domain.models.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;


import javax.persistence.Entity;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@RunWith(SpringRunner.class)
public class AvailabilityRepositoryTest {

    @Autowired
    AvailabilityRepository repository;

    @Test
    void testRepoCrud() {
        User user1 = new Specialist(1, "test@test.com", "test", null, "Withney", "Katrin");
        Availability availability = new Availability(user1, null, null, null);
        Availability savedAvailability = repository.save(availability);

        assertNotNull(savedAvailability);
    }



}
