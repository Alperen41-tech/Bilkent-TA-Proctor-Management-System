import React, { useEffect, useState, useRef } from "react";
import "./AdminLogsPage.css";
import NavbarAdmin from "./NavbarAdmin";
import AdminLogItem from "./AdminLogItem";
import axios from "axios";
import { set } from "date-fns";

const AdminLogsPage = () => {
  const [logs, setLogs] = useState([]); 

  // Refs for start and end date inputs
  const startDateRef = useRef();
  const endDateRef = useRef();
  const logTypeRef = useRef();
  //----------------------------------------------------------

  const fetchAdminLogs = async () => {
    try {
      const response = await axios.post("http://localhost:8080/log/getByDate", {
        startDate: startDateRef.current.value ? startDateRef.current.value + " 00:00:00": new Date().toISOString().split("T")[0] + " 00:00:00",
        endDate: endDateRef.current.value ? endDateRef.current.value + " 23:59:59" : new Date().toISOString().split("T")[0] + " 23:59:59",
      }); 
      if(response.data) {
        setLogs(response.data);
        console.log("Logs fetched successfully:", response.data);
      }
      else {
        console.error("No logs found or error in response:", response);
      }
    } catch (error) {
      console.error("Error fetching admin logs:", error);
    }
  }

  const handleSetConstraints = () => {
    setLogs([]);
    fetchAdminLogs();
  };

  const createLogsItems = () => {
    return(
    logs.filter((item) => {
      
    }).map((item) => (
      <AdminLogItem
        key={item.id}
        logType={item.logType}
        message={item.message}
        logDate={item.logDate}
      />
    )))
  };

  useEffect(() => {
    fetchAdminLogs();
  }, []);

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
              <select ref={logTypeRef} className="data-type-select">
                <option value="CREATE">Create</option>
                <option value="UPDATE">Update</option>
                <option value="DELETE">Delete</option>
                <option value="LOGIN">Login</option>
                <option value="LOGOUT">Logout</option>
              </select>
              <button className="search-button">Search</button>
            </div>
            <div className="table-body">
                  {createLogsItems()}
            </div>
          </div>

          
        </div>

        <div className="log-details">
          <div className="log-constraints-section">
            <h3>Set Constraints for Logs</h3>
            <div className="admin-logs-constraint-inputs">
              <label>Start Date</label>
              <input ref={startDateRef} type="date" className="start-date-input" />
              <label>End Date</label>
              <input ref={endDateRef} type="date" className="end-date-input" />
              <button onClick={()=>handleSetConstraints()} className="admin-log-set-constraints-button">Set Constraints</button>
            </div>
          </div>

          <div className="set-globals-section">
            <h4>Set Globals</h4>
            <div className="globals-inputs">
              <label>Term - Spring 2025</label>
              <input type="text" placeholder="e.g., Spring" />
              <button className="set-term-button">Set Term</button>
            </div>
            <div className="globals-inputs">
              <label>Proctoring Cap</label>
              <input type="text" placeholder="e.g., 6" />
              <button className="set-proctoring-button">Set Proctoring Cap</button>
            </div>
          </div>
        </div>

      </div>
    </div>
  );
};

export default AdminLogsPage;
