// src/InstructorProfilePage.jsx
import React from "react";
import "./DO_ProfilePage.css"; // Reusing the same CSS
import NavbarDO from "./NavbarDO";

const DO_ProfilePage = () => {
  const [showChangePasswordModal, setShowChangePasswordModal] = React.useState(false);

  return (
    <div className="profile-container">
      <NavbarDO />

      <div className="profile-content">
        {/* Info Section */}
        <div className="info-card">
          <h3>Personal Information</h3>
          <p><strong>Name</strong><br />Mehmet Fatih</p>
          <p><strong>Surname</strong><br />Sultan</p>
          <p><strong>Email</strong><br />ahmet.yilmaz@bilkent.edu.tr</p>
          <p><strong>ID</strong><br />34346543</p>
          <p><strong>Role</strong><br />Deans Office</p>
          <p><strong>Department</strong><br />Computer Science</p>
          <p><strong>Course(s)</strong><br />CS 202, CS 898</p>
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
