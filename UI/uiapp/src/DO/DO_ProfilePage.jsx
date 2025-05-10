// src/DO_ProfilePage.jsx
import React from "react";
import "./DO_ProfilePage.css"; // Reusing the same CSS
import NavbarDO from "./NavbarDO";
import axios from "axios";
import { useState, useEffect, useRef } from "react";

/**
 * DO_ProfilePage component
 * Displays Dean’s Office profile information and provides a password change modal.
 */
const DO_ProfilePage = () => {
  // Controls the visibility of the password change modal
  const [showChangePasswordModal, setShowChangePasswordModal] = React.useState(false);
  const [doProfileInfo, setDoProfileInfo] = useState({ courses: [] });

  //Refs for password inputs
  const oldPasswordRef = useRef();
  const newPasswordRef = useRef();
  const confirmNewPasswordRef = useRef();
  //-----------------------------------------------------------

  /**
   * Sends request to update the DO's password.
   * Uses email from fetched profile data and validates new input.
   */
  const handleChangePassword = async () => {
    try {
      console.log("Email:", doProfileInfo.email);
      console.log("Old Password:", oldPasswordRef.current.value);
      console.log("New Password:", newPasswordRef.current.value);
      console.log("Confirm New Password:", confirmNewPasswordRef.current.value);
      const response = await axios.put("http://localhost:8080/auth/changePassword", {
        email: doProfileInfo.email,
        oldPassword: oldPasswordRef.current.value,
        newPassword: newPasswordRef.current.value,
        userTypeName: "deans office"
      });
      if (response.data) {
        alert("Password changed successfully.");
        setShowChangePasswordModal(false);
      } else {
        alert("Failed to change password. Please try again.");
      }
    } catch (error) {
      console.error("Error changing password:", error);
    }

  };

  // On mount: Fetch profile information for the logged-in Dean’s Office user
  useEffect(() => {
    const fetchProfileInformation = async () => {
      try {
        const token = localStorage.getItem("token");
        const response = await axios.get("http://localhost:8080/deansOffice/profile", {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        setDoProfileInfo(response.data);
        console.log(doProfileInfo);
      } catch (error) {
        console.error("Error fetching tasks:", error);
      }
    };
    fetchProfileInformation();
  }, []);


  return (
    <div className="profile-container">
      <NavbarDO />

      <div className="profile-content">
        {/* Info Section */}
        <div className="info-card">
          <h3>Personal Information</h3>
          <p><strong>Name</strong><br />{doProfileInfo.name}</p>
          <p><strong>Surname</strong><br />{doProfileInfo.surname}</p>
          <p><strong>Email</strong><br />{doProfileInfo.email}</p>
          <p><strong>ID</strong><br />{doProfileInfo.bilkentId}</p>
          <p><strong>Role</strong><br />{doProfileInfo.role}</p>
          <p><strong>Faculty</strong><br />{doProfileInfo.faculty}</p>
        </div>

        {/* Manage Account Section */}
        <div className="right-section">
          <div className="manage-card">
            <h3>Manage Account</h3>
            <button className="purple-button" onClick={() => setShowChangePasswordModal(true)}>Change Password</button>
          </div>
        </div>
      </div>

      {/* Change Password Modal */}
      {showChangePasswordModal && (
        <div className="modal-overlay">
          <div className="modal">
            <h3>Change Password</h3>
            <label>Old Password</label>
            <input ref={oldPasswordRef} type="password" placeholder="Enter your old password" />
            <label>New Password</label>
            <input ref={newPasswordRef} type="password" placeholder="At least 8 characters long" />
            <label>Confirm New Password</label>
            <input ref={confirmNewPasswordRef} type="password" placeholder="Confirm new password" />
            <div className="modal-buttons">
              <button className="cancel-button" onClick={() => setShowChangePasswordModal(false)}>Cancel</button>
              <button className="apply-button" onClick={() => {
                if (!oldPasswordRef.current.value || !newPasswordRef.current.value || !confirmNewPasswordRef.current.value) {
                  alert("Please fill in all fields.");
                  return;
                }
                if (newPasswordRef.current.value !== confirmNewPasswordRef.current.value) {
                  alert("New password and confirmation do not match.");
                  return;
                }
                if (newPasswordRef.current.value.length < 8) {
                  alert("New password must be at least 8 characters long.");
                  return;
                }
                handleChangePassword();
              }}>Apply</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default DO_ProfilePage;
