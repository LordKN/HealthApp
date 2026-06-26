package com.HealthApp.service;

import java.util.List;

import com.HealthApp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Client saveClient(Client client) {
        validateClient(client);

        return repo.save(client);
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

    private void validateClient(Client client) {
        if (client.getName() == null || client.getName().isBlank()) {
            throw new RuntimeException("Name is required");
        }

        if (client.getWeight() == null || client.getWeight() < 30 || client.getWeight() > 300) {
            throw new RuntimeException("Weight must be between 30 and 300 kg");
        }

        if (client.getHeight() == null || client.getHeight() < 100 || client.getHeight() > 250) {
            throw new RuntimeException("Height must be between 100 and 250 cm");
        }

        if (client.getBodyFat() < 0 || client.getBodyFat() > 70) {
            throw new RuntimeException("Body fat must be between 0 and 70 percent");
        }

        if (client.getRole() == null) {
            throw new RuntimeException("Role must be assigned");
        }
    }

    public Client updateClient(Long id, Client updatedClient) {
        Client existingClient = repo.findById(id).orElseThrow(() -> new RuntimeException("No client found"));

        existingClient.setName(updatedClient.getName());
        existingClient.setEmail(updatedClient.getEmail());
        existingClient.setPhoneNo(updatedClient.getPhoneNo());
        existingClient.setAddress(updatedClient.getAddress());
        existingClient.setEmergencyContactName(updatedClient.getEmergencyContactName());
        existingClient.setEmergencyContactPhone(updatedClient.getEmergencyContactPhone());
        existingClient.setEmergencyContactRelationship(updatedClient.getEmergencyContactRelationship());

        existingClient.setActivityLevel(updatedClient.getActivityLevel());
        existingClient.setFitnessGoal(updatedClient.getFitnessGoal());
        existingClient.setHeight(updatedClient.getHeight());
        existingClient.setWeight(updatedClient.getWeight());
        existingClient.setBodyFat(updatedClient.getBodyFat());
        existingClient.setAvailability(updatedClient.getAvailability());
        existingClient.setStressLevel(updatedClient.getStressLevel());
        existingClient.setWorkoutPreference(updatedClient.getWorkoutPreference());
        existingClient.setBarriers(updatedClient.getBarriers());
        existingClient.setSleepPattern(updatedClient.getSleepPattern());

        validateClient(existingClient);

        return repo.save(existingClient);
    }

    public List<Client> getClientByGoal(Goal goal) {
        return repo.findByFitnessGoal(goal);
    }

    public List<Client> getClientByStressLevel(Level stressLevel) {
        return repo.findByStressLevel(stressLevel);
    }

    public List<Client> getClientBySleepPattern(Pattern pattern) {
        return repo.findBySleepPattern(pattern);
    }

    public List<Client> getClientByWorkoutPreference(WorkoutPreference preference) {
        return repo.findByWorkoutPreference(preference);
    }

    public List<Client> getClientByBarrier(Barrier barrier) {
        return repo.findByBarriers(barrier);
    }

    public List<Client> getClientByWeight (Double minWeight, Double maxWeight) { return repo.findByWeightBetween(minWeight, maxWeight);}

    public List<Client> getClientByHeight (Double minHeight, Double maxHeight) {return repo.findByHeightBetween(minHeight, maxHeight);}

    public List<Client> getClientByBodyfat (Double minBodyFat, Double maxBodyFat) {return repo.findByBodyFatBetween(minBodyFat, maxBodyFat);}
}
