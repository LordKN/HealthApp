import { Link } from "react-router-dom";
import { Navbar } from "../components/Navbar";
import { Footer } from "../components/Footer";

export default function Home() {
  return (
    <>
      <Navbar />
      <section className="hero">
        <div className="hero-content">
          <h1>Everything You Need For A Dream Body, All In One Place.</h1>

          <p id="Resources">
            Coaching, Nutrition Planning, Workout Plans, Progress Tracking, and
            Health resource library. We have everything you need to achieve your
            fitness goals, all in one place.
          </p>

          <Link to="/signup">
            <button className="start-btn">Get Started Here for Free!</button>
          </Link>
        </div>
      </section>

      <section className="quick-access">
        <h2>Quick Access</h2>

        <div className="card-container">
          <div className="card">
            <h3>Coaching</h3>
            <p>
              Get personalized coaching and support from our team of experts to
              help you achieve your fitness goals.
            </p>
          </div>

          <div className="card">
            <h3>Nutrition Planning</h3>
            <p>
              Create a customized nutrition plan that fits your lifestyle and
              helps you reach your fitness goals.
            </p>
          </div>

          <div className="card">
            <h3>Workout Plans</h3>
            <p>
              Access a variety of workout plans designed to help you build
              muscle, lose fat, and improve your overall fitness.
            </p>
          </div>

          <div className="card">
            <h3>Progress Tracking</h3>
            <p>
              Track your progress and see how far you've come with our
              easy-to-use progress tracking tools.
            </p>
          </div>

          <div className="card">
            <h3>Health Resource Library</h3>
            <p>
              Access a wealth of health resources, including articles, videos,
              and expert advice to help you stay informed and motivated on your
              fitness journey.
            </p>
          </div>
        </div>
      </section>

      <Footer />
    </>
  );
}
