import "../assets/css/login.css";

export default function Login() {
  return (
    <main className="login-page">
      <div className="login-card">
        <h1>Welcome Back</h1>

        <p className="subtitle">Login to continue your fitness journey.</p>

        <form>
          <div className="form-group">
            <label htmlFor="username">Username</label>

            <input
              type="text"
              id="username"
              name="username"
              placeholder="Enter username"
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="password">Password</label>

            <input
              type="password"
              id="password"
              name="password"
              placeholder="Enter password"
              required
            />
          </div>

          <button type="submit" className="login-btn">
            Login
          </button>
        </form>

        <div className="account-links">
          <a href="/forgot-password">Forgot Password?</a>

          <a href="/signup">Create Account</a>
        </div>
      </div>
    </main>
  );
}
