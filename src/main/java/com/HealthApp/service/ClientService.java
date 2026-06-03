package com.HealthApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.HealthApp.model.Client;
import com.HealthApp.model.Goal;
import com.HealthApp.repo.ClientRepository;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repo;

    public List<Client> getAllClients() {
        return repo.findAll();
    }

    public Client getClientById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    public void saveClient(Client client) {
        if (client.getName() == null || client.getName().isBlank()) {
            throw new RuntimeException("Name is required");
        }

        if (client.getWeight() == null || client.getWeight() <= 0) {
            throw new RuntimeException("Weight must be greater than 0");
        }

        if (client.getHeight() == null || client.getHeight() <= 0) {
            throw new RuntimeException("Height must be greater than 0");
        }

        if (client.getRole() == null) {
            throw new RuntimeException("Role must be assigned");
        }

        repo.save(client);
    }

    public void deleteClient(Long id) {

        if (!repo.existsById(id)) {
            throw new RuntimeException("Client not found");
        }

        repo.deleteById(id);
    }

    public long countClient() {
        return repo.count();
    }

    public void deleteAllClients() {

        if (repo.count() == 0) {
            throw new RuntimeException("No client to delete");
        }

        repo.deleteAll();
    }
}
