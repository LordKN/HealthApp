export default function WorkoutDetails({
  name,
  description,
  onNameChange,
  onDescriptionChange,
}) {
  return (
    <div className="workout-details">
      <div className="workout-field">
        <h3>Workout Details</h3>

        {/* When clicking the Workout Name field, the cursor should focus on it */}
        <label htmlFor="workout-name">Workout Name</label>

        <input
          id="workout-name"
          type="text"
          value={name}
          onChange={onNameChange}
        />
      </div>
      <div className="workout-field">
        {/* When clicking the Description field, the cursor should focus on it */}
        <label htmlFor="workout-description">Description (optional)</label>

        <textarea
          id="workout-description"
          value={description}
          onChange={onDescriptionChange}
        />
      </div>
    </div>
  );
}
