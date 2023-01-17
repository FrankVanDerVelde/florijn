package com.hva.ewa.team2.backend.lukas;

import com.hva.ewa.team2.backend.BackendApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class BackendApplicationTests {

    @Autowired
    BackendApplication application;

    @Test
    void contextLoads() {
        assertNotNull(application);
    }

}
