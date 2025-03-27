import React from "react";
import { useNavigate } from "react-router-dom";
import "./LoginPage.css";

const LoginPage = () => {
  const navigate = useNavigate();

  const handleLogin = () => {
    // you can add validation later
    navigate("/profile");
  };

  return (
    <div className="login-container">
      <div className="logo-section">
        <img src="logo.png" alt="Bilkent Logo" className="bilkent-logo" />
      </div>
      <div className="form-section">
        <div className="login-box">
          <h2>Sign in</h2>
          <label>Email</label>
          <input type="email" placeholder="example.email@bilkent.edu.tr" />
          <label>Password</label>
          <input type="password" placeholder="Enter your password" />
          <div className="forgot-password">
            <a href="#">Forgot password?</a>
          </div>
          <button onClick={handleLogin}>Login</button>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;