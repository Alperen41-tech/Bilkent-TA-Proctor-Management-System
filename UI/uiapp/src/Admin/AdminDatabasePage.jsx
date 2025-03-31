import React, { useState } from "react";
import NavbarAdmin from "./NavbarAdmin";
import "./AdminDatabasePage.css";

const AdminDatabasePage = () => {
  const [selectedType, setSelectedType] = useState("");
  const [taCount, setTaCount] = useState(2);
  const [selectedFile, setSelectedFile] = useState(null);

  const handleTypeChange = (e) => {
    setSelectedType(e.target.value);
  };

  const handleFileChange = (e) => {
    if (e.target.files && e.target.files[0]) {
      setSelectedFile(e.target.files[0]);
    }
  };

  return (
    <div className="database-container">
      <NavbarAdmin />

      {/* Top Section: Left side for search and data table, right side for view data details */}
      <div className="database-top">
        <div className="search-data-section">
          <div className="search-bar">
            <input type="text" placeholder="Name: ex. CS 202, Ahmet" />
            <select>
              <option value="">Data Type</option>
              <option value="Midterm">Midterm</option>
              <option value="Exam">Exam</option>
            </select>
            <button className="search-button">Search</button>
          </div>
          <div className="data-table">
            <div className="table-header">
              {/*buraya header başlığı gelecek*/}
            </div>
            <div className="table-body">
              <div className="table-row">
                <div> </div>
                <div> </div>
                <div> </div>
                <div> </div>
                <div> </div>
              </div>
              <div className="table-row">
                <div> </div>
                <div> </div>
                <div> </div>
                <div> </div>
                <div> </div>
              </div>
            </div>
          </div>
        </div>
        <div className="view-data-details">
          <h3>View Data Details</h3>
          <p>Select an entry from the table to see details.</p>
        </div>
      </div>

      {/* Bottom Section: Two panels side by side */}
      <div className="database-bottom">
        {/* Left Panel: Create New Type */}
        <div className="create-new-type">
          <h3>Create New Type</h3>
          <div className="type-selector">
            <label>Select Type:</label>
            <select value={selectedType} onChange={handleTypeChange}>
              <option value="">--Select Type--</option>
              <option value="Exam">Exam</option>
              <option value="TA">TA</option>
              <option value="Quiz">Quiz</option>
            </select>
          </div>
          {selectedType === "Exam" && (
            <div className="type-form">
              <label>Title</label>
              <input type="text" placeholder="e.g., Midterm Exam" />

              <label>Date</label>
              <input type="date" />

              <label>Classroom</label>
              <input type="text" placeholder="e.g., A101" />

              <label>Department</label>
              <select>
                <option value="">Select Department</option>
                <option value="CS">CS</option>
                <option value="IE">IE</option>
                <option value="Other">Other</option>
              </select>

              <label>Course Name</label>
              <input type="text" placeholder="e.g., CS 202" />

              <label>Start Time</label>
              <input type="time" />

              <label>End Time</label>
              <input type="time" />

              <label>TA Count</label>
              <input
                type="number"
                value={taCount}
                onChange={(e) => setTaCount(Number(e.target.value))}
              />

              <button className="create-type-button">Create</button>
            </div>
          )}

          {selectedType === "Quiz" && (
            <div className="type-form">
              <label>Title</label>
              <input type="text" placeholder="e.g., Quiz" />

              <label>Date</label>
              <input type="date" />

              <label>Classroom</label>
              <input type="text" placeholder="e.g., B-203" />

              <label>Department</label>
              <select>
                <option value="">Select Department</option>
                <option value="CS">CS</option>
                <option value="IE">IE</option>
                <option value="Other">Other</option>
              </select>

              <label>Course Name</label>
              <input type="text" placeholder="e.g., CS 202" />

              <label>Start Time</label>
              <input type="time" />

              <label>End Time</label>
              <input type="time" />

              <label>TA Count</label>
              <input
                type="number"
                value={taCount}
                onChange={(e) => setTaCount(Number(e.target.value))}
              />

              <button className="create-type-button">Create</button>
            </div>
          )}

          {selectedType === "TA" && (
            <div className="type-form">
              <label>TA Name</label>
              <input type="text" placeholder="enter name" />

              <label>Surname</label>
              <input type="text" placeholder="enter surname" />

              <label>Email</label>
              <input type="text" placeholder="enter email" />

              <label>ID</label>
              <input type="text" placeholder="enter ID" />

              <label>Department</label>
              <select>
                <option value="">Select Department</option>
                <option value="CS">CS</option>
                <option value="IE">IE</option>
                <option value="Other">Other</option>
              </select>

              <label>Select Type</label>
              <select>
                <option value="">Select Type</option>
                <option value="FT">Full Time</option>
                <option value="PT">Part Time</option>
              </select>

              <button className="create-type-button">Create</button>
            </div>
          )}
        </div>

        {/* Right Panel: Dump New Data */}
        <div className="dump-new-data">
          <h3>Dump New Data</h3>
          <div className="upload-container">
            <div className="drag-drop-area">
              <p>Drag &amp; Drop file here</p>
              <p>or</p>
              <label htmlFor="file-upload" className="choose-file-label">
                Choose File
              </label>
              <input
                id="file-upload"
                type="file"
                onChange={handleFileChange}
                style={{ display: "none" }}
              />
              <p>Supported formats: Excel</p>
            </div>
            <div className="upload-buttons">
              <button className="import-button">Import</button>
              <button className="cancel-button">Cancel</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdminDatabasePage;
