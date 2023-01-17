package com.hva.ewa.team2.backend.repositories;

import com.hva.ewa.team2.backend.data.Availability.AvailabilityRepository;
import com.hva.ewa.team2.backend.domain.models.availability.Availability;
import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import com.hva.ewa.team2.backend.domain.models.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;
import javax.persistence.Entity;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
        classes = { Repository.class, Entity.class }))
public class AvailabilityRepositoryTest {

    @Autowired
    AvailabilityRepository repository;

    User user1 = new Specialist(1, "test@test.com", "test", null, "Withney", "Katrin");
    User user2 = new Specialist(2, "tets2@test.com", "test2", null, "Tom", "Housten");

    @Test
    void testRepoCrud() {
        assertNotNull(user1);
        repository.save(new Availability(user1, null, null, null));
    }



}
