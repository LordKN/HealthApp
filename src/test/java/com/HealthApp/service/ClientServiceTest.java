package com.HealthApp.service;

import com.HealthApp.model.Client;
import com.HealthApp.repo.ClientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository repo;

    @InjectMocks
    private ClientService service;

    @Test
    @DisplayName("Test get client by id")
    void testGetClientById_whenClientIdIsProvided_returnClientObject() {
        // Arrange
        Client client = new Client();
        client.setId(1L);
        when(repo.findById(1L))
                .thenReturn(Optional.of(client));

        // Act
        Client c = service.getClientById(1L);

        // Assert
        assertEquals(1L, c.getId());

        verify(repo).findById(1L);
    }

    @Test
    @DisplayName("Test get client by email")
    void testGetClientByEmail_whenClientEmailIsProvided_returnClientObject() {
        // Arrange
        Client client = new Client();
        String email = "client@example.com";
        client.setEmail(email);
        when(repo.findByEmail(email))
                .thenReturn(Optional.of(client));

        // Act
        Client c = service.getClientByEmail(email);

        // Assert
        assertEquals("client@example.com", c.getEmail());

        verify(repo).findByEmail(email);
    }
}
