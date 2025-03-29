import React, { useState } from "react";
import NavbarINS from "./NavbarINS";
import "./NavbarINS.css";
import "./INS_ExamsPage.css";

const INS_ExamsPage = () => {
  const [searchText, setSearchText] = useState("");
  const [sortName, setSortName] = useState("");
  const [sortWorkload, setSortWorkload] = useState("");
  const [taCount, setTaCount] = useState(2);
  const [autoAssign, setAutoAssign] = useState(false);
  const [unpaid, setUnpaid] = useState(false);

  const handleSearch = () => {
    console.log("Searching for:", searchText);
  };

  const handleSort = () => {
    console.log("Sort Name:", sortName, "Workload:", sortWorkload);
  };

  return (
    <div className="exams-page">
      <NavbarINS />

      <div className="grid-container">
        {/* Instructor's Proctor Assignments */}
        <div className="card assignments">
          <h3>Your Assignments with Proctors</h3>
          <div className="placeholder">[ Assignment data from DB ]</div>
        </div>

        {/* TAs Assigned to Selected Task */}
        <div className="card assigned-tas">
          <h3>TAs Assigned for this Task</h3>
          <div className="placeholder">[ Assigned TA data from DB ]</div>
        </div>

        {/* Create New Task */}
        <div className="card create-task">
          <h3>Create a New Task with Proctoring</h3>

          <label>Select Task Type</label>
          <select>
            <option value="">Dropdown</option>
          </select>

          <label>Time Interval</label>

          <input type="date" placeholder="Select date" />

          <label>Start</label>
          <div className="interval-inputs">
            <input type="time" placeholder="start" />
            <label>End</label>
            <input type="time" placeholder="end" />
          </div>


          <label>Proctoring Details</label>
          <input type="text" placeholder="Task Title" />
          <input type="text" placeholder="Enter classroom" />
          

          <label>TA count</label>
          <input type="number" className="ta-count-input" value={taCount} onChange={(e) => setTaCount(e.target.value)} />

          <div className="assignment-buttons">
            <button
              className={autoAssign ? "active" : ""}
              onClick={() => setAutoAssign(!autoAssign)}
            >
              {autoAssign ? "✔ Automatic Assigning" : "Automatic Assigning"}
            </button>
            <button
              className={unpaid ? "active" : ""}
              onClick={() => setUnpaid(!unpaid)}
            >
              {unpaid ? "✔ Unpaid Proctoring" : "Unpaid Proctoring"}
            </button>
          </div>
        </div>

        {/* TA List + Sort/Search */}
        <div className="card ta-list">
          <h3>Available TAs</h3>

          <div className="filters">
            <input
              type="text"
              placeholder="🔍 Search by name"
              value={searchText}
              onChange={(e) => setSearchText(e.target.value)}
            />
            <select value={sortName} onChange={(e) => setSortName(e.target.value)}>
              <option value="">Sort by Name</option>
              <option value="asc">A → Z</option>
              <option value="desc">Z → A</option>
            </select>
            <select value={sortWorkload} onChange={(e) => setSortWorkload(e.target.value)}>
              <option value="">Sort by Workload</option>
              <option value="low">Low to High</option>
              <option value="high">High to Low</option>
            </select>
            <button onClick={handleSearch}>Apply</button>
          </div>

          <div className="placeholder">[ TA list from DB ]</div>
        </div>
      </div>
    </div>
  );
};

export default INS_ExamsPage;
