import React, { useState } from "react";
import "./AdminLogsPage.css";
import NavbarAdmin from "./NavbarAdmin";
import AdminDatabaseItem from "./AdminDatabaseItem";

const AdminLogsPage = () => {
  const [logs, setLogs] = useState([]); // Empty list, no hardcoded data

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


  const createLogsDatabaseItems = () => {
    return(
    adminDatabaseItems.map((item) => (
      <AdminDatabaseItem
        key={`${item.type}-${item.data.id}`}
        type={item.type}
        data={item.data}
        onDelete={(id) => console.log(`Deleted ${item.type} with ID: ${id}`)}
        onSelect={(data) => false}
        isSelected={false}
        inLog={true} // Assuming this is not a log item
      />
    )))
  }
  return (
    <div className="logs-container">
      <NavbarAdmin />
      <div className="logs">
        <div className="logs-content">
          <div className="logs-table">
            <div className="table-header">
              <input
                type="text"
                placeholder="Search by name, ex. CS 202"
                className="search-input"
              />
              <select className="data-type-select">
                <option value="Quiz Proctoring">Quiz Proctoring</option>
                <option value="Exam Proctoring">Exam Proctoring</option>
              </select>
              <button className="search-button">Search</button>
            </div>
            <div className="table-body">
                  {createLogsDatabaseItems()}
            </div>
          </div>

          <div className="set-globals">
            <h4>Set Globals</h4>
            <div className="globals-inputs">
              <label>Term - Spring 2025</label>
              <input type="text" placeholder="e.g., Spring" />
              <button className="set-term-button">Set Term</button>
            </div>
            <div className="globals-inputs">
              <label>Proctoring Cap</label>
              <input type="text" placeholder="e.g., Spring" />
              <button className="set-proctoring-button">Set Proctoring Cap</button>
            </div>
          </div>
        </div>

        <div className="log-details">
          <h3>View Log Details</h3>
          <div className="placeholder">[ Logs data from DB ]</div>
          <div className="action-buttons">
            <button className="create-report-button">Create Annual Report</button>
            <button className="export-logs-button">Export Logs</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdminLogsPage;
