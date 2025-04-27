import React, { useState, useRef } from "react";
import NavbarAdmin from "./NavbarAdmin";
import "./AdminDatabasePage.css";
import AdminDatabaseItem from "./AdminDatabaseItem";
import axios from "axios";
import { type } from "@testing-library/user-event/dist/type";

const AdminDatabasePage = () => {
  const [selectedType, setSelectedType] = useState("");
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

  // Refs for new Proctoring creation
  const newProctoringNameRef = useRef();
  const newProctoringCourseNameRef = useRef();
  const newProctoringSectionNumberRef = useRef();
  const newProctoringDateRef = useRef();
  const newProctoringStartTimeRef = useRef();
  const newProctoringEndTimeRef = useRef();
  const newProctoringClassroomsRef = useRef();
  const newProctoringTaCountRef = useRef();
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
      console.log("Creating new TA with the following details:");
      console.log(typeof newTaId);
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
      else {
        alert("TA created successfully!");
        console.log("TA created successfully:", response.data);
      }
    } catch (error) {
      console.error("There was an error with the ta creation:", error);
      alert("An error occurred. Please try again.");
    }

  }

  const createNewProctoring = async (newProctoringName, newProctoringCourseName, newProctoringSectionNumber, newProctoringDate, newProctoringStartTime, newProctoringEndTime, newProctoringClassrooms, newProctoringTaCount) => {
    try {
      console.log("Creating new Proctoring with the following details:");
      console.log(typeof newProctoringSectionNumber);
      const response = await axios.post("http://localhost:8080/classProctoring/createClassProctoring", {
        eventName: newProctoringName,
        courseName: newProctoringCourseName,
        sectionNo: parseInt(newProctoringSectionNumber, 10),
        startDate: newProctoringDate + " " + newProctoringStartTime + ":00",
        endDate: newProctoringDate + " " + newProctoringEndTime + ":00",
        classrooms: newProctoringClassrooms.split(","),
        taCount: parseInt(newProctoringTaCount, 10)
      });

      if (!response) {
        alert("Could not created the Proctoring. Try again.");
      } 
      else {
        alert("Proctoring created successfully!");
        console.log("Proctoring created successfully:", response.data);
      }
    } catch (error) {
      console.error("There was an error with the proctoring creation:", error);
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
              <option value="Proctoring">Proctoring</option>
              <option value="TA">TA</option>
            </select>
          </div>
          {selectedType === "Proctoring" && (
            <form className="admin-database-type-form" onSubmit={(e) => {
              e.preventDefault();
              createNewProctoring(
                newProctoringNameRef.current.value,
                newProctoringCourseNameRef.current.value,
                newProctoringSectionNumberRef.current.value,
                newProctoringDateRef.current.value,
                newProctoringStartTimeRef.current.value,
                newProctoringEndTimeRef.current.value,
                newProctoringClassroomsRef.current.value,
                newProctoringTaCountRef.current.value
              );
            }}>
              <label>Event Name</label>
              <input ref={newProctoringNameRef} type="text" placeholder="e.g., Midterm Exam" required/>

              <label>Course Name</label>
              <input ref={newProctoringCourseNameRef} type="text" placeholder="e.g., CS 202" required/>

              <label>Section Number</label>
              <input ref={newProctoringSectionNumberRef} type="number" min={0} placeholder="e.g., 1" required/>

              <label>Date</label>
              <input ref={newProctoringDateRef} type="date" required/>

              <label>Start Time</label>
              <input ref={newProctoringStartTimeRef} type="time" required/>

              <label>End Time</label>
              <input ref={newProctoringEndTimeRef} type="time" required/>

              <label>Classrooms</label>
              <input ref={newProctoringClassroomsRef} type="text" placeholder="e.g., EE-03,EA-534" pattern="^([A-Z]+-\d+)(,([A-Z]+-\d+))*$" title="Enter classrooms like EE-033,EA-56,B-012 — building code (capital letters), dash, room number" required/>

              <label>TA Count</label>
              <input ref={newProctoringTaCountRef} type="number" min={1} placeholder="e.g., 2" required/>

              <input type={"submit"} className="admin-database-create-type-button"/>
            </form>
          )}

          {selectedType === "TA" && (
            <form className="admin-database-type-form" onSubmit={(e) => {
              e.preventDefault();
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
            }}>
              <label>TA Name</label>
              <input ref={newTaNameRef} type="text" placeholder="enter name" required/>

              <label>Surname</label>
              <input ref={newTaSurnameRef} type="text" placeholder="enter surname" required/>

              <label>Email</label>
              <input ref={newTaEmailRef} type="email" placeholder="enter email" required />

              <label>ID</label>
              <input ref={newTaIdRef} type="number" min={0} placeholder="enter ID" required/>

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
              <input ref={newTaCourseNameRef} type="text" placeholder="enter course name" required />

              <label>Phone Number</label>
              <input ref={newTaPhoneNumRef} type="text" placeholder="enter phone number" pattern="^\+\d{1,3}-\d{3}-\d{3}-\d{2}-\d{2}$"  title="Phone number must be in the format +CountryCode-xxx-xxx-xx-xx" required/>

              <label>Class Year</label>
              <input ref={newTaClassYearRef} type="number" min={1} max={6} placeholder="enter class year" required/>

              <label>Login Informations</label>
              <input ref={newTaPasswordRef} type="password" placeholder="enter password" required />

              <input type="submit" value="Create"className="admin-database-create-type-button"/>
            </form>
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
