// src/PaidProctoringPage.jsx
import React from "react";
import Navbar from "../Navbar";
import "./PaidProctoringPage.css";

const PaidProctoringPage = () => {
  return (
    <div className="paid-proctoring-page">
      <Navbar />

      <div className="proctoring-content">
        {/* LEFT: List of proctoring tasks */}
        <div className="proctoring-left">
          <div className="card">
            <h3>Paid Proctoring List</h3>
            
            {/* Task List Placeholder */}
            <div className="task-list">
              {/* Future mapped tasks will go here */}
              <p className="placeholder">[ Proctoring tasks will load here from DB ]</p>
            </div>
          </div>
        </div>

        {/* RIGHT: Selected task details */}
        <div className="proctoring-right">
          <div className="card">
            <h3>TAs Assigned for this Task</h3>

            {/* Assigned TA Placeholder */}
            <div className="assigned-tas">
              <p className="placeholder">[ Assigned TAs will load here from DB ]</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default PaidProctoringPage;
