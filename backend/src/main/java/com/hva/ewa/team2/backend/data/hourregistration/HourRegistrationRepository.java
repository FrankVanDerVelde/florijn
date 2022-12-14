package com.hva.ewa.team2.backend.data.hourregistration;

import com.hva.ewa.team2.backend.domain.models.hourregistration.HourRegistration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface HourRegistrationRepository extends CrudRepository<HourRegistration, Integer> {

    @Query(value = "SELECT * FROM hour_registration h WHERE h.project_id = :projectId ORDER BY `status` IS NULL DESC, `from` DESC", nativeQuery = true)
    List<HourRegistration> findAllByProject(int projectId);

    @Query(value = "SELECT h.* FROM hour_registration h INNER JOIN project_participant p ON (h.user_id=p.user_id) WHERE h.user_id = :userId ORDER BY `status` IS NULL DESC, `from` DESC", nativeQuery = true)
    List<HourRegistration> findAllByUser(int userId);

    @Query(value = "SELECT DISTINCT h.* FROM hour_registration h INNER JOIN project_participant p ON (h.user_id=p.user_id) WHERE h.project_id = :projectId AND h.user_id = :userId ORDER BY `status` IS NULL DESC, `from` DESC", nativeQuery = true)
    List<HourRegistration> findAllByProjectParticipant(int projectId, int userId);

    @Query(value = "SELECT sum((h.to - h.from) / 10000) FROM HourRegistration h WHERE h.project.id = :projectId AND h.status <> 'REJECTED'")
    Double getTotalHoursForProject(int projectId);

    @Query(value = "SELECT sum((h.to - h.from) / 10000) FROM HourRegistration h WHERE h.project.id = :projectId AND h.projectParticipant.specialist.id = :userId AND h.status <> 'REJECTED'")
    Double getTotalHoursForProject(int projectId, int userId);

    @Query(value = "SELECT sum((h.to - h.from) / 10000) FROM HourRegistration h WHERE h.project.id = :projectId AND h.status <> 'REJECTED' AND h.from >= :from AND h.from <= :to")
    Double getTotalHoursForProjectBetween(int projectId, LocalDateTime from, LocalDateTime to);

    @Query(value = "SELECT sum((h.to - h.from) / 10000) FROM HourRegistration h WHERE h.project.id = :projectId AND h.projectParticipant.specialist.id = :userId AND h.status <> 'REJECTED' AND h.from >= :from AND h.from <= :to")
    Double getTotalHoursForProjectBetween(int projectId, int userId, LocalDateTime from, LocalDateTime to);

    @Query(value = "SELECT sum(((h.to - h.from) / 10000) * h.projectParticipant.hourlyRate) FROM HourRegistration h WHERE h.project.id = :projectId AND h.status <> 'REJECTED'")
    Double getTotalCostsForProject(int projectId);

    @Query(value = "SELECT sum(((h.to - h.from) / 10000) * h.projectParticipant.hourlyRate) FROM HourRegistration h WHERE h.project.id = :projectId AND h.projectParticipant.specialist.id = :userId AND h.status <> 'REJECTED'")
    Double getTotalRevenueForProject(int projectId, int userId);

}
