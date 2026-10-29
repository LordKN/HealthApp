package com.HealthApp.service;

import com.HealthApp.model.Muscle;
import com.HealthApp.repo.MuscleRepository;
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
class MuscleServiceTest {

    @Mock
    private MuscleRepository repo;

    @InjectMocks
    private MuscleService service;

    @Test
    @DisplayName("Test get muscle by id")
    void testGetMuscleById_whenMuscleIdIsProvided_returnMuscleObject() {
        // Arrange
        Muscle muscle = new Muscle();
        muscle.setId(1L);
        when(repo.findById(1L))
                .thenReturn(Optional.of(muscle));

        // Act
        Muscle c = service.getMuscleById(1L);

        // Assert
        assertEquals(1L, c.getId());

        verify(repo).findById(1L);
    }

    @Test
    @DisplayName("Test get muscle by wger id")
    void testGetMuscleByWgerId_whenWgerIdIsProvided_returnMuscleObject() {
        // Arrange
        Muscle muscle = new Muscle();
        muscle.setWgerId(1);
        when(repo.findByWgerId(1))
                .thenReturn(Optional.of(muscle));

        // Act
        Muscle m = service.getMuscleByWgerId(1);

        //Assert
        assertEquals(1, m.getWgerId());

        verify(repo).findByWgerId(1);
    }

    @Test
    @DisplayName("Test get muscle by name")
    void testGetMuscleByName_whenNameIsProvided_returnListOfMuscleObjects() {
        // Arrange
        Muscle m1 = new Muscle();
        m1.setName("Front Shoulders");

        Muscle m2 = new Muscle();
        m2.setName("Back Shoulders");

        String name = "shoulders";
        when(repo.findByNameContainingIgnoreCase(name))
                .thenReturn(List.of(m1, m2));

        // Act
        List<Muscle> muscleList = service.getMuscleByName(name);

        // Assert
        assertEquals(2, muscleList.size());

        verify(repo).findByNameContainingIgnoreCase(name);
    }
}
