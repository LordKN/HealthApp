package com.HealthApp.service;

import com.HealthApp.dto.CreateWorkoutRequestDto;
import com.HealthApp.dto.WorkoutDayRequestDto;
import com.HealthApp.exception.ExerciseNotFoundException;
import com.HealthApp.model.Client;
import com.HealthApp.model.Exercise;
import com.HealthApp.model.Workout;
import com.HealthApp.model.WorkoutDay;
import com.HealthApp.repo.ClientRepository;
import com.HealthApp.repo.ExerciseRepository;
import com.HealthApp.repo.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class WorkoutService {

    @Autowired
    private WorkoutRepository repo;

    @Autowired
    private ExerciseRepository exerciseRepo;

    @Autowired
    private ClientRepository clientRepo;

    public List<Workout> getWorkouts(String email) {

        Client client = clientRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return client.getWorkouts();
    }

    public Workout getWorkoutById(Long id, String email) {
        Client client = clientRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return client.getWorkouts()
                .stream()
                .filter(workout -> Objects.equals(workout.getId(), id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Workout not found"));
    }

    //If an exception is thrown, no workout is saved
    @Transactional
    public void saveWorkout (CreateWorkoutRequestDto workoutRequestDto, String userEmail) {

        //validateWorkout(workout);
        Workout workout = new Workout();

        Client client = clientRepo.findByEmail(userEmail)
                        .orElseThrow(() ->new UsernameNotFoundException("User not found"));
        workout.setClient(client);

        workout.setName(workoutRequestDto.name());
        workout.setDescription(workoutRequestDto.description());

        List<WorkoutDayRequestDto> dayList = workoutRequestDto.days();
        List<WorkoutDay> workoutDays = workout.getDays();

        for (WorkoutDayRequestDto dayRequestDto : dayList) {
            WorkoutDay workoutDay = new WorkoutDay();

            workoutDay.setDayNumber(dayRequestDto.dayNumber());
            workoutDay.setName(dayRequestDto.name());
            workoutDay.setWorkout(workout);

            List<Long> exerciseIds = dayRequestDto.exerciseIds();
            List<Exercise> exercises = workoutDay.getExercises();

            for (Long id : exerciseIds) {
                Exercise exercise = exerciseRepo.findById(id)
                                .orElseThrow(() -> new ExerciseNotFoundException(id));
                exercises.add(exercise);
            }

            workoutDays.add(workoutDay);

        }
        client.getWorkouts().add(workout);
        repo.save(workout);
    }

    @Transactional
    public void deleteWorkout(Long id, String email) {
        Client client = clientRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        Workout workoutToDelete = client.getWorkouts()
                .stream()
                .filter(workout -> workout.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Workout not found"));
        client.getWorkouts().remove(workoutToDelete);
        repo.delete(workoutToDelete);
    }

    public long countWorkout() {
        return repo.count();
    }

//    public void deleteAllWorkout() {
//
//        if (countWorkout() == 0) {
//            throw new RuntimeException("No workout to be deleted");
//        }
//        repo.deleteAll();
//    }

    /*
    private void validateWorkout(Workout workout) {
        if (workout == null) {
            throw new RuntimeException("Workout cannot be null");
        }

        if (workout.getName() == null || workout.getName().isBlank()) {
            throw new RuntimeException("Workout name is required");
        }

        if (workout.getName().length() > 100) {
            throw new RuntimeException("Workout name must be under 100 characters");
        }

        if (workout.getDescription() != null && workout.getDescription().length() > 2000) {
            throw new RuntimeException("Workout description must be under 2000 characters");
        }
    }
     */
}
