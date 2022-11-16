package com.hva.ewa.team2.backend.data.HourRegistration;

import com.hva.ewa.team2.backend.common.Services.DateService.DateServiceLogic;
import com.hva.ewa.team2.backend.domain.models.HourRegistration.CreateHourRegistrationRequest;
import com.hva.ewa.team2.backend.domain.models.HourRegistration.HourRegistration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class HourRegistrationRepositoryTests {

    private final HourRegistrationRepository hourRegistrationRepository;
    private final DateServiceLogic dateService;

    @Autowired
    public HourRegistrationRepositoryTests(
            HourRegistrationRepository hourRegistrationRepository,
            DateServiceLogic dateService) {
        this.hourRegistrationRepository = hourRegistrationRepository;
        this.dateService = dateService;
    }

    @BeforeEach
    void setupTestingData() {
        deleteAllHourRegistrations();
        addHourRegistrations();
    }

    /**
     * MARK: Tests
     **/
    @Test
    void testShouldHaveHourRegistrations() {
        List<HourRegistration> hourRegistrations = hourRegistrationRepository.fetchAllHourRegistrations();
        Assert.isTrue(hourRegistrations.size() > 0, "Hour registrations should not be empty");
    }

    @Test
    void testFetchSingleHourRegistration() {
        Optional<HourRegistration> hourRegistrations = hourRegistrationRepository.fetchHourRegistrationById(0);
        Assert.isTrue(hourRegistrations.isPresent(), "Did not find Hour Registration for id 0.");
    }

    @Test
    void testFetchAllHourRegistrationFromUser0() {
        List<HourRegistration> hourRegistrations = hourRegistrationRepository.fetchAllHourRegistrationsByUser(0);
        Assert.isTrue(hourRegistrations.size() == 5, "There should be 4 hour registrations in the repo.");
    }

    @Test
    void testFetchAllHourRegistrationFromUser1() {
        List<HourRegistration> hourRegistrations = hourRegistrationRepository.fetchAllHourRegistrationsByUser(1);
        Assert.isTrue(hourRegistrations.size() == 2, "There should be 2 hour registrations in the repo.");
    }

    @Test
    void testFetchAllHourRegistrationFromProject0() {
        List<HourRegistration> hourRegistrations = hourRegistrationRepository.fetchAllHourRegistrationByProject(0);
        Assert.isTrue(hourRegistrations.size() == 7, "There should be 6 hour registrations in the repo for project 0.");
    }

    @Test
    void testFetchAllHourRegistrationFromProject10ShouldBeEmpty() {
        List<HourRegistration> hourRegistrations = hourRegistrationRepository.fetchAllHourRegistrationByProject(10);
        Assert.isTrue(hourRegistrations.size() == 0, "There should be no hour registrations for project id 10.");
    }

    @Test
    void testIsAbleToApproveHourRegistration() {
        HourRegistration fetchedHourRegistrationFromRepo = hourRegistrationRepository.fetchHourRegistrationById(0).get();
        Assert.isTrue(!fetchedHourRegistrationFromRepo.isAccepted(), "HourRegistration from repo should not be accepted.");

        fetchedHourRegistrationFromRepo.setStatus(HourRegistration.Status.ACCEPTED);

        try {
            hourRegistrationRepository.updateHourRegistration(fetchedHourRegistrationFromRepo.getId(), fetchedHourRegistrationFromRepo);
            HourRegistration updatedHR = hourRegistrationRepository.fetchHourRegistrationById(0).get();
            Assert.isTrue(updatedHR.isAccepted(), "Hour Registration should now be accpeted");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testIsAbleToDeleteHourRegistration() {
        Optional<HourRegistration> fetchedHourRegistrationFromRepo = hourRegistrationRepository.fetchHourRegistrationById(0);
        Assert.isTrue(fetchedHourRegistrationFromRepo.isPresent(), "HourRegistration from repo should exist for id 0.");

        try {
            hourRegistrationRepository.deleteHourRegistration(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Optional<HourRegistration> deletedHourRegistration = hourRegistrationRepository.fetchHourRegistrationById(0);
        Assert.isTrue(deletedHourRegistration.isEmpty(), "HourRegistration from repo should exist NOT exisit for id 0.");
    }

    /**
     * MARK: Privates
     **/

    private void deleteAllHourRegistrations() {
        List<Long> ids = hourRegistrationRepository.fetchAllHourRegistrations().stream()
                .map(HourRegistration::getId)
                .toList();

        for (Long id : ids) {
            try {
                hourRegistrationRepository.deleteHourRegistration(id);
            } catch (Exception e) {
                System.out.println(e);
                throw new RuntimeException(e);
            }
        }
    }

    private void addHourRegistrations() {
        List<CreateHourRegistrationRequest> testHourRegistrations = testHourRegistrations();
        for (CreateHourRegistrationRequest hourRegistration : testHourRegistrations) {
            try {
                hourRegistrationRepository.createHourRegistration(hourRegistration);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<CreateHourRegistrationRequest> testHourRegistrations() {
        return List.of(
                // ** MARK: User 0 ** //
                new CreateHourRegistrationRequest(
                        0,
                        0,
                        dateService.currentDay(-3, 10, 0),
                        dateService.currentDay(-3, 12, 0),
                        "Gewerkt aan het project"
                ),
                new CreateHourRegistrationRequest(
                        0,
                        0,
                        dateService.currentDay(-2, 8, 30),
                        dateService.currentDay(-2, 12, 0),
                        "Gewerkt aan het project"
                ),
                new CreateHourRegistrationRequest(
                        0,
                        0,
                        dateService.currentDay(-1, 12, 15),
                        dateService.currentDay(-1, 16, 0),
                        "Gewerkt aan het project"
                ),
                new CreateHourRegistrationRequest(
                        0,
                        0,
                        dateService.currentDay(8, 30),
                        dateService.currentDay(12, 0),
                        "Gewerkt aan het project"
                ),
                new CreateHourRegistrationRequest(
                        0,
                        0,
                        dateService.currentDay(13, 0),
                        dateService.currentDay(17, 30),
                        "Gewerkt aan het project"
                ),
                // ** MARK: User 1 ** //
                new CreateHourRegistrationRequest(
                        0,
                        1,
                        dateService.currentDay(8, 30),
                        dateService.currentDay(12, 0),
                        "Gewerkt aan het project"
                ),
                new CreateHourRegistrationRequest(
                        0,
                        1,
                        dateService.currentDay(13, 0),
                        dateService.currentDay(17, 30),
                        "Gewerkt aan het project"
                )
        );
    }
}
