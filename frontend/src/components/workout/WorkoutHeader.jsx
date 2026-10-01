export default function WorkoutHeader({ onSave, onCancel, saving }) {
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
        <button
          disabled={saving}
          className="save-workout-button"
          onClick={onSave}
        >
          {saving ? "Saving..." : "Save Workout"}
        </button>
      </div>
    </div>
  );
}
