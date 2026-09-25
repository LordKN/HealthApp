import { NavLink, useNavigate } from "react-router-dom";
import { logoutUser } from "../../services/api.js";
import { useAuth } from "../../context/AuthContext.jsx";
import "../../assets/css/sidebar.css";
import { House, Dumbbell } from "lucide-react";

export default function Sidebar() {
  const navigate = useNavigate();
  const { setAccessToken, setRole } = useAuth();

  async function handleLogout() {
    try {
      await logoutUser();

      setAccessToken(null);
      setRole(null);

      navigate("/login");
    } catch (error) {
      console.error("Failed to logout", error);
    }
  }

  return (
    <aside className="sideboar">
      <div>
        <h1 className="sidebar-logo">
          <Dumbbell size={20} />
          Grind<span>Hub</span>
        </h1>

        <nav className="sidebar-nav">
          <NavLink to="/client/dashboard">
            <House size={15} />
            Dashboard
          </NavLink>

          <NavLink to="/client/exercises">Exercises</NavLink>

          <NavLink to="/client/workouts">Workouts</NavLink>

          <NavLink to="/client/profile">Profile</NavLink>
        </nav>
      </div>

      <button className="logout-button" onClick={handleLogout}>
        Logout
      </button>
    </aside>
  );
}
