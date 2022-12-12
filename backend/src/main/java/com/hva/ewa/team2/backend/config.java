package com.hva.ewa.team2.backend;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class config implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://*.up.railway.app")
                .allowedOriginPatterns("http://localhost:*", "https://localhost:*", "https://*.up.railway.app")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS");

    }
}

