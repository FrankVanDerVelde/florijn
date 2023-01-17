package com.hva.ewa.team2.backend.lukas.rest;

import com.hva.ewa.team2.backend.Config;
import com.hva.ewa.team2.backend.data.project.ProjectRepository;
import com.hva.ewa.team2.backend.data.user.UserRepository;
import com.hva.ewa.team2.backend.domain.models.project.Project;
import com.hva.ewa.team2.backend.domain.models.project.ProjectParticipant;
import com.hva.ewa.team2.backend.domain.models.user.Admin;
import com.hva.ewa.team2.backend.domain.models.user.Client;
import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import com.hva.ewa.team2.backend.domain.models.user.User;
import com.hva.ewa.team2.backend.rest.project.request.ProjectParticipantAddInfoRequest;
import com.hva.ewa.team2.backend.security.JWToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectRestTest {

    @Autowired
    private Config apiConfig;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    String jwt;
    Admin admin;
    Client client;
    Project project;
    Specialist specialist;

    @BeforeEach
    public void setup() {
        admin = userRepository.save(new Admin(5, "admin@test.com", "test", "", "Admin", "Bae"));
        client = userRepository.save(new Client(0, "hello@client.com", "welp", "", ""));
        specialist = userRepository.save(new Specialist(0, "spec@well.com", "", "", "", ""));

        project = projectRepository.save(new Project(0, "hello", "world", client));

        jwt = getJwt(admin);
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void addNewProject() {
        // checking if we can't access when we have no authorization
        final ResponseEntity<String> unauthCreateRes = restTemplate.postForEntity("/projects/create", null, String.class);
        Assertions.assertEquals(401, unauthCreateRes.getStatusCodeValue());

        // checking if we can access when we have authorization
        // creating the headers, and the multipart form data
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.set("title", "hello");
        body.set("description", "world");
        body.set("client", client.getId());

        // making the authenticated request and making sure it was created.
        final ResponseEntity<Project> test = restTemplate.postForEntity("/projects/create", new HttpEntity<>(body, getAuthHeaders("/projects/create", true)), Project.class);
        Assertions.assertEquals(201, test.getStatusCodeValue());

        // checking if the project was created and if it matches.
        final Project createdProject = test.getBody();
        Assertions.assertNotNull(createdProject);
        Assertions.assertEquals("hello", createdProject.getTitle());
        Assertions.assertEquals("world", createdProject.getDescription());
        Assertions.assertEquals(client.getId(), createdProject.getClient().getId());
    }

    @Test
    public void check() {
        // test project retrieval with specialist that's not assigned to the project
        jwt = getJwt(specialist);
        final ResponseEntity<Project> test = restTemplate.exchange("/projects/" + project.getId(), HttpMethod.GET, new HttpEntity<>(null, getAuthHeaders("/projects/create", false)), Project.class);
        Assertions.assertEquals(401, test.getStatusCodeValue());

        // adding the specialist to the project
        jwt = getJwt(admin);
        final String addRoute = "/projects/" + project.getId() + "/participants/add";
        final ResponseEntity<ProjectParticipant> developer = restTemplate.postForEntity(addRoute, new HttpEntity<>(new ProjectParticipantAddInfoRequest(specialist.getId(), "developer", 20), getAuthHeaders(addRoute,false)), ProjectParticipant.class);
        Assertions.assertEquals(200, developer.getStatusCodeValue());

        // checking if the specialist can access the project now
        jwt = getJwt(specialist);
        final ResponseEntity<Project> test1 = restTemplate.exchange("/projects/" + project.getId(), HttpMethod.GET, new HttpEntity<>(null, getAuthHeaders("/projects/create",false)), Project.class);
        Assertions.assertEquals(200, test1.getStatusCodeValue());
    }

    public HttpHeaders getAuthHeaders(String url, boolean formData) {
        HttpHeaders authHeaders = restTemplate.headForHeaders(url);
        authHeaders.setBearerAuth(jwt);
        if (formData) authHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        return authHeaders;
    }

    public String getJwt(User user) {
        return new JWToken(user.getId(), user.getRole()).encode(this.apiConfig.getIssuer(),
                this.apiConfig.getPassphrase(),
                this.apiConfig.getTokenDurationOfValidity());
    }

}
