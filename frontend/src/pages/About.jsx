import "../assets/css/about.css"

export default function About() {
  return (
    <main className="about-page">
      <section className="about-hero">
        <h1>About Grind Hub</h1>
        <p>Everything you need for your fitness journey, all in one place.</p>
      </section>

      <section className="about-content">
        <div className="about-card">
          <h2>Why Grind Hub Exists</h2>
          <p>
            Modern life is busy. Between work, school, family, and personal
            responsibilities, staying healthy often becomes harder than it
            should be.
          </p>

          <p>
            Many people struggle not because they lack motivation, but because
            fitness requires managing too many separate tools. One app tracks
            workouts, another handles nutrition, another schedules appointments,
            and another provides health articles.
          </p>

          <p>
            Personal trainers can also be expensive, and fixed gym schedules do
            not work for everyone. Grind Hub was created to make fitness more
            organized, flexible, and easier to follow.
          </p>
        </div>

        <div className="about-card">
          <h2>Our Mission</h2>
          <p>
            Our mission is to bring coaching, workout planning, nutrition
            guidance, progress tracking, scheduling, and health education into
            one simple platform.
          </p>

          <p>
            Instead of spending time switching between different apps, users can
            focus on building healthier habits and reaching their goals.
          </p>
        </div>

        <div className="about-card">
          <h2>Fitness With Freedom</h2>
          <p>
            Grind Hub gives users access to coach-guided workout planning
            without unnecessary micromanagement. Coaches can help create the
            right plan, while users still have the freedom to train on their own
            schedule.
          </p>

          <p>
            With proper technique, structured plans, and useful resources, Grind
            Hub helps users train smarter, stay consistent, and make real
            progress.
          </p>
        </div>
      </section>
    </main>
  );
}
