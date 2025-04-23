// src/InstructorProfilePage.jsx
import React from "react";
import "./DO_ProfilePage.css"; // Reusing the same CSS
import NavbarDO from "./NavbarDO";
import axios from "axios";
import { useState, useEffect } from "react";

const DO_ProfilePage = () => {
  const [showChangePasswordModal, setShowChangePasswordModal] = React.useState(false);
  const [doProfileInfo, setDoProfileInfo] = useState({courses: []});

  useEffect(() => {
    const fetchProfileInformation = async () => {
      try {
        const response = await axios.get("http://localhost:8080/deansOffice/profile?id=9");
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

export default DO_ProfilePage;
