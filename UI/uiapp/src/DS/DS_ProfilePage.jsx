// src/InstructorProfilePage.jsx
import React from "react";
import "./DS_ProfilePage.css"; // Reusing the same CSS
import NavbarDS from "./NavbarDS";
import axios from "axios";
import { useState, useEffect } from "react";

const DSProfilePage = () => {
  const [showChangePasswordModal, setShowChangePasswordModal] = React.useState(false);
  const [dsProfileInfo, setDsProfileInfo] = useState({courses: []});

  useEffect(() => {
    const fetchProfileInformation = async () => {
      try {
        const response = await axios.get("http://localhost:8080/departmentSecretary/profile?id=7");
        setDsProfileInfo(response.data);
        console.log(dsProfileInfo);
      } catch (error) {
        console.error("Error fetching tasks:", error);
      }
    };
    fetchProfileInformation();
  }, []);


  return (
    <div className="profile-container">
      <NavbarDS />

      <div className="profile-content">
        {/* Info Section */}
        <div className="info-card">
          <h3>Personal Information</h3>
          <p><strong>Name</strong><br />{dsProfileInfo.name}</p>
          <p><strong>Surname</strong><br />{dsProfileInfo.surname}</p>
          <p><strong>Email</strong><br />{dsProfileInfo.email}</p>
          <p><strong>ID</strong><br />{dsProfileInfo.bilkentId}</p>
          <p><strong>Role</strong><br />{dsProfileInfo.role}</p>
          <p><strong>Department</strong><br />{dsProfileInfo.departmentName}</p>
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
            <input type="password" placeholder="Enter your old password" />
            <label>New Password</label>
            <input type="password" placeholder="At least 8 characters long" />
            <label>Confirm New Password</label>
            <input type="password" placeholder="Confirm new password" />
            <div className="modal-buttons">
              <button className="cancel-button" onClick={() => setShowChangePasswordModal(false)}>Cancel</button>
              <button className="apply-button">Apply</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default DSProfilePage;
