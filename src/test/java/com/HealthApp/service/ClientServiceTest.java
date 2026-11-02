package com.HealthApp.service;

import com.HealthApp.model.Client;
import com.HealthApp.model.Role;
import com.HealthApp.repo.ClientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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

    @Test
    @DisplayName("Test save client")
    void testSaveClient_whenClientObjectIsProvided_returnSavedClientObject() {
        // Arrange
        when(repo.save(any(Client.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Client client = new Client();
        client.setName("Nguyn");
        client.setWeight(70.0);
        client.setHeight(172.0);
        client.setRole(Role.CLIENT);

        // Act
        Client c = service.saveClient(client);


        // Assert
        assertEquals("Nguyn", client.getName());
        assertEquals(70.0, client.getWeight());
        assertEquals(172.0, client.getHeight());
    }

    @Test
    @DisplayName("Test delete client")
    void testDeleteClient_whenClientIdIsProvided_deleteThatClient() {
        // Arrange
        Long id = 1L;
        when(repo.existsById(id)).thenReturn(true);

        // Act
        service.deleteClient(id);

        // Assert
        verify(repo).existsById(id);
        verify(repo).deleteById(id);
    }

    @Test
    @DisplayName("Test count client")
    void testCountClient() {
        // Arrange
        when(repo.count()).thenReturn(5L);

        // Act
        Long result = service.countClient();

        // Assert
        assertEquals(5L, result);
        verify(repo).count();
    }

    @Test
    @DisplayName("Test delete all clients")
    void testDeleteAllClients() {
        // Arrange
        when(repo.count()).thenReturn(5L);

        // Act
        service.deleteAllClients();

        // Assert
        verify(repo).count();
        verify(repo).deleteAll();
    }
}
