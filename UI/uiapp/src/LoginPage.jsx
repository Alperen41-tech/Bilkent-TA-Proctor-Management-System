import React from "react";
import { useNavigate } from "react-router-dom";
import "./LoginPage.css";
import axios from "axios";
import { is } from "date-fns/locale";
/**
 * LoginPage Component
 * This page handles login functionality for multiple user types (admin, TA assigner, general roles).
 * It also includes modal functionality for password recovery via email and user type selection.
 */
const LoginPage = () => {
  const navigate = useNavigate();
  const [showForgotPasswordModal, setShowForgotPasswordModal] = React.useState(false);

  //For storing email and password
  const [email, setEmail] = React.useState("");
  const [password, setPassword] = React.useState("");
  const [isAdmin, setIsAdmin] = React.useState(false);
  const [forgotPasswordMail, setIsForgotPasswordMail] = React.useState(false);
  const [isTAAssigner, setIsTAAssigner] = React.useState(false);
  
    /**
   * Handles the login process based on selected user type and credentials.
   */
  const handleLogin = async () => {
    try {
      console.log("Email:", email);
      console.log("Password:", password);
      console.log("isAdmin: " + isAdmin);
      console.log("isTAAssigner: " + isTAAssigner);
      // Make the API call to the Spring Boot backend
      const response = await axios.post("http://localhost:8080/auth/login", {
        email: email,
        password: password, 
        userTypeName: isAdmin ? "admin" : isTAAssigner ? "ta assigner" : "",
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
          case "ta assigner":
            navigate("/instructor-admin-profile");
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

    /**
   * Sends password reset email based on user type and email input.
   */
  const handleForgotPassword = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/auth/forgetPassword?userMail=${forgotPasswordMail}&userTypeName=${isAdmin ? "admin" : isTAAssigner ? "ta assigner" : ""}`);
      if (response.data) {
        alert("Password reset link sent to your email.");
      } else {
        alert("Failed to send password reset link. Please try again.");
      }
    } catch (error) {
      console.error("Error sending password reset link:", error);
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
                <div>
                  <input
                    type="checkbox"
                    id="isAdmin"
                    name="isAdmin"
                    checked={isAdmin}
                    onChange={(e) => {
                      setIsAdmin(e.target.checked);
                      if (e.target.checked) {
                        setIsTAAssigner(false);
                      }
                    }}
                  />
                  <label htmlFor="isAdmin">Is admin?</label>
                </div>
                <div>
                  <input
                    type="checkbox"
                    id="isTAAssigner"
                    name="isTAAssigner"
                    checked={isTAAssigner}
                    onChange={(e) => {
                      setIsTAAssigner(e.target.checked);
                      if (e.target.checked) {
                        setIsAdmin(false);
                      }
                    }}
                  />
                  <label htmlFor="isTAAssigner">Is TA assigner?</label>
                </div>
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
                <input type="email" placeholder="Enter your email" onChange={(e) => {setIsForgotPasswordMail(e.target.value)}}/>
                <div className="is-admin-checkbox">
                <div>
                  <input
                    type="checkbox"
                    id="isAdmin"
                    name="isAdmin"
                    checked={isAdmin}
                    onChange={(e) => {
                      setIsAdmin(e.target.checked);
                      if (e.target.checked) {
                        setIsTAAssigner(false);
                      }
                    }}
                  />
                  <label htmlFor="isAdmin">Is admin?</label>
                </div>
                <div>
                  <input
                    type="checkbox"
                    id="isTAAssigner"
                    name="isTAAssigner"
                    checked={isTAAssigner}
                    onChange={(e) => {
                      setIsTAAssigner(e.target.checked);
                      if (e.target.checked) {
                        setIsAdmin(false);
                      }
                    }}
                  />
                  <label htmlFor="isTAAssigner">Is TA assigner?</label>
                </div>
              </div>
                <div className="modal-buttons">
                  
                  <button className="cancel-button" onClick ={() => setShowForgotPasswordModal(false)}>Cancel</button>
                  <button className="apply-button" onClick={() => {setShowForgotPasswordModal(false); handleForgotPassword();} }>Change Password</button>
                </div>
              </div>
            </div>
        )}

      </div>
  );
};

export default LoginPage;