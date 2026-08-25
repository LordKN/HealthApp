package com.HealthApp.controller;

import com.HealthApp.model.*;
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
    public ResponseEntity<List<Client>> getAllClient() {
        return ResponseEntity.ok(service.getAllClients());
    }

    @GetMapping("/api/clients/{cliID}")
    public ResponseEntity<Client> getClientByid(@PathVariable("cliID") Long id) {

        Client client = service.getClientById(id);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/api/clients/{cliEmail}")
    public ResponseEntity<Client> getClientByEmail(@PathVariable("cliEmail") String email) {
        Client client = service.getClientByEmail(email);
        return ResponseEntity.ok(client);
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

    @DeleteMapping("/api/clients")
    public ResponseEntity<Void> deleteAllClients() {
        service.deleteAllClients();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/api/clients/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client) {
        Client updatedClient = service.updateClient(id, client);
        return ResponseEntity.ok(updatedClient);
    }

    @GetMapping("/api/clients/goal/{goal}")
    public ResponseEntity<List<Client>> getClientByGoal(@PathVariable Goal goal) {
        return ResponseEntity.ok(service.getClientByGoal(goal));
    }

    @GetMapping("/api/clients/level/{level}")
    public ResponseEntity<List<Client>> getClientByStressLevel(@PathVariable Level level) {
        return ResponseEntity.ok(service.getClientByStressLevel(level));
    }

    @GetMapping("/api/clients/pattern/{pattern}")
    public ResponseEntity<List<Client>> getClientBySleepPattern(@PathVariable Pattern pattern) {
        return ResponseEntity.ok(service.getClientBySleepPattern(pattern));
    }

    @GetMapping("/api/clients/preference/{preference}")
    public ResponseEntity<List<Client>> getClientByWorkoutPreference(@PathVariable WorkoutPreference preference) {
        return ResponseEntity.ok(service.getClientByWorkoutPreference(preference));
    }

    @GetMapping("/api/clients/barrier/{barrier}")
    public ResponseEntity<List<Client>> getClientByBarrier(@PathVariable Barrier barrier) {
        return ResponseEntity.ok(service.getClientByBarrier(barrier));
    }

    @GetMapping("/api/clients/weight")
    public ResponseEntity<List<Client>> getClientByWeightRange(@RequestParam Double min, @RequestParam Double max) {
        return ResponseEntity.ok(service.getClientByWeight(min, max));
    }

    @GetMapping("/api/clients/height")
    public ResponseEntity<List<Client>> getClientByHeightRange(@RequestParam Double min, @RequestParam Double max) {
        return ResponseEntity.ok(service.getClientByHeight(min, max));
    }

    @GetMapping("/api/clients/bodyfat")
    public ResponseEntity<List<Client>> getClientByBodyfatRange(@RequestParam Double min, @RequestParam Double max) {
        return ResponseEntity.ok(service.getClientByBodyfat(min, max));
    }
}
