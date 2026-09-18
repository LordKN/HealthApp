import { useAuth } from "../../context/AuthContext.jsx";
import {
    getClients, getCoaches, refreshAccessToken
} from "../../services/api.js";

export default function AdminDashboard() {
    const {
        accessToken, setAccessToken, setRole
    } = useAuth();

    async function loadClients() {
        try {
            let response = await getClients(accessToken);

            if (response.status === 401) {
                const data = await refreshAccessToken();
                setAccessToken(data.token);
                setRole(data.role);

                // Retry the request with the new access token
                response = await getClients(data.token);
            }

            if (!response.ok) {
                throw new Error("Failed to fetch clients");
            }

            const clients = await response.json();
            console.log("Clients: ", clients);
        } catch (error) {
            console.error("Error loading clients: ", error);
        }
    }

    async function loadCoaches() {
        try {
            let response = await getCoaches(accessToken);

            if (response.status === 401) {
                const data = await refreshAccessToken();
                setAccessToken(data.token);
                setRole(data.role);

                // Retry the request with the new access token
                response = await getCoaches(data.token);
            }

            if (!response.ok) {
                throw new Error("Failed to fetch coaches");
            }

            const coaches = await response.json();
            console.log("Coaches: ", coaches);
        } catch (error) {
            console.error("Error loading coaches: ", error);
        }
    }

    return (
        <div>
            <p><b>Hello from AdminDashboard</b></p>

            <button onClick={loadClients}>Load clients</button>
            <button onClick={loadCoaches}>Load coaches</button>
        </div>
    )
}