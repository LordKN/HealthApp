import { useEffect, useState } from "react";
import { AuthContext } from "./AuthContext.jsx";
import { refreshAccessToken } from "../services/api.js";

export function AuthProvider({ children }) {
  const [accessToken, setAccessToken] = useState(null);
  const [role, setRole] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function restoreSession() {
      try {
        const data = await refreshAccessToken();
        setAccessToken(data.token);
        setRole(data.role);
      } catch (error) {
        setAccessToken(null);
        setRole(null);
        console.error("Error restoring session:", error);
      } finally {
        setLoading(false);
      }
    }
    restoreSession();
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <AuthContext.Provider
      value={{ accessToken, setAccessToken, role, setRole }}
    >
      {children}
    </AuthContext.Provider>
  );
}
