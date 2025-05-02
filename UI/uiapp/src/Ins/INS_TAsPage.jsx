// src/TAsPage.jsx
import React, { use, useState, useEffect } from "react";
import NavbarINS from "./NavbarINS";
import "./INS_TAsPage.css";
import TAItem from "../TAItem";
import ViewTAProfile from "../ViewTAProfile";
import { es } from "date-fns/locale";
import axios from "axios";


const TAsPage = () => {
  const [selectedTA, setSelectedTA] = useState(null);
  const [searchText, setSearchText] = useState("");
  const [sortDepartment, setSortDepartment] = useState("default");
  const [sortWorkload, setSortWorkload] = useState("");
  const [sortName, setSortName] = useState("");
  const [allTas, setAllTAs] = useState([]);
  const [sortedSearchedTAs, setSortedSearchedTAs] = useState([]);

  const [activeTab, setActiveTab] = useState("list");
  const [activeTAId, setActiveTAId] = useState(null);

  const handleViewProfile = (taId) => {
    setActiveTab("profile");
    setActiveTAId(taId);
  };
  
  const handleBackToList = () => {
    setActiveTab("list");
    setActiveTAId(null);
  };

  const fetchAllTAs = async () => {
    try {
      const response = await axios.get("http://localhost:8080/ta/getAllTAProfiles"); // Adjust the URL as needed
      if (response.data) {
        setAllTAs(response.data);
        console.log("Available TAs:",response.data); 
      }
      else {
        console.error("Failed to fetch TAs:", response.statusText);
      }
    } catch (error) {
      console.error("Error fetching available TAs:", error);
    }
  };


  const createTAItem = (ta, onClickHandler) => {
    const isSelected = selectedTA === ta;
  
    return (
      <TAItem
        key={ta.bilkentId}
        ta={ta}
        onClick={onClickHandler}
        isSelected={isSelected}
        inInstructor={true} // Assuming this is for instructor view
      />
    );
  };

  const handleTAClick = (ta) => {
    setSelectedTA(ta);
  };

  useEffect(() => {
    fetchAllTAs();
  }, []);

  useEffect(() => {
    const filtered = allTas.filter((ta) =>
      ta.name?.toLowerCase().includes(searchText.toLowerCase()) ||
      ta.email?.toLowerCase().includes(searchText.toLowerCase())
    );
  
    const sorted = filtered.sort((a, b) => {
      // 1. Prioritize selected department (if not "default")
      if (sortDepartment !== "default") {
        const inDeptA = a.departmentName === sortDepartment ? 0 : 1;
        const inDeptB = b.departmentName === sortDepartment ? 0 : 1;
        if (inDeptA !== inDeptB) return inDeptA - inDeptB;
      }
  
      // 2. Sort by workload
      if (sortWorkload) {
        const workloadA = a.workload ?? 0;
        const workloadB = b.workload ?? 0;
        const workloadCompare = sortWorkload === "asc" ? workloadA - workloadB : workloadB - workloadA;
        if (workloadCompare !== 0) return workloadCompare;
      }
  
      // 3. Sort by name
      if (sortName) {
        const nameA = a.name || "";
        const nameB = b.name || "";
        return sortName === "asc" ? nameA.localeCompare(nameB) : nameB.localeCompare(nameA);
      }
  
      return 0;
    });
  
    setSortedSearchedTAs(sorted);
  }, [searchText, allTas, sortDepartment, sortWorkload, sortName]);
  
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
            </div>
  
            <div className="ta-list-header">
              <span>Name</span>
              <span>Email</span>
              <span>Department</span>
              <span>Bilkent ID</span>
              <span>Workload</span>
            </div>
  
            <div className="assigned-tas">
              {sortedSearchedTAs.map((ta) => (
                <div key={ta.bilkentId} className="ta-item-container">
                  {createTAItem(ta, () => handleTAClick(ta))}
                </div>
              ))}
            </div>
  
            <button className="insTA-bottom-btn" onClick={() => {
              if(selectedTA) {
                handleViewProfile(selectedTA.userId);
              } 
              else {
                alert("Please select a TA to view their profile.");
              }
              }}>
              View Profile
            </button>
          </div>
  
          {/* RIGHT SIDE: Sorting options */}
          <div className="ta-sort-card">
            <h3>TA Sorting Details</h3>
  
            <label>Department</label>
            <select value={sortDepartment} onChange={(e) => setSortDepartment(e.target.value)}>
              <option value="default">Alphabetical by default</option>
              <option value="Computer Engineering">CS</option>
              <option value="Industrial Engineering">IE</option>
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
          </div>
        </div>
      )}
  
      {activeTab === "profile" && (
        <div>
          <ViewTAProfile taId={activeTAId} />
          <button className="insTA-bottom-btn" onClick={handleBackToList}>
            ← Back to TA List
          </button>
        </div>
      )}
    </div>
  );
  
};

export default TAsPage;
