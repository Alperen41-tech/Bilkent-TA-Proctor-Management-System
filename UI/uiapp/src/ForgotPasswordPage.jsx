import React from "react";
import { useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import "./ForgotPasswordPage.css";
import axios from "axios";
import { useSearchParams } from 'react-router-dom';

const ForgotPasswordPage = () => {
    const newPasswordRef = useRef();
    const confirmNewPasswordRef = useRef();
    const [searchParams] = useSearchParams();
    const token = searchParams.get("token");

  const handleChangePassword = async () => {
    try {
      const response = await axios.put(`http://localhost:8080/auth/setAfterForgetPassword?token=${token}&newPassword=${newPasswordRef.current.value}`);
      if (response.data) {
        alert("Password changed successfully.");
      } else {
        alert("Failed to change password. Please try again.");
      }
    } catch (error) {
      console.error("Error changing password:", error);
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
          
            <h3>Change Password</h3>
            <label>New Password</label>
            <input ref={newPasswordRef} type="password" placeholder="At least 8 characters long" />
            <label>Confirm New Password</label>
            <input ref={confirmNewPasswordRef} type="password" placeholder="Confirm new password" />
            <div className="modal-buttons">
              <button className="apply-button" onClick={() => {
                if(!newPasswordRef.current.value || !confirmNewPasswordRef.current.value) {
                  alert("Please fill in all fields.");
                  return;
                }
                if(newPasswordRef.current.value !== confirmNewPasswordRef.current.value) {
                  alert("New password and confirmation do not match.");
                  return;
                }
                if(newPasswordRef.current.value.length < 8) {
                  alert("New password must be at least 8 characters long.");
                  return;
                }
                handleChangePassword();
              }}>Apply</button>
          </div>
        </div>
        </div>
      </div>
  );
};

export default ForgotPasswordPage;