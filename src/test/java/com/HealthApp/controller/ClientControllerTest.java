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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.RequestBuilder;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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

    @Test
    @DisplayName("Test get current client")
    void testGetCurrentClient_returnLoggedInClient() throws Exception {
        // Arrange
        Client c = new Client();
        String email = "client@example.com";

        c.setEmail(email);
        c.setId(1L);
        c.setName("Nguyen");
        c.setWeight(70.0);
        c.setHeight(175.0);
        c.setBodyFat(15.0);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                email,
                null,
                List.of(new SimpleGrantedAuthority("ROLE_CLIENT"))
        );

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/clients/me")
                .principal(authentication);

        ObjectMapper objectMapper = new ObjectMapper();

        when(service.getClientByEmail(email)).thenReturn(c);

        // Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        Client returnedClient = objectMapper.readValue(responseBodyAsString, Client.class);

        // Assert
        assertEquals(c.getName(), returnedClient.getName());
        assertEquals(c.getWeight(), returnedClient.getWeight());
        assertEquals(c.getHeight(), returnedClient.getHeight());
        assertEquals(c.getBodyFat(), returnedClient.getBodyFat());

        verify(service).getClientByEmail(email);
    }

}
