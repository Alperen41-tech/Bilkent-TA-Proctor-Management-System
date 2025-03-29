import React, { useState } from "react";
import NavbarINS from "./NavbarINS";
import "./INS_DashboardPage.css";

const INS_DashboardPage = () => {
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
      <NavbarINS />
      <div className="dashboard-grid">
        {/* LEFT SIDE */}
        <div className="dashboard-left">
          {/* Tabs */}
          <div className="tab-bar">
            <button onClick={() => handleTabClick("pending")} className={activeTab === "pending" ? "active" : ""}>Pending Requests</button>
            <button onClick={() => handleTabClick("received")} className={activeTab === "received" ? "active" : ""}>Received Requests</button>
            <button onClick={() => handleTabClick("tasks")} className={activeTab === "tasks" ? "active" : ""}>Tasks</button>
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
                <div className="bottom-left-task">
                <div className="task-type-create-form">
                    <h3>Create Task Type</h3>
                    <form>
                    <label>Task Type</label>
                        <input type="text" placeholder="Task Type" />
                    <label>Maximum Time Limit</label>
                    <div className="time-inputs">
                        <input type="number" placeholder="Hours" />
                    </div>
                    <button type="submit">Create Task Type</button>
                    </form>
                </div>
                <div className="task-type-delete-form">
                    <h3>Delete Task Type</h3>
                    <label>Select Task Type</label>
                    <form>
                    <select>
                        <option value="task1">Task Type 1</option>
                        <option value="task2">Task Type 2</option>
                        <option value="task3">Task Type 3</option>
                    </select>
                    <button type="submit">Delete Task Type</button>
                    </form>
                </div>
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
            <div className="stat">Unapproved Works: 7</div>
            <div className="stat">Total Works Last Week: 45 Hours</div>
            <div className="stat">Upcoming TA Tasks: 1</div>
            <div className="stat">Days Until Next TA Task: 3 </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default INS_DashboardPage;
