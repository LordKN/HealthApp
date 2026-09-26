import morningImage from "../assets/images/morning.png";
import afternoonImage from "../assets/images/afternoon.png";
import eveningImage from "../assets/images/evening.png";
import nightImage from "../assets/images/night.png";

export default function DaytimeBanner({ client }) {
  const hour = new Date().getHours();

  let greeting;
  let image;
  if (hour >= 5 && hour < 12) {
    greeting = "Good morning";
    image = morningImage;
  } else if (hour >= 12 && hour < 17) {
    greeting = "Good afternoon";
    image = afternoonImage;
  } else if (hour >= 17 && hour < 20) {
    greeting = "Good evening";
    image = eveningImage;
  } else {
    greeting = "Good night";
    image = nightImage;
  }

  return (
    <div className="dashboard-header">
      <div className="dashboard-greeting">
        <h1>
          {greeting}, {client?.name}!
        </h1>
        <p>Keep going! Small steps lead to big results.</p>
      </div>

      <img className="day-time-image" src={image} alt="" />
    </div>
  );
}
