package com.HealthApp.controller;

import com.HealthApp.model.Client;
import com.HealthApp.model.Goal;
import com.HealthApp.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientController {

    @Autowired
    private ClientService service;

    @GetMapping("/api/clients")
    public List<Client> getAllClient() {
        return service.getAllClients();
    }

    @GetMapping("/api/clients/{cliID}")
    public Client getClient(@PathVariable("cliID") Long id) {
        return service.getClientById(id);
    }

    @GetMapping("/api/clients/count")
    public Long countClient() {
        return service.countClient();
    }

    @PostMapping("/api/clients")
    public ResponseEntity<Client> saveClient(@RequestBody Client client) {

        Client savedClient = service.saveClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    @DeleteMapping("/api/clients/{cliID}")
    public ResponseEntity<Void> deleteClient(@PathVariable("cliID") Long id) {
        service.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("api/clients")
    public String deleteAllClients() {
        service.deleteAllClients();
        return "All clients deleted";
    }

    @PutMapping("api/clients/{id}")
    public Client updateClient(@PathVariable Long id, @RequestBody Client client) {
        return service.updateClient(id, client);
    }

    @GetMapping("/api/clients/goal/{goal}")
    public List<Client> getClientByGoal(@PathVariable Goal goal) {
        return service.getClientByGoal(goal);
    }
}
