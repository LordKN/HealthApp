package com.HealthApp.controller;

import com.HealthApp.model.Client;
import com.HealthApp.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/api/client/{cliID}")
    public Client getClient(@PathVariable("cliID") Long id) {
        return service.getClientById(id);
    }

    @GetMapping("/api/clients/count")
    public Long countClient() {
        return service.countClient();
    }

    @PostMapping("/api/client")
    public void saveClient(@RequestBody Client client) {
        service.saveClient(client);
    }

    @DeleteMapping("/api/client/{cliID}")
    public String deleteClient(@PathVariable("cliID") Long id) {
        service.deleteClient(id);
        return "Client Deleted";
    }

    @DeleteMapping("api/clients")
    public String deleteAllClients() {
        service.deleteAllClients();
        return "All clients deleted";
    }
}
