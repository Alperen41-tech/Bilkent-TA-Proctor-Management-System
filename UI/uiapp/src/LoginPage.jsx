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
  const [isAdmin, setIsAdmin] = React.useState(false);

  
  const handleLogin = async () => {
    try {
      console.log(isAdmin);
      console.log("Email:", email);
      console.log("Password:", password);
      // Make the API call to the Spring Boot backend
      const response = await axios.post("http://localhost:8080/auth/login", {
        email: email,
        password: password, 
        userTypeName: isAdmin ? "admin" : null,
      });

      if (response.data) {
        switch(response.data.userTypeName) {
          case "admin":
            navigate("/admin-database");
            break;
          case "ta":
            navigate("/dashboard");
            break;
          case "instructor":
            navigate("/ins-dashboard");
            break;
          case "deans office":
            navigate("/do-dashboard");
            break;
          case "department secretary":
            navigate("/ds-dashboard");
            break;
          default:
            console.error("Unknown user type:", response.data.userTypeName);
        }
        console.log(response.data.token);
        localStorage.setItem("token", response.data.token);
      } else {
        // Handle unsuccessful login (e.g., show an error message)
        alert("Invalid credentials. Please try again.");
      }
    } catch (error) {
      console.error("There was an error with the login request:", error);
      if (error.response.data.message) {
        alert(error.response.data.message);
      }
      else{
        alert("An error occurred. Please try again.");
      }
    }
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
            <div className="login-page-constaint-checkboxes">
              <div className="is-admin-checkbox">
                <input
                  type="checkbox"
                  id="isAdmin"
                  name="isAdmin"
                  checked={isAdmin}
                  onChange={(e) => setIsAdmin(e.target.checked)}
                />
                <label htmlFor="isAdmin">Is admin?</label>

                
              </div>
              <div className="forgot-password">
                <a href="#" onClick={() => setShowForgotPasswordModal(true)}>Forgot password?</a>
              </div>
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