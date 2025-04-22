import React, { useState } from "react";
import NavbarDO from "./NavbarDO";
import "./DOExamsPage.css";
import AdminDatabaseItem from "../Admin/AdminDatabaseItem";
import TAItem from "../TAItem";




const DOExamsPage = () => {
  // Example exam data
    const [selectedTA, setSelectedTA] = useState(null);
    const [selectedExamKey, setSelectedExamKey] = useState(null);
  

  const adminDatabaseItems = [
    {
      type: 'exam',
      data: {
        id: 1,
        course: "CS 319",
        date: "2025-03-15",
        time: "10:00 AM",
        location: "EE-214"
      }
    },
    {
      type: 'exam',
      data: {
        id: 2,
        course: "CS 315",
        date: "2025-04-17",
        time: "10:30 AM",
        location: "EE-212"
      }
    },
    {
      type: 'exam',
      data: {
        id: 3,
        course: "CS 376",
        date: "2025-05-25",
        time: "11:00 AM",
        location: "BZ-04"
      }
    },
    {
      type: 'exam',
      data: {
        id: 4,
        course: "CS 202",
        date: "2025-06-05",
        time: "8:00 AM",
        location: "EE-214"
      }
    },
    
  ];




  // Example available TAs
  const [availableTAs, setAvailableTAs] = useState([
    { name: "Kemal D", department: "CS" },
    { name: "Elif R", department: "CS" },
    { name: "Merve K", department: "IE" },
  ]);

  // For the search bar
  const [searchQuery, setSearchQuery] = useState("");
  const [departmentFilter, setDepartmentFilter] = useState("");



  // Dummy logic for "Automatic Assign"
  const handleAutomaticAssign = () => {
    alert("Automatic assignment logic goes here.");
  };

  // Dummy logic for "Manually Assign"
  const handleManualAssign = () => {
    alert("Manual assignment logic goes here.");
  };


  const handleTAClick = (ta) => {
    const key = `${ta.firstName}-${ta.lastName}-${ta.email}`;
    setSelectedTA(key);
  };


  const createLogsDatabaseItems = () => {
    return adminDatabaseItems.map((item) => {
      const key = `${item.data.course}-${item.data.date}`;
      const isSelected = selectedExamKey === key;
  
      return (
        <AdminDatabaseItem
          key={key}
          type={item.type}
          data={item.data}
          onDelete={(id) => console.log(`Deleted ${item.type} with ID: ${id}`)}
          onSelect={(data) => setSelectedExamKey(key)}
          isSelected={isSelected}
          inLog={true}
        />
      );
    });
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

            {createLogsDatabaseItems()}

          </div>
        </div>

        {/* RIGHT SECTION */}
        <div className="right-section">
          {/* Assigned TAs */}
          <div className="assigned-tas">
            <h3>Assigned TAs</h3>
            <div className="assigned-list">
              {createTAItem("Ahmet", "Yılmaz", "ahmet.yilmaz@example.com", handleTAClick, selectedTA)}
              {createTAItem("Merve", "Kara", "merve.kara@example.com", handleTAClick, selectedTA)}
              {createTAItem("John", "Doe", "john.doe@example.com", handleTAClick, selectedTA)}

            </div>
            <button className="dismissTA-button" >
                Dismiss
            </button>
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
              {createTAItem("Cemil", "Yılmaz", "ahmet.yilmaz@example.com", handleTAClick, selectedTA)}
              {createTAItem("Yusuf", "Kara", "merve.kara@example.com", handleTAClick, selectedTA)}
              {createTAItem("Suat", "Doe", "john.doe@example.com", handleTAClick, selectedTA)}
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
