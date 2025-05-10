import React, { useEffect, useState, useRef } from "react";
import "./ProfilePage.css";
import { useNavigate } from "react-router-dom";
import Navbar from "./Navbar";
import axios from "axios";


/**
 * ProfilePage Component
 * Displays TA profile information and provides options to change password
 * and set unavailability periods through modals.
 */
const ProfilePage = () => {
  const [showChangePasswordModal, setShowChangePasswordModal] = React.useState(false);
  const [showUnavailabilityModal, setShowUnavailabilityModal] = React.useState(false);
  const [taProfileInfo, setTaProfileInfo] = useState({}); // Initialize as an empty object");
  const today = new Date().toISOString().split("T")[0];

  // Refs for the input fields
  const inputStartDateRef = useRef();
  const inputStartTimeRef = useRef();
  const inputEndDateRef = useRef();
  const inputEndTimeRef = useRef();
  const inputDetailsRef = useRef();
  const oldPasswordRef = useRef();
  const newPasswordRef = useRef();
  const confirmNewPasswordRef = useRef();
  //-----------------------------------------------------------
    /**
   * Handles changing the TA's password by calling the backend API.
   */
  const handleChangePassword = async () => {
    try {
      console.log("Email:", taProfileInfo.email);
      console.log("Old Password:", oldPasswordRef.current.value);
      console.log("New Password:", newPasswordRef.current.value);
      console.log("Confirm New Password:", confirmNewPasswordRef.current.value);
      const response = await axios.put("http://localhost:8080/auth/changePassword", {
        email: taProfileInfo.email,
        oldPassword: oldPasswordRef.current.value,
        newPassword: newPasswordRef.current.value,
        userTypeName: "ta"
      });
      if (response.data) {
        alert("Password changed successfully.");
        setShowChangePasswordModal(false);
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

  /**
   * Sends a new leave request based on the form inputs.
   */
  const handleSetUnavailability = async () => {
    try {
      const token = localStorage.getItem("token");
      console.log("Start Date:", inputStartDateRef.current.value);
      console.log("Start Time:", inputStartTimeRef.current.value);
      console.log("End Date:", inputEndDateRef.current.value);
      console.log("End Time:", inputEndTimeRef.current.value);
      console.log("Details:", inputDetailsRef.current.value);
      console.log("Token:", token);
      const response = await axios.post("http://localhost:8080/taLeaveRequest/create", {
        description: inputDetailsRef.current.value,
        leaveStartDate: inputStartDateRef.current.value + "T" + inputStartTimeRef.current.value + ":00",
        leaveEndDate: inputEndDateRef.current.value + "T" + inputEndTimeRef.current.value + ":00",
      },{
        headers: {
          Authorization: `Bearer ${token}`
        }
      }
      );

      if (response.data) {
        alert("Leave request sent successfully!");
        console.log("Leave request sent successfully!");
      }
      else {
        alert("Failed to set unavailability. Please try again.");
        console.log("Failed to set unavailability. Please try again.");
      }
    } catch (error) {
      console.error("Error fetching tasks:", error);
      if (error.response.data.message) {
        alert(error.response.data.message);
      }
      else{
        alert("An error occurred. Please try again.");
      }
    }

  }; 

    /**
   * Fetches the TA's profile information from the backend.
   */
  const fetchProfileInformation = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.get("http://localhost:8080/ta/profile", {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      
      setTaProfileInfo(response.data);
      console.log(taProfileInfo);
    } catch (error) {
      console.error("Error fetching tasks:", error);
      if (error.response.data.message) {
        alert(error.response.data.message);
      }
      else{
        alert("An error occurred. Please try again.");
      }
    }
  };

  useEffect(() => {
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
          <p><strong>Total Workload</strong><br />{taProfileInfo.workload}</p>
        </div>

        <div className="right-section">
          <div className="manage-card">
            <h3>Manage Account</h3>
            <button className="purple-button" onClick={() => setShowChangePasswordModal(true)}>Change Password</button>
            <button className="purple-button" onClick={() => setShowUnavailabilityModal(true)}>Set Unavailability</button>
          </div>
        </div>
      </div>

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

      {showUnavailabilityModal && (
        <div className="modal-overlay">
          <form className="modal" onSubmit={(e) => { e.preventDefault();handleSetUnavailability();}}>
            <h3>Set Unavailable Dates</h3>
            <label>Start Date</label>
            <input ref={inputStartDateRef} type="date" min={today} required/>
            <label>Start Time</label>
            <input ref={inputStartTimeRef} type="time" min="08:00" max="22:00"required/>
            <label>End Date</label>
            <input ref={inputEndDateRef} type="date" min={today} required/>
            <label>End Time</label>
            <input ref={inputEndTimeRef} type="time" min="08:00" max="22:00" required/>
            <label>Details</label>
            <textarea ref={inputDetailsRef} placeholder="Details about the unavailability" rows="3"></textarea>
            <div className="modal-buttons">
              <button className="cancel-button" onClick={() => setShowUnavailabilityModal(false)}>Cancel</button>
              <button className="apply-button" type="submit">Apply</button>
            </div>
          </form>
        </div>
      )}
    </div>
  );
};

export default ProfilePage;
