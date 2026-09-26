import Sidebar from "./Sidebar.jsx";
import "../../assets/css/ClientDashboard.css";
import { useState } from "react";
import { useEffect } from "react";
import { getCurrentClient } from "../../services/api.js";
import { useAuth } from "../../context/AuthContext.jsx";
import StatCard from "./StatCard.jsx";

function formatEnum(value) {
  if (!value) return "Not set";

  return value
    .replace(/([a-z])([A-Z])/g, "$1 $2")
    .replace(/[_-]+/g, " ")
    .replace(/\b\w/g, (character) => character.toUpperCase());
}

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
        <div className="client-stats">
          <StatCard
            icon="🎯"
            label="Your Goal"
            value={formatEnum(client?.fitnessGoal)}
          />

          <StatCard
            icon="⚖️"
            label="Current Weight"
            value={client?.weight ? `${client.weight} lb` : "Not set"}
          />

          <StatCard
            icon="📏"
            label="Height"
            value={client?.height ? `${client.height} cm` : "Not set"}
          />

          <StatCard
            icon="🏃"
            label="Activity Level"
            value={formatEnum(client?.activityLevel)}
          />
        </div>
      </main>
    </div>
  );
}
