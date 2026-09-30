export default function ExercisePicker({
  exercises,
  page,
  totalPages,
  onPageChange,
  onAddExercise,
}) {
  return (
    <section className="exercise-picker">
      <h2>Add Exercise</h2>

      <input
        className="exercise-search"
        type="text"
        placeholder="Search exercises by name..."
      />

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
