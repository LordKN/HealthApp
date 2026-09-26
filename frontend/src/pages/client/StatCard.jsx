export default function StatCard({ icon, label, value }) {
  return (
    <div className="stat-card">
      <div className="stat-icon">{icon}</div>

      <div className="stat-info">
        <p className="stat-label">{label}</p>
        <h2 className="stat-value">{value}</h2>
      </div>
    </div>
  );
}
