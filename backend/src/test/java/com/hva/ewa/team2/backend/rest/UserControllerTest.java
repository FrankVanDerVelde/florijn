package com.hva.ewa.team2.backend.rest;

import static org.hamcrest.CoreMatchers.*;
import com.hva.ewa.team2.backend.domain.models.user.Admin;
import com.hva.ewa.team2.backend.domain.models.user.Client;
import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    HttpEntity<String> entity;

    private static class SignOnInfo {
        public String email;
        public String password;

        public SignOnInfo(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    @BeforeEach
    public void makeUserLogin(){
        SignOnInfo signOnInfo = new SignOnInfo("admin1@test.com", "test");
        ResponseEntity<Admin> response =
                this.restTemplate.postForEntity("/auth/login", signOnInfo, Admin.class);

        String jwToken = response.getHeaders().get("Authorization").get(0);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwToken);
        this.entity = new HttpEntity<>(headers);
    }

    @Test
    public void allUsersByRoleCanBeRetrieved() {
        ResponseEntity<Admin[]> response1 = this.restTemplate.exchange("/users/role/ADMIN", HttpMethod.GET, this.entity, Admin[].class);
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        Admin[] admins = response1.getBody();
        assertThat(admins.length, is(greaterThan(0)));

        ResponseEntity<Specialist[]> response2 = this.restTemplate.exchange("/users/role/SPECIALIST", HttpMethod.GET, this.entity, Specialist[].class);
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        Specialist[] specialists = response2.getBody();
        assertThat(specialists.length, is(greaterThan(0)));

        ResponseEntity<Client[]> response3 = this.restTemplate.exchange("/users/role/CLIENT", HttpMethod.GET, this.entity, Client[].class);
        assertEquals(HttpStatus.OK, response3.getStatusCode());
        Client[] clients = response3.getBody();
        assertThat(clients.length, is(greaterThan(0)));
    }
}
