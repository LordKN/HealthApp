package com.HealthApp.controller;

import com.HealthApp.dto.CreateWorkoutRequestDto;
import com.HealthApp.dto.WorkoutDayRequestDto;
import com.HealthApp.model.Workout;
import com.HealthApp.service.JwtService;
import com.HealthApp.service.MyUserDetailService;
import com.HealthApp.service.WorkoutService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest (controllers = WorkoutController.class)
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(username = "client@example.com", roles = "CLIENT")
public class WorkoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WorkoutService service;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private MyUserDetailService myUserDetailService;

    @Test
    @DisplayName("Test create workout")
    void testSaveWorkout_whenValidDtoAndAuthenticationProvided_returnResponseEntityCreated() throws Exception {

        // Arrange
        String email = "client@example.com";

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                email,
                null,
                List.of(new SimpleGrantedAuthority("ROLE_CLIENT")
        ));

        Workout workout = new Workout();
        workout.setId(1L);
        workout.setName("Push Day");
        workout.setDescription("Upper Body");

        CreateWorkoutRequestDto requestDto = getCreateWorkoutRequestDto();

        ObjectMapper objectMapper = new ObjectMapper();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/workouts")
                .principal(authentication)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto));

        when(service.saveWorkout(any(CreateWorkoutRequestDto.class), eq(email))).thenReturn(workout);

        // Act
        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andReturn();

        String responseBodyAsString = result.getResponse().getContentAsString();
        Workout returnedWorkout = objectMapper.readValue(responseBodyAsString, Workout.class);

        // Assert
        assertEquals(workout.getId(), returnedWorkout.getId());
        assertEquals(workout.getName(), returnedWorkout.getName());
        assertEquals(workout.getDescription(), returnedWorkout.getDescription());

        verify(service).saveWorkout(any(CreateWorkoutRequestDto.class), eq(email));
        
        
    }

    private static CreateWorkoutRequestDto getCreateWorkoutRequestDto() {

        List<WorkoutDayRequestDto> days = new ArrayList<>();

        for (int i = 1; i <= 7; i++) {
            days.add(new WorkoutDayRequestDto(
                    i,
                    "Day " + i,
                    List.of(1L, 2L)
            ));
        }

        return new CreateWorkoutRequestDto(
                "Push Day",
                "Upper Body",
                days
        );
    }
}
