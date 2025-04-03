import React from "react";
import { useNavigate } from "react-router-dom";
import "./LoginPage.css";

const LoginPage = () => {
  const navigate = useNavigate();
  const [showForgotPasswordModal, setShowForgotPasswordModal] = React.useState(false);
  const handleLogin = () => {
    // you can add validation later
    //navigate("/dashboard");   //ENABLE FOR TA
    //navigate("/ins-dashboard");
    //navigate("/admin-database"); //ENABLE FOR ADMIN
    //navigate("/ds-dashboard"); //ENABLE FOR DS
    navigate("/do-profile"); 
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
          <input type="email" placeholder="example.email@bilkent.edu.tr" />
          <label>Password</label>
          <input type="password" placeholder="Enter your password" />
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