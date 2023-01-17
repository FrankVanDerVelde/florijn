package com.hva.ewa.team2.backend.rest;

import com.hva.ewa.team2.backend.domain.models.user.Admin;
import com.hva.ewa.team2.backend.domain.models.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static class SignOnInfo {
        public String email;
        public String password;

        public SignOnInfo(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    @Test
    public void postedLoginSucceeds() {
        SignOnInfo signOnInfo = new SignOnInfo("admin1@test.com", "test");

        ResponseEntity<Admin> response =
                this.restTemplate.postForEntity("/auth/login", signOnInfo, Admin.class);

        // check status code, location header and response body of post request
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());

        User user = response.getBody();
        assertEquals(user.getId(), 1);
        assertEquals(User.Role.ADMIN, user.getRole());

        String token = response.getHeaders().get("Authorization").get(0);
        assertTrue(token.startsWith("Bearer "));
    }
}
