export default function WorkoutDays({
  days,
  onDayNameChange,
  selectedDay,
  onDaySelect,
  onDayExpand,
  expandedDay,
}) {
  return (
    <section className="workout-days">
      <h2>Workout Days</h2>
      <p>Plan your week and choose what you want to train each day</p>

      <div className="workout-days-list">
        {days.map((day) => (
          <div
            className={`workout-day ${
              selectedDay === day.dayNumber ? "selected" : ""
            }`}
            key={day.dayNumber}
          >
            <div
              className="workout-day-header"
              onClick={() => onDaySelect(day.dayNumber)}
            >
              <span className="day-number">Day {day.dayNumber}</span>

              <input
                className="day-name-input"
                type="text"
                value={day.name}
                placeholder="Rest"
                onChange={(e) => onDayNameChange(day.dayNumber, e.target.value)}
                onClick={(e) => e.stopPropagation()}
              />

              <span className="exercise-count">
                {day.exercises.length} exercises
              </span>

              <button
                type="button"
                className="day-expand-button"
                onClick={(e) => {
                  e.stopPropagation();
                  onDayExpand(day.dayNumber);
                }}
              >
                {expandedDay === day.dayNumber ? "▲" : "▼"}
              </button>
            </div>

            {expandedDay === day.dayNumber && (
              <div className="day-exercises">
                {day.exercises.map((exercise) => (
                  <div key={exercise.id} className="day-exercise">
                    {exercise.exerciseImageUrl && (
                      <img
                        src={exercise.exerciseImageUrl}
                        alt={exercise.name}
                      />
                    )}

                    <div className="day-exercise-info">
                      <h4>{exercise.name}</h4>

                      <div className="day-exercise-muscles">
                        {exercise.primaryMuscles.map((muscle) => (
                          <span key={muscle}>{muscle}</span>
                        ))}
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>
        ))}
      </div>
    </section>
  );
}
