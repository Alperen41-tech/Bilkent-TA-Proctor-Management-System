import React, { useEffect, useState } from "react";
import "./ProfilePage.css";
import { useNavigate } from "react-router-dom";
import Navbar from "./Navbar";
import axios from "axios";

const ProfilePage = () => {
  const navigate = useNavigate();
  const [showChangePasswordModal, setShowChangePasswordModal] = React.useState(false);
  const [showUnavailabilityModal, setShowUnavailabilityModal] = React.useState(false);
  const [taProfileInfo, setTaProfileInfo] = useState({}); // Initialize as an empty object");

  useEffect(() => {
    const fetchProfileInformation = async () => {
      try {
        const response = await axios.get("http://localhost:8080/ta/profile?id=2");
        setTaProfileInfo(response.data);
        console.log(taProfileInfo);
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
        <div className="info-card">
          <h3>Personal Information</h3>
          <p><strong>Name</strong><br />{taProfileInfo.name}</p>
          <p><strong>Surname</strong><br />{taProfileInfo.surname}</p>
          <p><strong>Email</strong><br />{taProfileInfo.email}</p>
          <p><strong>ID</strong><br />{taProfileInfo.bilkentId}</p>
          <p><strong>Role</strong><br />{taProfileInfo.role}</p>
          <p><strong>Department</strong><br />{taProfileInfo.departmentName}</p>
          <p><strong>Course</strong><br />{taProfileInfo.courseName}</p>
        </div>

        <div className="right-section">
          <div className="manage-card">
            <h3>Manage Account</h3>
            <button className="purple-button" onClick={() => setShowChangePasswordModal(true)}>Change Password</button>
            <button className="purple-button" onClick={() => setShowUnavailabilityModal(true)}>Set Unavailability</button>
          </div>

          <div className="stats-card">
            <div className="stat">Total Hours Worked: 7</div>
            <div className="stat">Pending Proctor Approvals: 2</div>
            <div className="stat">Upcoming TA Tasks: 1</div>
            <div className="stat">Days Until Next TA Task: 3</div>
          </div>
        </div>
      </div>

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

      {showUnavailabilityModal && (
        <div className="modal-overlay">
          <div className="modal">
            <h3>Set Unavailable Dates</h3>
            <label>Start Date</label>
            <input type="date" />
            <label>End Date</label>
            <input type="date" />
            <label>Details</label>
            <textarea placeholder="Details about the unavailability" rows="3"></textarea>
            <div className="modal-buttons">
              <button className="cancel-button" onClick={() => setShowUnavailabilityModal(false)}>Cancel</button>
              <button className="apply-button">Apply</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default ProfilePage;
