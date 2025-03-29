// src/ExamsPage.jsx
import React from "react";
import "./ExamsPage.css";
import Navbar from "./Navbar";

const ExamsPage = () => {
  return (
    <div className="exams-container">
      <Navbar />

      <div className="exams-content">
        <div className="left-section">
          <div className="card">
            <h3>Choose the task you wish to get</h3>
            <div className="task-row placeholder-row">[ Task options will load here ]</div>
          </div>

          <div className="card">
            <h3>Choose one of your tasks</h3>
            <div className="task-row placeholder-row">[ Your tasks will load here ]</div>
          </div>
        </div>

        <div className="right-section">
          <div className="card">
            <h3>TAs Assigned for this Task</h3>
            <div className="assigned-tas placeholder-row">[ Assigned TAs will load here ]</div>
            <div className="details-section">
              <label htmlFor="details">Details</label>
              <textarea id="details" placeholder="Enter details..." />
              <button className="swap-button">Request Swap</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ExamsPage;
