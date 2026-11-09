package com.HealthApp.controller;
import com.HealthApp.model.Client;
import com.HealthApp.service.ClientService;
import com.HealthApp.service.JwtService;
import com.HealthApp.service.MyUserDetailService;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.RequestBuilder;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest (controllers = ClientController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientService service;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private MyUserDetailService myUserDetailService;

    @Test
    @DisplayName("Test create user")
    void testSaveClient_whenValidClientObjectProvided_returnResponseEntityCreated() throws Exception {

        // Arrange
        Client c = new Client();
        c.setId(1L);
        c.setName("Nguyen");
        c.setWeight(70.0);
        c.setHeight(175.0);
        c.setBodyFat(15.0);

        // Since the object received by controller is a different object when deserializing the JSON, use any
        when(service.saveClient(any(Client.class))).thenReturn(c);

        ObjectMapper objectMapper = new ObjectMapper();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/auth/clients")              // Send a post request to this endpoint
                .contentType(MediaType.APPLICATION_JSON)          // The request body is formatted as JSON
                .content(objectMapper.writeValueAsString(c));     // Attaches the actual JSON data to the request

        // Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        Client client = objectMapper.readValue(responseBodyAsString, Client.class);

        // Assert
        assertEquals(c.getName(), client.getName());
        assertEquals(c.getHeight(), client.getHeight());
        assertEquals(c.getWeight(), client.getWeight());
        assertEquals(c.getBodyFat(), client.getBodyFat());

    }
}
