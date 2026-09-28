export default function ExerciseCard({ exercise }) {
  return (
    <div className="exercise-card">
      <div className="exercise-image-container">
        {exercise.exerciseImageUrl ? (
          <img
            className="exercise-image"
            src={exercise.exerciseImageUrl}
            alt={exercise.name}
          />
        ) : (
          <div className="exercise-no-image">No image available</div>
        )}
      </div>

      <div className="exercise-card-info">
        <h3>{exercise.name}</h3>

        <p className="exercise-category">{exercise.category}</p>

        <div className="exercise-muscles">
          {exercise.primaryMuscles.map((muscle) => (
            <span key={muscle}>{muscle}</span>
          ))}
        </div>
      </div>
    </div>
  );
}
