const API_BASE_URL = "http://localhost:8080/api";

//CLIENT
export async function getClients() {
  const response = await fetch(`${API_BASE_URL}/clients`);
  return response.json();
}

export async function createClient(client) {
  //Send a HTTP POST request to /api/clients
  const response = await fetch(`${API_BASE_URL}/clients`, {
    method: "POST",
    headers: {
      //Set the content type to JSON so Spring can know how to parse the request body
      //Content type can be set to application/json, application/xml, text/plain, etc.
      "Content-Type": "application/json",
    },
    //Convert the client object to a JSON string and send it in the request body
    body: JSON.stringify(client),
  });

  if (!response.ok) {
    const message = await response.text();
    throw new Error(message);
  }
  return response.json();
}

//COACH
export async function getCoaches() {
  const response = await fetch(`${API_BASE_URL}/coaches`);
  return response.json();
}

export async function createCoach(coach) {
  const response = await fetch(`${API_BASE_URL}/coaches`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(coach),
  });
  return response.json();
}
