package com.HealthApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * This class configures global settings for Spring MVC
 *
 * In this case, we use it to solve CORS problems between:
 *
 * REACT frontend:
 *      http://localhost:5173
 *
 * Spring Boot backend:
 *      http://localhost:8080
 *
 * Even though they are both localhost, the browser treats them
 * as different origins because they use different ports
 *
 * Without CORS configuration:
 *
 * React request
 * ↓
 * Browser checks permission
 * ↓
 * Spring does not allow localhost:5173
 * ↓
 * Browser blocks request
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /*
     * Override Spring's default CORS settings
     *
     * This method runs when Spring starts and registers
     * which external applications are allowed to call our API
     */

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}
