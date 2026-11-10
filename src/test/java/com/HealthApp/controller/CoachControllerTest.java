package com.HealthApp.controller;

import com.HealthApp.model.Coach;
import com.HealthApp.model.Role;
import com.HealthApp.model.Specialty;
import com.HealthApp.service.CoachService;
import com.HealthApp.service.JwtService;
import com.HealthApp.service.MyUserDetailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CoachController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CoachControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CoachService service;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private MyUserDetailService myUserDetailService;

    @Test
    @DisplayName("Test create user")
    void testSaveCoach_whenValidCoachObjectProvided_returnResponseEntityCreated() throws Exception {

        // Arrange
        Coach c = new Coach();

        c.setName("Nguyen");
        c.setEmail("coach@example.com");
        c.setSpecialty(Specialty.STRENGTH_TRAINING);
        c.setYearsOfExperience(5);
        c.setClientCount(10);
        c.setWorkPlace("GrindHub");
        c.setDesc("Certified fitness coach");
        c.setRole(Role.COACH);

        when(service.saveCoach(any(Coach.class))).thenReturn(c);

        ObjectMapper objectMapper = new ObjectMapper();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/auth/coaches")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(c));

        // Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andReturn();

        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        Coach coach = objectMapper.convertValue(responseBodyAsString, Coach.class);

        // Assert
        assertEquals(c.getName(), coach.getName());
        assertEquals(c.getEmail(), coach.getEmail());
        assertEquals(c.getSpecialty(), coach.getSpecialty());
        assertEquals(c.getYearsOfExperience(), coach.getYearsOfExperience());
        assertEquals(c.getClientCount(), coach.getClientCount());
        assertEquals(c.getWorkPlace(), coach.getWorkPlace());
        assertEquals(c.getDesc(), coach.getDesc());
        assertEquals(c.getRole(), coach.getRole());

    }
}
