package com.hva.ewa.team2.backend.models;

import com.hva.ewa.team2.backend.domain.models.availability.Availability;
import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import com.hva.ewa.team2.backend.domain.models.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class AvailabilityTest {

    Availability availability1, availability2;
    User user1, user2;
    @BeforeEach
    void setUp() {
        user1 = new Specialist(1, "test@test.com", "test", null, "Withney", "Katrin");
        user2 = new Specialist(2, "tets2@test.com", "test2", null, "Tom", "Housten");

        this.availability1 = new Availability(user1, LocalDate.of(2021, 1, 1), LocalTime.of(10, 0), LocalTime.of(11, 0));
        this.availability2 = new Availability(user2, LocalDate.of(2021, 1, 1), LocalTime.of(14, 0), LocalTime.of(19, 0));
    }

    @Test
    void testUsersAreCorrect() {
        assertEquals(availability1.getUser(), user1);
        assertEquals(availability2.getUser(), user2);
    }

    @Test
    void testDatesAreCorrect() {
        assertEquals(availability1.getDate(), LocalDate.of(2021, 1, 1));
        assertEquals(availability2.getDate(), LocalDate.of(2021, 1, 1));
    }

    @Test
    void testStartTimesAreCorrect() {
        assertEquals(availability1.getFrom(), LocalTime.of(10, 0));
        assertEquals(availability2.getFrom(), LocalTime.of(14, 0));
    }

    @Test
    void checkToStringIsCorrect() {
        assertEquals(availability1.toString(), "Availability{" +
                "id=" + availability1.getId() +
                ", user=" + availability1.getUser() +
                ", date=" + availability1.getDate() +
                ", from=" + availability1.getFrom() +
                ", to=" + availability1.getTo() +
                '}');
    }


}
