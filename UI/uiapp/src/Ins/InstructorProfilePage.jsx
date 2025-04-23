// src/InstructorProfilePage.jsx
import React from "react";
import "./InstructorProfilePage.css"; // Reusing the same CSS
import Navbar from "./NavbarINS";
import axios from "axios";
import { useState, useEffect } from "react";

const InstructorProfilePage = () => {
  const [showChangePasswordModal, setShowChangePasswordModal] = useState(false);
  const [insProfileInfo, setInsProfileInfo] = useState({courses: []});

  useEffect(() => {
    const fetchProfileInformation = async () => {
      try {
        const response = await axios.get("http://localhost:8080/instructor/profile?id=6");
        setInsProfileInfo(response.data);
        console.log(insProfileInfo);
      } catch (error) {
        console.error("Error fetching tasks:", error);
      }
    };
    fetchProfileInformation();
  }, []);


  return (
    <div className="profile-container">
      <Navbar />

      <div className="profile-content">
        {/* Info Section */}
        <div className="info-card">
          <h3>Personal Information</h3>
          <p><strong>Name</strong><br />{insProfileInfo.name}</p>
          <p><strong>Surname</strong><br />{insProfileInfo.surname}</p>
          <p><strong>Email</strong><br />{insProfileInfo.email}</p>
          <p><strong>ID</strong><br />{insProfileInfo.bilkentId}</p>
          <p><strong>Role</strong><br />{insProfileInfo.role}</p>
          <p><strong>Department</strong><br />{insProfileInfo.departmentName}</p>
          <p><strong>Course(s)</strong><br />{insProfileInfo.courses.map((courseName, index) => {
            console.log(courseName);
            return (
              <span key={index}>
                {courseName}{index < insProfileInfo.courses.length - 1 ? ", " : ""}
              </span>
            );
          })}</p>
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

export default InstructorProfilePage;
