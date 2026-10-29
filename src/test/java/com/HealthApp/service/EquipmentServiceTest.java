package com.HealthApp.service;

import com.HealthApp.model.Equipment;
import com.HealthApp.repo.EquipmentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EquipmentServiceTest {

    @Mock
    private EquipmentRepository repo;

    @InjectMocks
    private EquipmentService service;

    @Test
    @DisplayName("Test get equipment by Id")
    void testGetEquipmentById_whenEquipmentIdIsProvided_returnEquipmentObject() {

        // Arrange
        Equipment equipment = new Equipment();
        equipment.setId(1L);
        when(repo.findById(1L))
                .thenReturn(Optional.of(equipment));


        // Act
        Equipment e = service.getEquipmentById(1L);

        // Assert
        assertEquals(1L, e.getId());

        verify(repo).findById(1L);
    }

    @Test
    @DisplayName("Test get equipment by wger Id")
    void testGetEquipmentByWgerId_whenEquipmentWgerIdIsProvided_returnEquipmentObject() {

        // Arrange
        Equipment equipment = new Equipment();
        equipment.setWgerId(1);
        when(repo.findByWgerId(1))
                .thenReturn(Optional.of(equipment));

        // Act
        Equipment e = service.getEquipmentByWgerId(1);

        // Assert
        assertEquals(1, e.getWgerId());

        verify(repo).findByWgerId(1);
    }

    @Test
    @DisplayName("Test get equipment by name")
    void testGetEquipmentByName_whenEquipmentNameIsProvided_returnListOfEquipments() {
        // Arrange
        Equipment e1 = new Equipment();
        e1.setName("Dumbbell");

        Equipment e2 = new Equipment();
        e2.setName("Barbell");

        String name = "Bell";

        when(repo.findByNameContainingIgnoreCase(name))
                .thenReturn(List.of(e1, e2));

        // Act
        List<Equipment> equipmentList = repo.findByNameContainingIgnoreCase(name);

        // Assert
        assertEquals(2, equipmentList.size());

        verify(repo).findByNameContainingIgnoreCase(name);
    }

}
