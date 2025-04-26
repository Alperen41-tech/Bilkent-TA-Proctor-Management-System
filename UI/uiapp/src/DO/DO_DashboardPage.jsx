// DO_Dashboard.jsx  – Dean’s Office (final with Pending/Received components)
import React, { useState } from "react";
import NavbarDO from "./NavbarDO";
import PendingRequestItem from "../PendingRequestItem";
import ReceivedRequestItem from "../ReceivedRequestItem";
import "./DO_DashboardPage.css";

const DO_Dashboard = () => {
  const [activeTab, setActiveTab] = useState("pending");
  const [selectedRequest, setSelectedRequest] = useState(null);

  /* ─── Dummy data ─────────────────────────────── */
  const pendingRequests = [
    {
      id: 1,
      date: { month: "March", day: "23", weekday: "Fri" },
      time: { start: "09:00", end: "10:30" },
      role: "TA Allocation",
      duration: 1.5,
      name: "Dean 2",
      email: "dean2@bilkent.edu.tr",
      status: "Pending TA’s answer",
      department: "Computer Engineering",
      course: "CS 202",
    },
  ];
  const receivedRequests = [
    {
      id: 2,
      date: { month: "Apr", day: "5", weekday: "Wed" },
      time: { start: "14:00", end: "16:00" },
      role: "TA Allocation",
      duration: 2,
      name: "Dean 3",
      email: "dean3@bilkent.edu.tr",
      status: "Pending your answer",
      department: "EEE",
      course: "EE 200",
    },
  ];

  const notifications = [
    { id: 1, msg: "Dean 2 requested 2 TAs for paid proctoring.", time: "30 min" },
    { id: 2, msg: "Dean 5 accepted your request of 5 TAs.", time: "10 h" },
  ];
  /* ─────────────────────────────────────────────── */

  const handleTabClick = (tab) => {
    setActiveTab(tab);
    setSelectedRequest(null);
  };

  const renderPendingReq = (req, i) => (
    <div key={i} onClick={() => setSelectedRequest(req)}>
      <PendingRequestItem {...req} />
    </div>
  );

  const renderReceivedReq = (req, i) => (
    <div key={i} onClick={() => setSelectedRequest(req)}>
      <ReceivedRequestItem {...req} />
    </div>
  );

  return (
    <div className="ta-dashboard-dashboard-page">
      <NavbarDO />

      <div className="ta-dashboard-dashboard-grid">
        {/* LEFT side */}
        <div className="ta-dashboard-dashboard-left">
  {/* Top-left panel: Tabs */}
  <div className="ta-dashboard-left-up-panel">
    <div className="ta-dashboard-tab-bar">
      <button onClick={() => handleTabClick("pending")} className={activeTab === "pending" ? "active" : ""}>
        Pending Requests
      </button>
      <button onClick={() => handleTabClick("received")} className={activeTab === "received" ? "active" : ""}>
        Received Requests
      </button>
    </div>

    <div className="ta-dashboard-tab-content">
      {activeTab === "pending" && pendingRequests.map(renderPendingReq)}
      {activeTab === "received" && receivedRequests.map(renderReceivedReq)}
    </div>
  </div>

  {/* Bottom-left panel: Details */}
  <div className="ta-dashboard-bottom-left">
    <h3>Details</h3>
    {selectedRequest ? (
      <div>
        <p><strong>Name:</strong> {selectedRequest.name}</p>
        <p><strong>Email:</strong> {selectedRequest.email}</p>
        <p><strong>Date:</strong> {selectedRequest.date.weekday}, {selectedRequest.date.month} {selectedRequest.date.day}</p>
        <p><strong>Time:</strong> {selectedRequest.time.start} - {selectedRequest.time.end}</p>
        <p><strong>Course:</strong> {selectedRequest.course}</p>
        <p><strong>Department:</strong> {selectedRequest.department}</p>
        <p><strong>Status:</strong> {selectedRequest.status}</p>
      </div>
    ) : (
      <p className="ta-dashboard-placeholder">[ Click a request to see its details ]</p>
    )}
  </div>
</div>

        {/* RIGHT side */}
        <div className="ta-dashboard-dashboard-right">
          <div className="ta-dashboard-notifications">
            <h3>Notifications</h3>
            <div className="ta-dashboard-placeholder">[ Pull real-time notifications from DB ]</div>

          </div>

          <div className="ta-dashboard-stats-box">
            <div className="ta-dashboard-stat">Request From Others: {receivedRequests.length}</div>
            <div className="ta-dashboard-stat">Your Requests: {pendingRequests.length}</div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DO_Dashboard;
