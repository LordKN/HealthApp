package com.HealthApp.controller;

import com.HealthApp.dto.ExerciseDto;
import com.HealthApp.service.ExerciseService;
import com.HealthApp.service.JwtService;
import com.HealthApp.service.MyUserDetailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest (controllers = ExerciseController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ExerciseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ExerciseService service;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private MyUserDetailService myUserDetailService;

    @Test
    @DisplayName("Test get all exercises")
    void testGetAllExercises_returnAllExercises() throws Exception {
        ExerciseDto benchPress = new ExerciseDto(1L, "Bench Press",
                null,
                null,
                null,
                null,
                null,
                null,
                null);


        ExerciseDto shoulderPress = new ExerciseDto(2L, "Shoulder Press",
                null,
                null,
                null,
                null,
                null,
                null,
                null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/exercises");

        ObjectMapper objectMapper = new ObjectMapper();
        Pageable pageable = PageRequest.of(0,4);

        when(service.getAllExercises(pageable))
                .thenReturn(new PageImpl<>(List.of(benchPress, shoulderPress), pageable, 2));

        // Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        JsonNode returnedDto = objectMapper.readTree(responseBodyAsString);

        // Assert
        assertEquals(2, returnedDto.get("content").size());
        assertEquals("Bench Press", returnedDto.get("content")
                .get(0).get("name").asString());
        assertEquals("Shoulder Press", returnedDto.get("content")
                .get(1).get("name").asString());

        assertEquals(2, returnedDto.get("totalElements").asInt());
        assertEquals(1, returnedDto.get("totalPages").asInt());
    }

    @Test
    @DisplayName("Test get all exercises")
    void testSearchExerciseByName_returnExercises() throws Exception {
        ExerciseDto benchPress = new ExerciseDto(1L, "Bench Press",
                null,
                null,
                null,
                null,
                null,
                null,
                null);


        ExerciseDto shoulderPress = new ExerciseDto(2L, "Shoulder Press",
                null,
                null,
                null,
                null,
                null,
                null,
                null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/exercises/search")
                .param("name", "Press");

        ObjectMapper objectMapper = new ObjectMapper();
        Pageable pageable = PageRequest.of(0, 4);

        when(service.getExercise(eq("Press"), isNull(), isNull(), isNull(), eq(pageable)))
                .thenReturn(new PageImpl<>(List.of(benchPress, shoulderPress), pageable, 2));

        // Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        JsonNode returnedDto = objectMapper.readTree(responseBodyAsString);

        // Assert
        assertEquals(2, returnedDto.get("content").size());
        assertEquals("Bench Press", returnedDto.get("content")
                .get(0).get("name").asString());
        assertEquals("Shoulder Press", returnedDto.get("content")
                .get(1).get("name").asString());

        assertEquals(2, returnedDto.get("totalElements").asInt());
        assertEquals(1, returnedDto.get("totalPages").asInt());
    }
}
