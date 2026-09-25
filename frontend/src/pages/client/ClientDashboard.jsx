import Sidebar from "./Sidebar.jsx";
import "../../assets/css/ClientDashboard.css";
import { useState } from "react";
import { useEffect } from "react";
import { getCurrentClient } from "../../services/api.js";
import { useAuth } from "../../context/AuthContext.jsx";

export default function ClientDashboard() {
  const [client, setClient] = useState(null);
  const { accessToken } = useAuth();

  useEffect(
    () => {
      async function loadClient() {
        try {
          const response = await getCurrentClient(accessToken);

          if (!response.ok) {
            throw new Error("Failed to load client");
          }

          const data = await response.json();
          setClient(data);
        } catch (error) {
          console.error(error);
        }
      }

      loadClient();
    },
    [accessToken] /* Besides running when the component mounts, it will
                    also run whenever the accessToken changes */,
  );

  return (
    <div className="client-layout">
      <Sidebar />

      <main className="dashboard-content">
        <h1>Good morning, {client?.name}!</h1>
        <p>Keep going! Small steps lead to big results.</p>
      </main>
    </div>
  );
}
