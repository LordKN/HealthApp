const API_BASE_URL = "http://localhost:8080/api";

export async function getExercises(
  accessToken,
  page,
  name,
  categoryId,
  muscleId,
  equipmentId,
) {
  const params = new URLSearchParams({
    page,
  });

  if (name.trim()) {
    params.set("name", name.trim());
  }

  if (categoryId !== "") {
    params.set("categoryId", categoryId);
  }

  if (muscleId !== "") {
    params.set("muscleId", muscleId);
  }

  if (equipmentId !== "") {
    params.set("equipmentId", equipmentId);
  }

  const response = await fetch(`${API_BASE_URL}/exercises/search?${params}`, {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });

  if (!response.ok) {
    throw new Error("Failed to fetch exercises by name");
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
