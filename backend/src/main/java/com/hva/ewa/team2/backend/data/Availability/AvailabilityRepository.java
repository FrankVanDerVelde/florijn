package com.hva.ewa.team2.backend.data.Availability;

import com.hva.ewa.team2.backend.domain.models.availability.Availability;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AvailabilityRepository extends CrudRepository<Availability, Integer> {

    @Override
    @Query("SELECT a FROM Availability a WHERE a.id = :integer")
    Optional<Availability> findById(Integer integer);

    @Query(value = "SELECT a.* FROM Availability a WHERE a.user_id = :userId AND WEEK(date) = :weekNumber AND YEAR(date) = :year", nativeQuery = true)
    List<Availability> fetchAllAvailabilityByUser(int userId, int weekNumber, int year);
}

