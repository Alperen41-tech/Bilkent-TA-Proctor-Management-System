import React from "react";
import { useNavigate } from "react-router-dom";
import "./LoginPage.css";
import axios from "axios";

const LoginPage = () => {
  const navigate = useNavigate();
  const [showForgotPasswordModal, setShowForgotPasswordModal] = React.useState(false);

  //For storing email and password
  const [email, setEmail] = React.useState("");
  const [password, setPassword] = React.useState("");

  
  const handleLogin = async () => {
    navigate("admin-database"); // Change this to the appropriate page for your role
    //navigate("ds-dashboard"); // Change this to the appropriate page for your role
    /*
    try {

      console.log("Email:", email);
      console.log("Password:", password);
      // Make the API call to the Spring Boot backend
      const response = await axios.post("http://localhost:8080/api/login", {
        email: email,      // User input for email
        password: password,  // User input for password
      });

      // Check if the login was successful based on the response
      if (response.data) { // Assuming the response is a boolean or an object
        // If login is successful, navigate to the desired page
        navigate("/ins-dashboard"); // Change this to the appropriate page for your role
      } else {
        // Handle unsuccessful login (e.g., show an error message)
        alert("Invalid credentials. Please try again.");
      }
    } catch (error) {
      console.error("There was an error with the login request:", error);
      alert("An error occurred. Please try again.");
    }*/
  };

  return (
      <div className="login-container">
        <div className="logo-section">
          <img src="BilkentLogo.jpg" alt="Bilkent Logo" className="bilkent-logo" />
        </div>
        <div className="form-section">
          <div className="login-box">
            <h2>Sign in</h2>
            <label>Email</label>
            <input
                type="email"
                placeholder="example.email@bilkent.edu.tr"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
            />
            <label>Password</label>
            <input
                type="password"
                placeholder="Enter your password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <div className="forgot-password">
              <a href="#" onClick={() => setShowForgotPasswordModal(true)}>Forgot password?</a>
            </div>
            <button onClick={handleLogin}>Login</button>
          </div>
        </div>
        {showForgotPasswordModal && (
            <div className="modal-overlay">
              <div className="modal">
                <h3>Reset Password</h3>
                <label>Email</label>
                <input type="email" placeholder="Enter your email" />
                <div className="modal-buttons">
                  <button className="cancel-button" onClick ={() => setShowForgotPasswordModal(false)}>Cancel</button>
                  <button className="apply-button" onClick={() => setShowForgotPasswordModal(false)}>Send New Password</button>
                </div>
              </div>
            </div>
        )}

      </div>
  );
};

export default LoginPage;