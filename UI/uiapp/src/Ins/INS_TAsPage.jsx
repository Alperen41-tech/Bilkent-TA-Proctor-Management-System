// src/TAsPage.jsx
import React, { useState } from "react";
import NavbarINS from "./NavbarINS";
import "./INS_TAsPage.css";
import TAItem from "../TAItem";
import ViewTAProfile from "../ViewTAProfile";


const TAsPage = () => {
  const [selectedTA, setSelectedTA] = useState(null);
  const [searchText, setSearchText] = useState("");
  const [sortDepartment, setSortDepartment] = useState("");
  const [sortWorkload, setSortWorkload] = useState("");
  const [sortName, setSortName] = useState("");

  const [activeTab, setActiveTab] = useState("list");
  const [activeTAId, setActiveTAId] = useState(null);

  const handleSearch = () => {
    // Later: call backend API or filter the loaded TA list
    console.log("Search for:", searchText);
  };

  const handleSort = () => {
    // Later: apply sort on TA list from backend or state
    console.log("Sorting by:", sortDepartment, sortWorkload, sortName);
  };


  const handleViewProfile = (taId) => {
    setActiveTab("profile");
    setActiveTAId(taId);
  };
  
  const handleBackToList = () => {
    setActiveTab("list");
    setActiveTAId(null);
  };


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



  const handleTAClick = (ta) => {
    const key = `${ta.firstName}-${ta.lastName}-${ta.email}`;
    setSelectedTA(key);
  };

  return (
    <div className="tas-page">
      <NavbarINS />
  
      {activeTab === "list" && (
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
  
            <div className="ta-list-header">
              <span>Name</span>
              <span>Email</span>
              <span>Department</span>
            </div>
  
            <div className="assigned-tas">
              {createTAItem("Ahmet", "Yılmaz", "ahmet.yilmaz@example.com", handleTAClick, selectedTA)}
              {createTAItem("Merve", "Kara", "merve.kara@example.com", handleTAClick, selectedTA)}
              {createTAItem("John", "Doe", "john.doe@example.com", handleTAClick, selectedTA)}
            </div>
  
            <button className="insTA-bottom-btn" onClick={() => handleViewProfile(2)}>
              View Profile
            </button>
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
      )}
  
      {activeTab === "profile" && (
        <div style={{ marginTop: "30px" }}>
          <button className="insTA-bottom-btn" onClick={handleBackToList}>
            ← Back to TA List
          </button>
          <ViewTAProfile taId={activeTAId} />
        </div>
      )}
    </div>
  );
  
};

export default TAsPage;
