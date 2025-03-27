import React from "react";
import "./ProfilePage.css";
import { useNavigate } from "react-router-dom";

const ProfilePage = () => {
  const navigate = useNavigate();

  const handleLogin = () => {
    // you can add validation later
    navigate("/exams");
  };

  return (
    <div className="profile-container">
      <header className="navbar">
        <div className="title">Bilkent TA Management System</div>
        <nav className="nav-links">
          <a href="#">Dashboard</a>
          <a href="#">My Schedule</a>
          <a href="#">Paid Proctoring</a>
          <a href="#" onClick={handleLogin}>Exams</a>
          <a className="active" href="#">Profile</a>
          <button className="logout">Logout</button>
        </nav>
      </header>

      <div className="profile-content">
        <div className="info-card">
          <h3>Personal Information</h3>
          <p><strong>Name</strong><br />Ahmet</p>
          <p><strong>Surname</strong><br />Yılmaz</p>
          <p><strong>Email</strong><br />ahmet.yilmaz@ug.bilkent.edu.tr</p>
          <p><strong>ID</strong><br />34346543</p>
          <p><strong>Role</strong><br />Teaching Assistant</p>
          <p><strong>Department</strong><br />Computer Science</p>
          <p><strong>Course</strong><br />CS 202</p>
        </div>

        <div className="right-section">
            <div className="manage-card">
            <h3>Manage Account</h3>
            <button className="purple-button">Change Password</button>
            <button className="purple-button">Set Unavailability</button>
            </div>

            <div className="stats-card">
            <div className="stat">Total Hours Worked: 7</div>
            <div className="stat">Pending Proctor Approvals: 2</div>
            <div className="stat">Upcoming TA Tasks: 1</div>
            <div className="stat">Days Until Next TA Task: 3</div>
            </div>
        </div>
      </div>
    </div>
  );
};

export default ProfilePage;