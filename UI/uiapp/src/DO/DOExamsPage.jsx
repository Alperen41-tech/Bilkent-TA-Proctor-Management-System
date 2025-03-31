import React, { useState } from "react";
import NavbarDO from "./NavbarDO";
import "./DOExamsPage.css";

const DOExamsPage = () => {
  // Example exam data
  const [exams, setExams] = useState([
    {
      date: "March 29, Wed",
      time: "9:00AM - 10:30AM",
      course: "CS 102",
      type: "Midterm",
      taAssigned: "2 TAs assigned",
    },
    {
      date: "March 31, Fri",
      time: "1:00PM - 2:30PM",
      course: "CS 319",
      type: "Quiz",
      taAssigned: "1 TA assigned",
    },
    {
      date: "April 2, Sun",
      time: "10:00AM - 12:00PM",
      course: "CS 476",
      type: "Midterm",
      taAssigned: "3 TAs assigned",
    },
  ]);

  // Example assigned TAs
  const [assignedTAs, setAssignedTAs] = useState([
    { name: "Ali YS", department: "CS" },
    { name: "Hasan B", department: "CS" },
    { name: "Zeynep T", department: "IE" },
  ]);

  // Example available TAs
  const [availableTAs, setAvailableTAs] = useState([
    { name: "Kemal D", department: "CS" },
    { name: "Elif R", department: "CS" },
    { name: "Merve K", department: "IE" },
  ]);

  // For the search bar
  const [searchQuery, setSearchQuery] = useState("");
  const [departmentFilter, setDepartmentFilter] = useState("");

  // Handle TA dismissal
  const handleDismissTA = (index) => {
    // Remove the TA from assignedTAs
    const newAssigned = [...assignedTAs];
    newAssigned.splice(index, 1);
    setAssignedTAs(newAssigned);
  };

  // Dummy logic for "Automatic Assign"
  const handleAutomaticAssign = () => {
    alert("Automatic assignment logic goes here.");
  };

  // Dummy logic for "Manually Assign"
  const handleManualAssign = () => {
    alert("Manual assignment logic goes here.");
  };

  return (
    <div className="do-exams-container">
      <NavbarDO />

      <div className="do-exams-content">
        {/* LEFT SECTION */}
        <div className="left-section">
          <div className="search-filter">
            <input
              type="text"
              placeholder="Name: ex. CS 202, E 400"
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
            />
            <select
              value={departmentFilter}
              onChange={(e) => setDepartmentFilter(e.target.value)}
            >
              <option value="">Select Department</option>
              <option value="CS">CS</option>
              <option value="IE">IE</option>
              <option value="Other">Other</option>
            </select>
            <button className="search-button">Search</button>
          </div>

          <div className="exams-table">
            <div className="table-row header">
              <div>Date</div>
              <div>Time</div>
              <div>Course</div>
              <div>Type</div>
              <div>TA Assigned</div>
            </div>
            {exams.map((exam, index) => (
              <div className="table-row" key={index}>
                <div>{exam.date}</div>
                <div>{exam.time}</div>
                <div>{exam.course}</div>
                <div>{exam.type}</div>
                <div>{exam.taAssigned}</div>
              </div>
            ))}
          </div>
        </div>

        {/* RIGHT SECTION */}
        <div className="right-section">
          {/* Assigned TAs */}
          <div className="assigned-tas">
            <h3>Assigned TAs</h3>
            <div className="assigned-list">
              {assignedTAs.map((ta, idx) => (
                <div className="ta-item" key={idx}>
                  <span>{ta.name}</span>
                  <span>{ta.department}</span>
                  <button className="dismiss-button" onClick={() => handleDismissTA(idx)}>
                    dismiss
                  </button>
                </div>
              ))}
            </div>
          </div>

          {/* Choose TAs */}
          <div className="choose-tas">
            <h3>Choose TAs</h3>
            <div className="choose-header">
              <select>
                <option value="">Select Department</option>
                <option value="CS">CS</option>
                <option value="IE">IE</option>
                <option value="Other">Other</option>
              </select>
            </div>
            <div className="choose-list">
              {availableTAs.map((ta, idx) => (
                <div className="ta-item" key={idx}>
                  <span>{ta.name}</span>
                  <span>{ta.department}</span>
                </div>
              ))}
            </div>
            <div className="choose-actions">
              <button className="assign-button" onClick={handleAutomaticAssign}>
                Automatic Assign
              </button>
              <button className="assign-button" onClick={handleManualAssign}>
                Manually Assign
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DOExamsPage;
