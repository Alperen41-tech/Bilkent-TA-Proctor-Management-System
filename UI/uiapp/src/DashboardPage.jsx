import React, { useState } from "react";
import Navbar from "./Navbar";
import "./DashboardPage.css";

const DashboardPage = () => {
  const [activeTab, setActiveTab] = useState("pending");
  const [selectedRequest, setSelectedRequest] = useState(null);
  const [selectedProctoring, setSelectedProctoring] = useState(null);

  const handleTabClick = (tab) => {
    setActiveTab(tab);
    setSelectedRequest(null);
    setSelectedProctoring(null);
  };

  return (
    <div className="dashboard-page">
      <Navbar />
      <div className="dashboard-grid">
        {/* LEFT SIDE */}
        <div className="dashboard-left">
          {/* Tabs */}
          <div className="tab-bar">
            <button onClick={() => handleTabClick("pending")} className={activeTab === "pending" ? "active" : ""}>Pending Requests</button>
            <button onClick={() => handleTabClick("received")} className={activeTab === "received" ? "active" : ""}>Received Requests</button>
            <button onClick={() => handleTabClick("tasks")} className={activeTab === "tasks" ? "active" : ""}>Tasks</button>
            <button onClick={() => handleTabClick("proctorings")} className={activeTab === "proctorings" ? "active" : ""}>Proctorings</button>
          </div>

          {/* Top Left Panel */}
          <div className="tab-content">
            {activeTab === "pending" && (
              <div className="placeholder">[ Load and display SENT requests from DB — click to select one ]</div>
            )}
            {activeTab === "received" && (
              <div className="placeholder">[ Load RECEIVED requests from DB — click to select one ]</div>
            )}
            {activeTab === "tasks" && (
              <div className="placeholder">[ Display past submitted workload entries ]</div>
            )}
            {activeTab === "proctorings" && (
              <div className="placeholder">[ Load upcoming proctoring duties — click to send swap ]</div>
            )}
          </div>

          {/* Bottom Left Panel */}
          <div className="bottom-left">
            {activeTab === "pending" || activeTab === "received" ? (
              <div className="details-panel">
                <h3>Details</h3>
                {selectedRequest ? (
                  <div>
                    <p><strong>To:</strong> {selectedRequest.receiver}</p>
                    <p><strong>Reason:</strong> {selectedRequest.reason}</p>
                    <p><strong>Time:</strong> {selectedRequest.timestamp}</p>
                  </div>
                ) : (
                  <p className="placeholder">[ Click a request to see its details ]</p>
                )}
              </div>
            ) : activeTab === "tasks" ? (
              <div className="task-entry-form">
                <h3>Enter Task</h3>
                <form>
                  <label>Task Type</label>
                  <select>
                    <option>Quiz Reading</option>
                    <option>Homework Grading</option>
                    <option>Project Evaluation</option>
                  </select>

                  <label>Time Spent</label>
                  <div className="time-inputs">
                    <input type="number" placeholder="Hours" />
                    <input type="number" placeholder="Minutes" />
                  </div>

                  <label>Details</label>
                  <textarea placeholder="Optional comments" />

                  <button type="submit">Send</button>
                </form>
              </div>
            ) : activeTab === "proctorings" ? (
              <div className="swap-form">
                <h3>Proctoring Information</h3>
                {selectedProctoring ? (
                  <form>
                    <label>Select TA</label>
                    <select>
                      <option>TA 1</option>
                      <option>TA 2</option>
                    </select>

                    <label>Details</label>
                    <textarea placeholder="Reason for swap" />

                    <button type="submit">Send Swap Request</button>
                  </form>
                ) : (
                  <p className="placeholder">[ Select a proctoring duty to send swap request ]</p>
                )}
              </div>
            ) : null}
          </div>
        </div>

        {/* RIGHT SIDE */}
        <div className="dashboard-right">
          <div className="notifications">
            <h3>Notifications</h3>
            <div className="placeholder">[ Pull real-time notifications from DB ]</div>
          </div>

          <div className="stats-box">
            <div className="stat">Total Hours Worked: 7</div>
            <div className="stat">Hours Awaiting Approval: 2.5</div>
            <div className="stat">Upcoming Proctoring Duties: 1</div>
            <div className="stat">Days Until Next Proctoring: 3</div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DashboardPage;
