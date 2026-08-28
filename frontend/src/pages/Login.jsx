import { useState } from "react";
import "../assets/css/login.css";
import Navbar from "../components/Navbar";
import Footer from "../components/Footer";
import { loginUser } from "../services/api.js";

export default function Login() {
  const [formData, setFormData] = useState({
    username: "",
    password: ""
  })

  function handleInputChange(e) {
    setFormData({
    ...formData,
    [e.target.id]: e.target.value
    })
  }

  async function handleSubmit(e) {
    e.preventDefault();
    console.log("Form submitted:", formData);
    try {
      await loginUser(formData);
    }
    catch (error) {
      console.error("Error logging in:", error);
    }
  }

  return (
    <>
      <Navbar/>
      <main className="login-page">
      <div className="login-card">
        <h1>Welcome Back</h1>

        <p className="subtitle">Login to continue your fitness journey.</p>

        <form className = "log-in-container" onSubmit ={handleSubmit} >
          <div className="form-group">
            <label htmlFor="username">Username</label>

            <input
              type="text"
              id="username"
              name="username"
              placeholder="Enter email"
              onChange = {handleInputChange}
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
              onChange = {handleInputChange}
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
      <Footer/>
    </>
  );
}
