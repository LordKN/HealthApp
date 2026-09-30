export default function WorkoutHeader({ onSave, onCancel }) {
  return (
    <div className="workout-header">
      <div>
        <h1>Create Workout</h1>
        <p>Give your workout a name and at exercises to each day</p>
      </div>

      <div>
        <button className="cancel-button" onClick={onCancel}>
          Cancel
        </button>
        <button className="save-workout-button" onClick={onSave}>
          Save Workout
        </button>
      </div>
    </div>
  );
}
