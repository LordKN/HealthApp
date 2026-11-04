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
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository repo;

    @InjectMocks
    private ClientService service;

    // Ideal Scenario
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
    @DisplayName("Test get all clients")
    void testGetAllClient_returnAllClients() {

        // Arrange
        Client c1 = new Client();
        c1.setName("Nguyn");

        Client c2 = new Client();
        c2.setName("Nhat");

        when(repo.findAll()).thenReturn(List.of(c1, c2));

        // Act
        List<Client> clientList = service.getAllClients();

        // Assert
        assertEquals(2, clientList.size());
        assertEquals("Nguyn", clientList.get(0).getName());
        assertEquals("Nhat", clientList.get(1).getName());

        verify(repo).findAll();
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

    // Exception Scenario
    @Test
    @DisplayName("Test get client by email throw client not found exception")
    void testGetClientByEmail_whenEmailProvided_throwUsernameNotFoundException() {

        // Arrange
        String email = "";
        when(repo.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> service.getClientByEmail(email));

        // Assert
        assertEquals("Client not found", exception.getMessage());
        verify(repo).findByEmail(email);
    }

    @Test
    @DisplayName("Test get client by Id throw client not found exception")
    void testGetClientById_whenIdProvided_throwUsernameNotFoundException() {

        // Arrange
        long id = 100L;
        when(repo.findById(id)).thenReturn(Optional.empty());

        // Act
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> service.getClientById(id));

        // Assert
        assertEquals("Client not found", exception.getMessage());
        verify(repo).findById(id);
    }

    @Test
    @DisplayName("Test delete client throw username not found exception")
    void testDeleteClient_whenIdProvided_throwUsernameNotFoundException() {

        // Arrange
        long id = 100L;
        when(repo.existsById(id)).thenReturn(false);

        // Act
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> service.deleteClient(id));

        // Assert
        assertEquals("Client not found", exception.getMessage());
        verify(repo).existsById(id);
    }

    @Test
    @DisplayName("Test delete all clients return runtime exception")
    void testDeleteAllClients_returnRuntimeException() {

        // Arrange
        when(repo.count()).thenReturn(0L);

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.deleteAllClients());

        // Assert
        assertEquals("No client to delete", exception.getMessage());
        verify(repo).count();
    }

    @Test
    @DisplayName("Test validate client throw runtime exception on empty name")
    void testValidateClient_whenBlankClientNameProvided_throwRuntimeException() {
        // Arrange
        String name = "";

        Client c = new Client();
        c.setName(name);

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.saveClient(c));

        // Assert
        assertEquals("Name is required", exception.getMessage());
    }

    @Test
    @DisplayName("Test validate client throw runtime exception on empty name")
    void testValidateClient_whenNoClientNameProvided_throwRuntimeException() {
        // Arrange
        Client c = new Client();

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.saveClient(c));

        // Assert
        assertEquals("Name is required", exception.getMessage());
    }

    @Test
    @DisplayName("Test validate client throw runtime exception on empty weight")
    void testValidateClient_whenNoClientWeightProvided_throwRuntimeException() {
        // Arrange
        Client c = new Client();
        c.setName("Nguyen");

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.saveClient(c));

        // Assert
        assertEquals("Weight must be between 30 and 300 kg", exception.getMessage());
    }

    @Test
    @DisplayName("Test validate client throw runtime exception on less than 30 kg")
    void testValidateClient_whenClientWeightLessThan30Provided_throwRuntimeException() {
        // Arrange
        Client c = new Client();
        c.setName("Nguyen");
        c.setWeight(29.0);

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.saveClient(c));

        // Assert
        assertEquals("Weight must be between 30 and 300 kg", exception.getMessage());
    }

    @Test
    @DisplayName("Test validate client throw runtime exception on more than 300 kg")
    void testValidateClient_whenClientWeightMoreThan300Provided_throwRuntimeException() {
        // Arrange
        Client c = new Client();
        c.setName("Nguyen");
        c.setWeight(301.0);

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.saveClient(c));

        // Assert
        assertEquals("Weight must be between 30 and 300 kg", exception.getMessage());
    }

    @Test
    @DisplayName("Test validate client throw runtime exception on null height")
    void testValidateClient_whenHeightIsNull_throwRuntimeException() {
        // Arrange
        Client c = new Client();
        c.setName("Nguyen");
        c.setWeight(70.0);

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.saveClient(c)
        );

        // Assert
        assertEquals("Height must be between 100 and 250 cm", exception.getMessage());
    }

    @Test
    @DisplayName("Test validate client throw runtime exception on height less than 100 cm")
    void testValidateClient_whenHeightLessThan100_throwRuntimeException() {
        // Arrange
        Client c = new Client();
        c.setName("Nguyen");
        c.setWeight(70.0);
        c.setHeight(99.0);

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.saveClient(c)
        );

        // Assert
        assertEquals("Height must be between 100 and 250 cm", exception.getMessage());
    }

    @Test
    @DisplayName("Test validate client throw runtime exception on height more than 250 cm")
    void testValidateClient_whenHeightMoreThan250_throwRuntimeException() {
        // Arrange
        Client c = new Client();
        c.setName("Nguyen");
        c.setWeight(70.0);
        c.setHeight(251.0);

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.saveClient(c)
        );

        // Assert
        assertEquals("Height must be between 100 and 250 cm", exception.getMessage());
    }

    @Test
    @DisplayName("Test validate client throw runtime exception on negative body fat")
    void testValidateClient_whenBodyFatLessThan0_throwRuntimeException() {
        // Arrange
        Client c = new Client();
        c.setName("Nguyen");
        c.setWeight(70.0);
        c.setHeight(175.0);
        c.setBodyFat(-1.0);

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.saveClient(c)
        );

        // Assert
        assertEquals("Body fat must be between 0 and 70 percent", exception.getMessage());
    }

    @Test
    @DisplayName("Test validate client throw runtime exception on body fat greater than 70")
    void testValidateClient_whenBodyFatMoreThan70_throwRuntimeException() {
        // Arrange
        Client c = new Client();
        c.setName("Nguyen");
        c.setWeight(70.0);
        c.setHeight(175.0);
        c.setBodyFat(71.0);

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.saveClient(c)
        );

        // Assert
        assertEquals("Body fat must be between 0 and 70 percent", exception.getMessage());
    }

    @Test
    @DisplayName("Test validate client throw runtime exception on null role")
    void testValidateClient_whenRoleIsNull_throwRuntimeException() {
        // Arrange
        Client c = new Client();
        c.setName("Nguyen");
        c.setWeight(70.0);
        c.setHeight(175.0);
        c.setBodyFat(20.0);

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.saveClient(c)
        );

        // Assert
        assertEquals("Role must be assigned", exception.getMessage());
    }

    @Test
    @DisplayName("Test update client")
    void testUpdateClient_whenClientIdAndUpdatedClientProvided_returnUpdatedClientObject() {
        // Arrange
        Client existingClient = new Client();
        existingClient.setId(1L);
        existingClient.setRole(Role.CLIENT);

        when(repo.findById(1L)).thenReturn(Optional.of(existingClient));

        Client updatedClient = new Client();
        updatedClient.setName("Man");
        updatedClient.setWeight(37.0);
        updatedClient.setHeight(170.0);

        when(repo.save(existingClient)).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Client client = service.updateClient(1L, updatedClient);

        // Assert
        assertEquals("Man", client.getName());
        assertEquals(37.0, client.getWeight());
        assertEquals(170.0, client.getHeight());

        verify(repo).findById(1L);
        verify(repo).save(existingClient);
    }
}
