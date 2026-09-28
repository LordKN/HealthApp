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
