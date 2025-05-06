// src/Instructor_AdminProfilePage.jsx
import React from "react";
import "./Instructor_AdminProfilePage.css"; // Reusing the same CSS
import Instructor_AdminNavbar from "./Instructor_AdminNavbar";
import axios from "axios";
import { useState, useEffect, useRef} from "react";

const Instructor_AdminProfilePage = () => {
  const [showChangePasswordModal, setShowChangePasswordModal] = useState(false);
  const [insProfileInfo, setInsProfileInfo] = useState({courses: []});

  //Refs for password inputs
  const oldPasswordRef = useRef();
  const newPasswordRef = useRef();
  const confirmNewPasswordRef = useRef();
  //-----------------------------------------------------------

  const handleGetTaConstraints = async () => {
    try {
      const response = await axios.get("http://localhost:8080/excel/getRequirementsExcel");
      if (response.data) {
        console.log("TA constraints fetched successfully.");
        console.log("TA Constraints:", response.data);
      } else {
        alert("Failed to fetch TA constraints. Please try again.");
      }
    } catch (error) {
      console.error("Error fetching TA constraints:", error);
    }
  };

  const handleChangePassword = async () => {
    try {
      console.log("Email:", insProfileInfo.email);
      console.log("Old Password:", oldPasswordRef.current.value);
      console.log("New Password:", newPasswordRef.current.value);
      console.log("Confirm New Password:", confirmNewPasswordRef.current.value);
      const response = await axios.put("http://localhost:8080/auth/changePassword", {
        email: insProfileInfo.email,
        oldPassword: oldPasswordRef.current.value,
        newPassword: newPasswordRef.current.value,
        userTypeName: "instructor"
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
        const response = await axios.get("http://localhost:8080/instructor/profile?id=4");
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
      <Instructor_AdminNavbar />

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
            <button className="purple-button" onClick={() => handleGetTaConstraints()}>Get Instructor's TA constraints</button>
          </div>
        </div>
      </div>

      {/* Change Password Modal */}
      {showChangePasswordModal && (
        <div className="modal-overlay">
          <div className="modal">
            <h3>Change Password</h3>
            <label>Old Password</label>
            <input ref={oldPasswordRef} type="password" placeholder="Enter your old password"/>
            <label>New Password</label>
            <input ref={newPasswordRef} type="password" placeholder="At least 8 characters long" />
            <label>Confirm New Password</label>
            <input ref={confirmNewPasswordRef} type="password" placeholder="Confirm new password" />
            <div className="modal-buttons">
              <button className="cancel-button" onClick={() => setShowChangePasswordModal(false)}>Cancel</button>
              <button className="apply-button" onClick={() => {
                if(!oldPasswordRef.current.value || !newPasswordRef.current.value || !confirmNewPasswordRef.current.value) {
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
      )}
    </div>
  );
};

export default Instructor_AdminProfilePage;
