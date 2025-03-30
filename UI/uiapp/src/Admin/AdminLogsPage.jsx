import React, { useState } from "react";
import "./AdminLogsPage.css"; // Importing the CSS file for styles
import NavbarAdmin from "./NavbarAdmin"; // Importing the Navbar component

const AdminLogsPage = () => {
  const [logs, setLogs] = useState([
    {
      date: "March 23, Fri",
      time: "9:00AM - 10:30AM",
      course: "CS 102",
      type: "Quiz Proctoring",
      duration: "1.5 hours",
      taAssigned: "2 TAs assigned",
    },
    {
      date: "March 15, Thu",
      time: "9:00AM - 10:30AM",
      course: "CS 319",
      type: "Quiz Proctoring",
      duration: "1 hour",
      taAssigned: "1 TA assigned",
    },
    {
      date: "March 13, Fri",
      time: "9:00AM - 10:30AM",
      course: "CS 476",
      type: "Quiz Proctoring",
      duration: "1.5 hours",
      taAssigned: "1 TA assigned",
    },
  ]);

  return (
    <div className="logs-container">
      <NavbarAdmin />
      <div className="logs-content">
        <div className="logs-table">
          <div className="table-header">
            <input
              type="text"
              placeholder="Search by name, ex. CS 202, Ahmet"
              className="search-input"
            />
            <select className="data-type-select">
              <option value="Quiz Proctoring">Quiz Proctoring</option>
              <option value="Exam Proctoring">Exam Proctoring</option>
            </select>
            <button className="search-button">Search</button>
          </div>
          <div className="table-body">
            {logs.map((log, index) => (
              <div key={index} className="table-row">
                <div className="table-cell">{log.date}</div>
                <div className="table-cell">{log.time}</div>
                <div className="table-cell">{log.course}</div>
                <div className="table-cell">{log.type}</div>
                <div className="table-cell">{log.duration}</div>
                <div className="table-cell">{log.taAssigned}</div>
              </div>
            ))}
          </div>
        </div>
        <div className="log-details">
          <h3>View Log Details</h3>


          <div className="placeholder">[ Logs data from DB ]</div>
          {/* Action Buttons Inside Log Details Section */}
          <div className="action-buttons">
            <button className="create-report-button">Create Annual Report</button>
            <button className="export-logs-button">Export Logs</button>
          </div>
        </div>
      </div>

      <div className="set-globals">
        <h4>Set Globals</h4>
        <div className="globals-inputs">
          <label>Term - Spring 2025</label>
          <input type="text" placeholder="e.g., Spring" />
          <button className="set-term-button">Set Term</button>
        </div>
        <div className="globals-inputs">
          <label>Proctoring Cap</label>
          <input type="text" placeholder="e.g., Spring" />
          <button className="set-proctoring-button">Set Proctoring Cap</button>
        </div>
      </div>
    </div>
  );
};

export default AdminLogsPage;
