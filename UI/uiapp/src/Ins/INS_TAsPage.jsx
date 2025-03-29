// src/TAsPage.jsx
import React, { useState } from "react";
import NavbarINS from "./NavbarINS";
import "./INS_TAsPage.css";

const TAsPage = () => {
  const [searchText, setSearchText] = useState("");
  const [sortDepartment, setSortDepartment] = useState("");
  const [sortWorkload, setSortWorkload] = useState("");
  const [sortName, setSortName] = useState("");

  const handleSearch = () => {
    // Later: call backend API or filter the loaded TA list
    console.log("Search for:", searchText);
  };

  const handleSort = () => {
    // Later: apply sort on TA list from backend or state
    console.log("Sorting by:", sortDepartment, sortWorkload, sortName);
  };

  return (
    <div className="tas-page">
      <NavbarINS />

      <div className="ta-content">
        {/* LEFT SIDE: TA list and search */}
        <div className="ta-list-card">
          <div className="ta-search-bar">
            <input
              type="text"
              placeholder="🔍 Search TA name"
              value={searchText}
              onChange={(e) => setSearchText(e.target.value)}
            />
            <button onClick={handleSearch}>Search</button>
          </div>

          {/* Table Header */}
          <div className="ta-list-header">
            <span>Name</span>
            <span>Email</span>
            <span>Department</span>
          </div>

          {/* Placeholder for TA list */}
          <div className="ta-list-body">
            <p className="placeholder">[ TA data will appear here from DB ]</p>
          </div>
        </div>

        {/* RIGHT SIDE: Sorting options */}
        <div className="ta-sort-card">
          <h3>TA Sorting Details</h3>

          <label>Department</label>
          <select value={sortDepartment} onChange={(e) => setSortDepartment(e.target.value)}>
            <option value="">Alphabetical by default</option>
            <option value="CS">CS</option>
            <option value="IE">IE</option>
            <option value="EE">EE</option>
          </select>

          <label>Workload</label>
          <select value={sortWorkload} onChange={(e) => setSortWorkload(e.target.value)}>
            <option value="">Select lower / higher workload</option>
            <option value="asc">Lower workload first</option>
            <option value="desc">Higher workload first</option>
          </select>

          <label>Name</label>
          <select value={sortName} onChange={(e) => setSortName(e.target.value)}>
            <option value="">Alphabetical by default</option>
            <option value="asc">A → Z</option>
            <option value="desc">Z → A</option>
          </select>

          <button className="sort-btn" onClick={handleSort}>Sort TA List</button>
        </div>
      </div>
    </div>
  );
};

export default TAsPage;
