export default function ExercisePicker({
  exercises,
  page,
  totalPages,
  onPageChange,
  onAddExercise,
  nameSearch,
  categoryId,
  muscleId,
  equipmentId,
  onSearchChange,
  onCategoryChange,
  onMuscleChange,
  onEquipmentChange,
}) {
  return (
    <section className="exercise-picker">
      <h2>Add Exercise</h2>

      <input
        className="exercise-search"
        type="text"
        placeholder="Search exercises by name..."
        value={nameSearch}
        onChange={(e) => onSearchChange(e.target.value)}
      />

      <select
        value={categoryId}
        onChange={(e) => onCategoryChange(e.target.value)}
      >
        <option value="">All Categories</option>
        <option value="1">Abs</option>
        <option value="2">Arms</option>
        <option value="3">Back</option>
        <option value="4">Calves</option>
        <option value="5">Cardio</option>
        <option value="6">Chest</option>
        <option value="7">Legs</option>
        <option value="8">Shoulders</option>
      </select>

      <select value={muscleId} onChange={(e) => onMuscleChange(e.target.value)}>
        <option value="">All Muscles</option>
        <option value="1">Front shoulder</option>
        <option value="2">Biceps</option>
        <option value="3">Hamstrings</option>
        <option value="4">Upper arm</option>
        <option value="5">Calf muscle</option>
        <option value="6">Glutes</option>
        <option value="7">Lats</option>
        <option value="8">Side abs</option>
        <option value="9">Chest</option>
        <option value="10">Quads</option>
        <option value="11">Abs</option>
        <option value="12">Side ribs</option>
        <option value="13">Deep calf</option>
        <option value="14">Traps</option>
        <option value="15">Triceps</option>
      </select>

      <select
        value={equipmentId}
        onChange={(e) => onEquipmentChange(e.target.value)}
      >
        <option value="">All Equipments</option>
        <option value="1">Barbell</option>
        <option value="2">Bench</option>
        <option value="3">Cable machine</option>
        <option value="4">Dumbell</option>
        <option value="5">Gym mat</option>
        <option value="6">Incline bench</option>
        <option value="7">Kettlebell</option>
        <option value="8">Pull-up bar</option>
        <option value="9">Resistance band</option>
        <option value="10">SZ-Bar</option>
        <option value="11">Swiss Ball</option>
        <option value="12">Body weight</option>
      </select>

      <div className="exercise-picker-pagination">
        <button
          type="button"
          disabled={page === 0}
          onClick={() => onPageChange(page - 1)}
        >
          Previous
        </button>
        <span>
          Page {page + 1} of {totalPages}
        </span>
        <button
          type="button"
          disabled={page === totalPages - 1}
          onClick={() => onPageChange(page + 1)}
        >
          Next
        </button>
      </div>

      <div className="exercise-picker-list">
        {exercises.map((exercise) => (
          <div className="exercise-picker-item" key={exercise.id}>
            {exercise.exerciseImageUrl && (
              <img src={exercise.exerciseImageUrl} alt={exercise.name} />
            )}

            <div className="exercise-picker-info">
              <h3>{exercise.name}</h3>

              <div className="exercise-picker-muscles">
                {exercise.primaryMuscles.map((muscle) => (
                  <span key={muscle}>{muscle}</span>
                ))}
              </div>
            </div>

            <button type="button" onClick={() => onAddExercise(exercise)}>
              + Add
            </button>
          </div>
        ))}
      </div>
    </section>
  );
}
