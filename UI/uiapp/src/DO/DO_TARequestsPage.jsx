// DO_TARequestsPage.jsx
import React, { useState } from "react";
import NavbarDO from "./NavbarDO";
import "./DO_TARequestsPage.css";

const DO_TARequestsPage = () => {
  const [selectedRequestId, setSelectedRequestId] = useState(null);

  const taRequests = [
    {
      id: 1,
      date: { month: "Mar", day: 23, weekday: "Fri" },
      time: "09:00",
      course: "CS 202",
      faculty: "Engineering",
      department: "Computer Engineering",
      status: "TA Allocation to Departments",
    },
    {
      id: 2,
      date: { month: "Apr", day: 5, weekday: "Wed" },
      time: "14:00",
      course: "EE 200",
      faculty: "Engineering",
      department: "Electrical & Electronics Eng.",
      status: "TA Allocation to Departments",
    },
  ];

  const selected = taRequests.find((r) => r.id === selectedRequestId);

  return (
    <div className="do-TA-ta-page">
      <NavbarDO />

      <main className="do-TA-grid">
        {/* ---------- LEFT COLUMN ---------- */}
        <section className="do-TA-col do-left">
          {/* -- Request List -- */}
          <div className="do-TA-card">
            <h3 className="do-TA-card-title">In-Progress TA Requests</h3>
            <div className="do-TA-list">
              {taRequests.map((req) => (
                <button
                  key={req.id}
                  type="button"
                  className={`do-TA-list-item ${{ true: "do-selected" }[selectedRequestId === req.id] || ""}`}
                  onClick={() => setSelectedRequestId(req.id)}
                >
                  <span className="do-TA-li-date">{`${req.date.month} ${req.date.day} (${req.date.weekday})`}</span>
                  <span className="do-TA-li-time">{req.time}</span>
                  <span className="do-TA-li-course">{req.course}</span>
                  <span className="do-TA-li-status">{req.status}</span>
                </button>
              ))}
            </div>
          </div>

          {/* -- Details -- */}
          <div className="do-TA-card">
            <h3 className="do-TA-card-title">Details</h3>
            {selected ? (
              <ul className="do-TA-details">
                <li><strong>Date:</strong> {`${selected.date.weekday}, ${selected.date.month} ${selected.date.day}`}</li>
                <li><strong>Time:</strong> {selected.time}</li>
                <li><strong>Course:</strong> {selected.course}</li>
                <li><strong>Faculty:</strong> {selected.faculty}</li>
                <li><strong>Department:</strong> {selected.department}</li>
                <li><strong>Status:</strong> {selected.status}</li>
              </ul>
            ) : (
              <div className="do-TA-placeholder">⇠ Click a request to see its details</div>
            )}
          </div>
        </section>

        {/* ---------- RIGHT COLUMN ---------- */}
        <section className="do-TA-col do-right">
          <div className="do-TA-card">
            <h3 className="do-TA-card-title">Sending Required TA Number</h3>
            <div className="do-TA-placeholder">(empty)</div>
          </div>
          <div className="do-TA-card">
            <h3 className="do-TA-card-title">Selected Departments</h3>
            <div className="do-TA-placeholder">(empty)</div>
          </div>
        </section>
      </main>
    </div>
  );
};

export default DO_TARequestsPage;