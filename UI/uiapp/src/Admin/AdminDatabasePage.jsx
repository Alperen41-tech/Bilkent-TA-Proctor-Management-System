import React, { useState, useRef } from "react";
import NavbarAdmin from "./NavbarAdmin";
import "./AdminDatabasePage.css";
import AdminDatabaseItem from "./AdminDatabaseItem";
import axios from "axios";

const AdminDatabasePage = () => {
  const [selectedType, setSelectedType] = useState("");
  const [taCount, setTaCount] = useState(2);
  const [selectedFile, setSelectedFile] = useState(null);
  const [selectedData, setSelectedData] = useState({});
  const [departmentSelection, setDepartmentSelection] = useState("");
  const [selectTaWorktime, setSelectTaWorktime] = useState("");

  // Refs for new TA creation
  const newTaNameRef = useRef();
  const newTaSurnameRef = useRef();
  const newTaEmailRef = useRef();
  const newTaIdRef = useRef();
  const newTaCourseNameRef = useRef();
  const newTaPhoneNumRef = useRef();
  const newTaClassYearRef = useRef();
  const newTaPasswordRef = useRef();
  //-----------------------------

  // Sample data for the database items
  const adminDatabaseItems = [
    {
      type: 'ta',
      data: {
        id: 1,
        name: "Ali Kılıç",
        email: "ali.kilic@ug.bilkent.edu.tr",
        department: "Computer Engineering"
      }
    },
    {
      type: 'instructor',
      data: {
        id: 2,
        name: "Dr. Zeynep Aslan",
        email: "zeynep.aslan@bilkent.edu.tr",
        title: "Associate Professor"
      }
    },
    {
      type: 'exam',
      data: {
        id: 3,
        course: "CS 319",
        date: "2025-03-15",
        time: "10:00 AM",
        location: "EE-214"
      }
    },
    {
      type: 'course',
      data: {
        id: 4,
        code: "CS 319",
        name: "Object-Oriented Software Engineering",
        instructor: "Dr. Zeynep Aslan"
      }
    },
    {
      type: 'class',
      data: {
        id: 5,
        room: "EE-214",
        capacity: 35
      }
    },
    {
      type: 'student',
      data: {
        id: 6,
        name: "Elif Kaya",
        email: "elif.kaya@ug.bilkent.edu.tr",
        studentId: "22123478"
      }
    }
  ];



  const handleTypeChange = (e) => {
    setSelectedType(e.target.value);
  };

  const handleFileChange = (e) => {
    if (e.target.files && e.target.files[0]) {
      setSelectedFile(e.target.files[0]);
    }
  };

  const createDatabaseItems = () => {
    return(
    adminDatabaseItems.map((item) => (
      <AdminDatabaseItem
        key={`${item.type}-${item.data.id}`}
        type={item.type}
        data={item.data}
        onDelete={(id) => console.log(`Deleted ${item.type} with ID: ${id}`)}
        onSelect={(data) => setSelectedData(data)}
        isSelected={selectedData.id === item.data.id}
        inLog={false} // Assuming this is not a log item
      />
    )))
  }
  const createNewTa = async (newTaName,newTaSurname, newTaEmail, newTaId, departmentSelection, newTaCourseName, newTaPhoneNum, newTaClassYear, newTaPassword) => {
    try {
      console.log("Submitting new TA:", {
        name: newTaName,
        surname: newTaSurname,
        email: newTaEmail,
        bilkentId: newTaId,
        departmentName: departmentSelection,
        courseName: newTaCourseName,
        phoneNumber: newTaPhoneNum,
        classYear: newTaClassYear,
        password: newTaPassword
      });

      const response = await axios.post("http://localhost:8080/ta/createTA", {
        profile: {
          name: newTaName,
          surname: newTaSurname,
          email: newTaEmail,
          bilkentId: newTaId,
          departmentName: departmentSelection,
          courseName: newTaCourseName,
          phoneNumber: newTaPhoneNum,
          active: true,
          classYear: parseInt(newTaClassYear, 10),
        },
        login: {
          password: newTaPassword,
          userTypeName: "ta"
        }
      });

      if (!response) {
        alert("Could not locked the swap. Try again.");
      } 
    } catch (error) {
      console.error("There was an error with the ta creation:", error);
      alert("An error occurred. Please try again.");
    }

  }

  return (
    <div className="admin-database-database-container">
      <NavbarAdmin />

      {/* Top Section: Left side for search and data table, right side for view data details */}
      <div className="admin-database-database-top">
        <div className="admin-database-search-data-section">
          <div className="admin-database-search-bar">
            <input type="text" placeholder="Name: ex. CS 202, Ahmet" />
            <select>
              <option value="">Data Type</option>
              <option value="Midterm">Midterm</option>
              <option value="Exam">Exam</option>
            </select>
            <button className="admin-database-search-button">Search</button>
          </div>
          <div className="admin-database-data-table">
            <div className="admin-database-table-body">
              <div className="admin-database-table-row">
                  {createDatabaseItems()}
              </div>
            </div>
          </div>
        </div>
        <div className="admin-database-view-data-details">
          <h3>View Data Details</h3>
          <p>Select an entry from the table to see details.</p>
        </div>
      </div>

      {/* Bottom Section: Two panels side by side */}
      <div className="admin-database-database-bottom">
        {/* Left Panel: Create New Type */}
        <div className="admin-database-create-new-type">
          <h3>Create New Data</h3>
          <div className="admin-database-type-selector">
            <label>Select Type:</label>
            <select value={selectedType} onChange={handleTypeChange}>
              <option value="">--Select Type--</option>
              <option value="Exam">Exam</option>
              <option value="TA">TA</option>
              <option value="Quiz">Quiz</option>
            </select>
          </div>
          {selectedType === "Exam" && (
            <div className="admin-database-type-form">
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

              <button className="admin-database-create-type-button">Create</button>
            </div>
          )}

          {selectedType === "Quiz" && (
            <div className="admin-database-type-form">
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

              <button className="admin-database-create-type-button">Create</button>
            </div>
          )}

          {selectedType === "TA" && (
            <div className="admin-database-type-form">
              <label>TA Name</label>
              <input ref={newTaNameRef} type="text" placeholder="enter name" />

              <label>Surname</label>
              <input ref={newTaSurnameRef} type="text" placeholder="enter surname" />

              <label>Email</label>
              <input ref={newTaEmailRef} type="text" placeholder="enter email" />

              <label>ID</label>
              <input ref={newTaIdRef} type="text" placeholder="enter ID" />

              <label>Department</label>
              <select value={departmentSelection} onChange={(e) => {setDepartmentSelection(e.target.value)
                console.log("Selected Department:", e.target.value)

              }}>
                <option value="">Select Department</option>
                <option value="CS">CS</option>
                <option value="IE">IE</option>
                <option value="Other">Other</option>
              </select>

              <label>Select Type</label>
              <select value={selectTaWorktime} onChange={(e) => {setSelectTaWorktime(e.target.value)
                console.log("Selected Worktime For TA:", e.target.value)
              }}>
                <option value="">Select Type</option>
                <option value="FT">Full Time</option>
                <option value="PT">Part Time</option>
              </select>

              <label>Course Name</label>
              <input ref={newTaCourseNameRef} type="text" placeholder="enter course name" />

              <label>Phone Number</label>
              <input ref={newTaPhoneNumRef} type="text" placeholder="enter phone number" />

              <label>Class Year</label>
              <input ref={newTaClassYearRef} type="text" placeholder="enter class year" />

              <label>Login Informations</label>
              <input ref={newTaPasswordRef} type="password" placeholder="enter password" />

              <button className="admin-database-create-type-button" onClick={() => {
                createNewTa(
                  newTaNameRef.current.value,
                  newTaSurnameRef.current.value,
                  newTaEmailRef.current.value,
                  newTaIdRef.current.value,
                  departmentSelection,
                  newTaCourseNameRef.current.value,
                  newTaPhoneNumRef.current.value,
                  newTaClassYearRef.current.value,
                  newTaPasswordRef.current.value
                );
              }}>Create</button>
            </div>
          )}
        </div>
        
        {/* Right Panel: Dump New Data */}
        <div className="admin-database-dump-new-data">
          <h3>Dump New Data</h3>
          <div className="admin-database-upload-container">
            <div className="admin-database-drag-drop-area">
              <p>Drag &amp; Drop file here</p>
              <p>or</p>
              <label htmlFor="file-upload" className="admin-database-choose-file-label">
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
            <div className="admin-database-upload-buttons">
              <button className="admin-database-import-button">Import</button>
              <button className="admin-database-cancel-button">Cancel</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdminDatabasePage;
