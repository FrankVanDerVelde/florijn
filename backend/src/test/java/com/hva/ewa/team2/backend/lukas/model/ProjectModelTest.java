package com.hva.ewa.team2.backend.lukas.model;

import com.hva.ewa.team2.backend.domain.models.project.Project;
import com.hva.ewa.team2.backend.domain.models.project.ProjectParticipant;
import com.hva.ewa.team2.backend.domain.models.user.Admin;
import com.hva.ewa.team2.backend.domain.models.user.Client;
import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProjectModelTest {

    private Client client;
    private Project project1;

    @BeforeEach
    void setup() {
        client = new Client(1, "welp@lol.com", "test", "", "ING");
        project1 = new Project(1, "ING", "ing is a cool bank", client);
    }

    @Test
    void testProjectInformation() {
        Assertions.assertEquals(1, project1.getId());
        Assertions.assertEquals("ING", project1.getTitle());
        Assertions.assertEquals("ing is a cool bank", project1.getDescription());
        Assertions.assertEquals(client, project1.getClient());
        Assertions.assertFalse(project1.isArchived());
    }

    @Test
    void testParticipantAddingAndRemoving() {
        Specialist specialist = new Specialist(5, "admin@lol.com", "", "Mimi", "Webb");
        final ProjectParticipant participant = new ProjectParticipant(specialist, "developer", 20);

        project1.addSpecialist(participant);
        Assertions.assertEquals(1, project1.getParticipants().size());
        Assertions.assertTrue(project1.getParticipants().contains(participant));
        Assertions.assertEquals(project1.getParticipantByUserId(specialist.getId()), participant);

        project1.removeSpecialist(participant);

        Assertions.assertEquals(0, project1.getParticipants().size());
        Assertions.assertNull(project1.getParticipantByUserId(specialist.getId()));
    }

    @Test
    void testAccess() {
        // creating test data.
        Specialist specialist = new Specialist(6, null, null, null, null);
        Admin admin = new Admin(7, null, null, null, null, null);
        Client client1 = new Client(8, null, null, null, null);

        // testing if non-added specialist has no access.
        Assertions.assertFalse(project1.hasAccess(specialist, false));
        // testing if admin has access.
        Assertions.assertTrue(project1.hasAccess(admin, true));

        project1.addSpecialist(new ProjectParticipant(specialist, "developer", 20));

        // testing if the added specialist has access
        Assertions.assertTrue(project1.hasAccess(specialist, false));
        // testing if the added specialist has access with moderation permission
        Assertions.assertFalse(project1.hasAccess(specialist, true));

        // testing if the client has access
        Assertions.assertTrue(project1.hasAccess(client, true));
        // testing if a non-added client has access.
        Assertions.assertFalse(project1.hasAccess(client1, false));

    }

}
