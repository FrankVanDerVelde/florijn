package com.hva.ewa.team2.backend.data.HourRegistration;

import com.hva.ewa.team2.backend.domain.models.HourRegistration.HourRegistration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
public class HourRegistrationRepositoryTests {

    private final HourRegistrationRepository hourRegistrationRepository;

    @Autowired
    public HourRegistrationRepositoryTests(HourRegistrationRepository hourRegistrationRepository) {
        this.hourRegistrationRepository = hourRegistrationRepository;
    }

    @Test
    void testShouldHaveHourRegistrations() {
        List<HourRegistration> hourRegistrations = hourRegistrationRepository.fetchAllHourRegistrations();
        Assert.isTrue(hourRegistrations.size() > 0, "Hour registrations should not be empty");
    }
}
