package com.HealthApp.controller;

import com.HealthApp.model.Client;
import com.HealthApp.service.ClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientController {

    private ClientService service;

    @GetMapping("/api/clients")
    public List<Client> getAllClient() {
        return service.getAllClients();
    }
}
