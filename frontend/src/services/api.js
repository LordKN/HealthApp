const API_BASE_URL = "http://localhost:8080/api";

//CLIENT
export async function getClients(accessToken) {
  const response = await fetch(`${API_BASE_URL}/clients`, {
    headers: {
      Authorization: `Bearer ${accessToken}`, // Include the access token in the Authorization bearer header
    },
  });
  return response;
}

export async function getCurrentClient(accessToken) {
  const response = await fetch(`${API_BASE_URL}/clients/me`, {
    headers: {
      Authorization: `Bearer ${accessToken}`, // Include the access token in the Authorization bearer header
    },
  });

  if (!response.ok) {
    throw new Error("Failed to fetch current user");
  }
  return response;
}

export async function createClient(client) {
  //Send a HTTP POST request to /api/clients
  const response = await fetch(`${API_BASE_URL}/auth/clients`, {
    method: "POST",
    headers: {
      //Set the content type to JSON so Spring can know how to parse the request body
      //Content type can be set to application/json, application/xml, text/plain, etc.
      "Content-Type": "application/json",
    },
    //Convert the client object to a JSON string and send it in the request body
    body: JSON.stringify(client),
  });
  return response.json();
}

//COACH
export async function getCoaches(accessToken) {
  const response = await fetch(`${API_BASE_URL}/coaches`, {
    headers: {
      Authorization: `Bearer ${accessToken}`, // Include the access token in the Authorization bearer header
    },
  });
  return response;
}

export async function createCoach(coach) {
  const response = await fetch(`${API_BASE_URL}/auth/coaches`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(coach),
  });
  return response.json();
}

//Login
export async function loginUser(credentials) {
  const response = await fetch(`${API_BASE_URL}/auth/login`, {
    method: "POST",
    credentials: "include", // Include cookies in the request
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(credentials), // Convert the credentials object to a JSON string and send it in the request body
  });

  if (!response.ok) {
    throw new Error("Failed to login");
  }
  return response.json();
}

//Refresh
export async function refreshAccessToken() {
  const response = await fetch(`${API_BASE_URL}/auth/refresh`, {
    method: "POST",
    credentials: "include", // Include cookies in the request
  });

  if (!response.ok) {
    throw new Error("Failed to refresh access token");
  }

  return response.json();
}

//Logout
export async function logoutUser() {
  const response = await fetch(`${API_BASE_URL}/auth/logout`, {
    method: "POST",
    credentials: "include", // Include cookies in the request so Spring boot can revoke refresh session and clear refresh cookie
  });

  if (!response.ok) {
    throw new Error("Failed to logout");
  }
}
