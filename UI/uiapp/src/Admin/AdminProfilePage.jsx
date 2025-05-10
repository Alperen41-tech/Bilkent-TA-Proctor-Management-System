// src/InstructorProfilePage.jsx
import React from "react";
import "./AdminProfilePage.css"; // Reusing the same CSS
import NavbarAdmin from "./NavbarAdmin";
import { useState, useEffect, useRef } from "react";
import axios from "axios";

const AdminProfilePage = () => {
  const [showChangePasswordModal, setShowChangePasswordModal] = React.useState(false);
  const [adminProfileInfo, setAdminProfileInfo] = useState({ courses: [] }); // tbchanged for admin

  //Refs for password inputs
  const oldPasswordRef = useRef();
  const newPasswordRef = useRef();
  const confirmNewPasswordRef = useRef();
  //-----------------------------------------------------------


  const handleChangePassword = async () => {
    try {

      const response = await axios.put("http://localhost:8080/auth/changePassword", {
        email: adminProfileInfo.email,
        oldPassword: oldPasswordRef.current.value,
        newPassword: newPasswordRef.current.value,
        userTypeName: "admin",
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


  useEffect(() => {
    const fetchProfileInformation = async () => {
      try {
        const token = localStorage.getItem("token");
        const response = await axios.get("http://localhost:8080/admin/getAdminProfile", {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        setAdminProfileInfo(response.data);
        console.log(adminProfileInfo);
      } catch (error) {
        console.error("Error fetching tasks:", error);
      }
    };
    fetchProfileInformation();
  }, []);

  return (
    <div className="profile-container">
      <NavbarAdmin />

      <div className="profile-content">
        {/* Info Section */}
        <div className="info-card">
          <h3>Personal Information</h3>
          <p><strong>Name</strong><br />{adminProfileInfo.name}</p>
          <p><strong>Surname</strong><br />{adminProfileInfo.surname}</p>
          <p><strong>Email</strong><br />{adminProfileInfo.email}</p>
          <p><strong>ID</strong><br />{adminProfileInfo.bilkentId}</p>
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

export default AdminProfilePage;
