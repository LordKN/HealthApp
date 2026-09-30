import WorkoutDays from "../../components/workout/WorkoutDays.jsx";
import WorkoutDetails from "../../components/workout/WorkoutDetails.jsx";
import WorkoutHeader from "../../components/workout/WorkoutHeader.jsx";
import { useState, useEffect } from "react";
import "../../assets/css/CreateWorkout.css";
import { useAuth } from "../../context/AuthContext.jsx";
import ExercisePicker from "../../components/workout/ExercisePicker.jsx";
import { createWorkout, getAllExercises } from "../../services/exerciseApi.js";
import SideBar from "./Sidebar.jsx";

export default function CreateWorkoutPage() {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [exercises, setExercises] = useState([]);
  const { accessToken } = useAuth();
  const [exercisePage, setExercisePage] = useState(0);
  const [totalExercisePages, setTotalExercisePages] = useState(0);
  const [selectedDay, setSelectedDay] = useState(1);
  const [expandedDay, setExpandedDay] = useState(null);
  const [days, setDays] = useState([
    { dayNumber: 1, name: "", exercises: [] },
    { dayNumber: 2, name: "", exercises: [] },
    { dayNumber: 3, name: "", exercises: [] },
    { dayNumber: 4, name: "", exercises: [] },
    { dayNumber: 5, name: "", exercises: [] },
    { dayNumber: 6, name: "", exercises: [] },
    { dayNumber: 7, name: "", exercises: [] },
  ]);

  useEffect(() => {
    async function loadExercises() {
      try {
        const data = await getAllExercises(accessToken, exercisePage, 4);
        setExercises(data.content);
        setTotalExercisePages(data.totalPages);
        console.log("Exercise response:", data);
      } catch (error) {
        console.error("Failed to load exercises", error);
      }
    }
    loadExercises();
  }, [accessToken, exercisePage]);

  function handleExpandDay(dayNumber) {
    setExpandedDay(expandedDay === dayNumber ? null : dayNumber);
  }

  async function handleSave() {
    const workoutDays = days.map((day) => ({
      dayNumber: day.dayNumber,
      name: day.name,
      exerciseIds: day.exercises.map((exercise) => exercise.id),
    }));

    const workout = {
      name: name,
      description: description,
      days: workoutDays,
    };

    await createWorkout(accessToken, workout);
    console.log("Workout saved successfully");
  }

  function handleCancel() {}

  function handleNameChange(event) {
    setName(event.target.value);
  }

  function handleDescriptionChange(event) {
    setDescription(event.target.value);
  }

  function handleDayNameChange(dayNumber, newName) {
    setDays(
      days.map((day) =>
        day.dayNumber === dayNumber ? { ...day, name: newName } : day,
      ),
    );
  }

  function handleAddExercise(exercise) {
    console.log("Adding exercise:", exercise);
    setDays(
      days.map((day) =>
        day.dayNumber === selectedDay
          ? {
              ...day,
              exercises: day.exercises.some(
                (currentExercise) => currentExercise.id === exercise.id,
              )
                ? day.exercises
                : [...day.exercises, exercise],
            }
          : day,
      ),
    );
  }

  return (
    <div className="client-layout">
      <SideBar />
      <main className="create-workout-page">
        <WorkoutHeader onSave={handleSave} onCancel={handleCancel} />
        <WorkoutDetails
          name={name}
          description={description}
          onNameChange={handleNameChange}
          onDescriptionChange={handleDescriptionChange}
        />
        <div className="workout-builder">
          <WorkoutDays
            days={days}
            onDayNameChange={handleDayNameChange}
            selectedDay={selectedDay}
            onDaySelect={setSelectedDay}
            onDayExpand={handleExpandDay}
            expandedDay={expandedDay}
          />
          <ExercisePicker
            exercises={exercises}
            totalPages={totalExercisePages}
            onPageChange={setExercisePage}
            onAddExercise={handleAddExercise}
            page={exercisePage}
          />
        </div>
      </main>
    </div>
  );
}
