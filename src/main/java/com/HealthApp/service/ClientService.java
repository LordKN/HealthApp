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
        repo.save(client);
    }

    public void deleteClient(Long id) {
        repo.deleteById(id);
    }

    public long countClient() {
        return repo.count();
    }

    public void deleteAllClients() {
        repo.deleteAll();
    }
}
