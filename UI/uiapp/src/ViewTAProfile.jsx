// src/ViewTAProfile.jsx
import React, { useEffect, useState } from "react";
import "./ViewTAProfile.css";
import axios from "axios";

const ViewTAProfile = ({ taId = 2 }) => {
  const [profile, setProfile] = useState({});

  useEffect(() => {
    const fetchTA = async () => {
      try {
        const res = await axios.get(`http://localhost:8080/ta/profile?id=${taId}`);
        setProfile(res.data);
      } catch (err) {
        console.error("Failed to load TA profile", err);
      }
    };
    fetchTA();
  }, [taId]);

  const days = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"];
  const times = [
    "8:30 am", "9:30 am", "10:30 am", "11:30 am", "12:30 pm",
    "1:30 pm", "2:30 pm", "3:30 pm", "4:30 pm", "5:30 pm"
  ];

  return (
    <div className="view-ta-profile-profile-view">

      <div className="view-ta-profile-profile-content">
        <div className="view-ta-profile-left-info-card">
          <h3>TA Profile</h3>
          <p><strong>Name:</strong> {profile.name}</p>
          <p><strong>Surname:</strong> {profile.surname}</p>
          <p><strong>Email:</strong> {profile.email}</p>
          <p><strong>Bilkent ID:</strong> {profile.bilkentId}</p>
          <p><strong>Role:</strong> {profile.role}</p>
          <p><strong>Department:</strong> {profile.departmentName}</p>
          <p><strong>Course:</strong> {profile.courseName}</p>
        </div>

        <div className="view-ta-profile-right-schedule-card">
          <h3>Weekly Schedule</h3>
          <div className="view-ta-profile-view-calendar">
          <div className="view-ta-profile-calendar-row">
              <div className="view-ta-profile-empty-cell"></div>
              {days.map((day) => (
                <div key={day} className="view-ta-profile-day-header">{day}</div>
              ))}
            </div>

            {times.map((time, timeIdx) => (
              <div className="view-ta-profile-calendar-row" key={timeIdx}>
                <div className="view-ta-profile-time-cell">{time}</div>
                {days.map((_, dayIdx) => (
                  <div key={`${timeIdx}-${dayIdx}`} className="view-ta-profile-view-cell">
                    {/* Busy slots can be styled based on data in future */}
                  </div>
                ))}
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default ViewTAProfile;
