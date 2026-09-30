const API_BASE_URL = "http://localhost:8080/api";

export async function getAllExercises(accessToken, page, size) {
  const response = await fetch(
    `${API_BASE_URL}/exercises?page=${page}&size=${size}`,
    {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    },
  );

  if (!response.ok) {
    throw new Error("Failed to fetch exercises");
  }

  return response.json();
}

export async function createWorkout(accessToken, workout) {
  const response = await fetch(`${API_BASE_URL}/workouts`, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${accessToken}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(workout),
  });

  if (!response.ok) {
    throw new Error("Failed to save workout");
  }
}
