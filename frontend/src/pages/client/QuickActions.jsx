import { Link } from "react-router-dom";

export default function QuickActions({ icon, title, description, to }) {
  return (
    <Link to={to} className="quick-action">
      <div className="quick-action-icon">{icon}</div>

      <div className="quick-action-info">
        <h3>{title}</h3>
        <p>{description}</p>
      </div>

      <span className="quick-action-arrow">›</span>
    </Link>
  );
}
