import React, { useState } from "react";
import "./AdminLogsPage.css";
import NavbarAdmin from "./NavbarAdmin";
import TAItem from "../TAItem";
import TaskItemSearch from "../TaskItemSearch";

const AdminLogsPage = () => {
  const [logs, setLogs] = useState([]); // Empty list, no hardcoded data
  const [selectedTA, setSelectedTA] = useState(null);
  

  const createTAItem = (firstName, lastName, email, onClickHandler, selectedTAKey) => {
    const ta = { firstName, lastName, email };
    const key = `${firstName}-${lastName}-${email}`;
    const isSelected = selectedTAKey === key;
  
    return (
      <TAItem
        key={key}
        ta={ta}
        onClick={onClickHandler}
        isSelected={isSelected}
      />
    );
  };

  const createTaskItem = (course, name, date, timeInterval, classroom, onClickHandler, isSelected) => {
    const task = { course, name, date, timeInterval, classroom };
    return (
      <TaskItemSearch
        key={`${course}-${name}-${date}-${timeInterval}`}
        task={task}
        onClick={() => onClickHandler(task)}
        isSelected={isSelected}
      />
    );
  };
  


  

  const handleTAClick = (ta) => {
    const key = `${ta.firstName}-${ta.lastName}-${ta.email}`;
    setSelectedTA(key);
  };

  return (
    <div className="logs-container">
      <NavbarAdmin />
      <div className="logs">
        <div className="logs-content">
          <div className="logs-table">
            <div className="table-header">
              <input
                type="text"
                placeholder="Search by name, ex. CS 202"
                className="search-input"
              />
              <select className="data-type-select">
                <option value="Quiz Proctoring">Quiz Proctoring</option>
                <option value="Exam Proctoring">Exam Proctoring</option>
              </select>
              <button className="search-button">Search</button>
            </div>
            <div className="table-body">
                          {logs.length === 0 ? (
                <div className="no-logs-message">
                  {createTaskItem("CS 101", "Quiz Proctoring", "March 24", "9:00 - 10:00", "B-116", () => {}, false)}
                  {createTaskItem("CS 319", "Midterm Exam", "March 26", "13:00 - 15:00", "B-203", () => {}, false)}
              {createTAItem("Ahmet", "Yılmaz", "ahmet.yilmaz@example.com", handleTAClick, selectedTA)}

                </div>
              ) : (
                logs.map((log, index) => (
                  <div key={index} className="table-row">
                    {/* Your log content here */}
                  </div>
                ))
              )}

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

        <div className="log-details">
          <h3>View Log Details</h3>
          <div className="placeholder">[ Logs data from DB ]</div>
          <div className="action-buttons">
            <button className="create-report-button">Create Annual Report</button>
            <button className="export-logs-button">Export Logs</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdminLogsPage;
