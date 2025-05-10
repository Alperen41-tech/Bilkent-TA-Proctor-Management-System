import React, { useEffect, useState, useRef, use } from "react";
import "./AdminLogsPage.css";
import NavbarAdmin from "./NavbarAdmin";
import AdminLogItem from "./AdminLogItem";
import axios from "axios";
import { set } from "date-fns";

/**
 * AdminLogsPage component
 * Displays admin activity logs, supports filtering by date, type, and message.
 * Also allows setting global constraints (term & proctoring cap).
 */

const AdminLogsPage = () => {
  const [logs, setLogs] = useState([]);
  const [searchText, setSearchText] = useState("");
  const [logTypeFilter, setLogTypeFilter] = useState("ALL");
  const [globalSemester, setGlobalSemester] = useState(null);
  const [globalProctoringCap, setGlobalProctoringCap] = useState(null);

  // Refs for start and end date inputs
  const startDateRef = useRef();
  const endDateRef = useRef();
  const globalSemesterRef = useRef();
  const globalProctoringCapRef = useRef();

  /**
 * Fetches logs from the backend within the specified date range.
 * Sorts logs by newest first.
 */

  const fetchAdminLogs = async () => {
    try {
      const response = await axios.post("http://localhost:8080/log/getByDate", {
        startDate: startDateRef.current.value ? startDateRef.current.value + " 00:00:00" : new Date().toISOString().split("T")[0] + " 00:00:00",
        endDate: endDateRef.current.value ? endDateRef.current.value + " 23:59:59" : new Date().toISOString().split("T")[0] + " 23:59:59",
      });
      if (response.data) {
        const sortedLogs = response.data.sort((a, b) => new Date(b.logDate) - new Date(a.logDate));
        setLogs(sortedLogs);
        console.log("Logs fetched successfully:", response.data);
      }
      else {
        console.error("No logs found or error in response:", response);
      }
    } catch (error) {
      console.error("Error fetching admin logs:", error);
    }
  }

  /**
   * Clears log view and refetches logs using current date inputs.
   */

  const handleSetConstraints = () => {
    setLogs([]);
    fetchAdminLogs();
  };
  /**
   * Creates filtered log entries based on search and type filters.
   */

  const createLogsItems = () => {
    return logs
      .filter((log) => {
        const logTypeMatch = logTypeFilter === "ALL" || log.logType === logTypeFilter;
        const messageMatch = log.message.toLowerCase().includes(searchText.toLowerCase());
        return logTypeMatch && messageMatch;
      })
      .map((item) => (
        <AdminLogItem
          key={item.id}
          logType={item.logType}
          message={item.message}
          logDate={item.logDate}
        />
      ));
  };

  useEffect(() => {
    fetchAdminLogs();
    fetchGlobalData();
  }, []);

  const handleSetTerm = async () => {
    const term = globalSemesterRef.current.value;
    console.log(term);
    try {
      const response = await axios.post(`http://localhost:8080/generalVariable/changeSemester?semester=${term}`);
      if (response.data) {
        console.log("Term set successfully:", response.data);
        fetchGlobalData();
      } else {
        console.error("Error setting term:", response);
      }
    } catch (error) {
      console.error("Error setting term:", error);
    }
  };
  /**
   * Sends request to update the global proctoring cap.
   */

  const handleSetProctoringCap = async () => {
    const proctoringCap = globalProctoringCapRef.current.value;
    try {
      const response = await axios.post(`http://localhost:8080/generalVariable/changeProctoringCap?proctoringCap=${proctoringCap}`);
      if (response.data) {
        console.log("Proctoring cap set successfully:", response.data);
        alert("Proctoring cap set successfully!");
        fetchGlobalData();
      } else {
        console.error("Error setting proctoring cap:", response);
      }
    } catch (error) {
      console.error("Error setting proctoring cap:", error);
    }
  };
  /**
   * Fetches current global variables (semester, proctoring cap).
   */

  const fetchGlobalData = async () => {
    try {
      const response = await axios.get("http://localhost:8080/generalVariable/getGeneralVariable");
      if (response.data) {
        console.log("Global data fetched successfully:", response.data);
        setGlobalSemester(response.data.semester);
        setGlobalProctoringCap(response.data.proctoringCap);
      } else {
        console.error("Error fetching global data:", response);
      }
    } catch (error) {
      console.error("Error fetching global data:", error);
    }
  };

  return (
    <div className="logs-container">
      <NavbarAdmin />
      <div className="logs">
        <div className="logs-content">
          <div className="logs-table">
            <div className="table-header">
              <input
                type="text"
                placeholder="Search by message, e.g., CS 202"
                className="search-input"
                value={searchText}
                onChange={(e) => setSearchText(e.target.value)}
              />
              <select
                className="data-type-select"
                value={logTypeFilter}
                onChange={(e) => setLogTypeFilter(e.target.value)}
              >
                <option value="ALL">All</option>
                <option value="CREATE">Create</option>
                <option value="UPDATE">Update</option>
                <option value="DELETE">Delete</option>
                <option value="LOGIN">Login</option>
                <option value="LOGOUT">Logout</option>
              </select>

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
              <button onClick={() => handleSetConstraints()} className="admin-log-set-constraints-button">Set Constraints</button>
            </div>
          </div>

          <div className="set-globals-section">
            <h4>Set Globals</h4>
            <form className="globals-inputs" onSubmit={(e) => { e.preventDefault(); handleSetTerm(); }}>
              <label>Term - {globalSemester}</label>
              <input ref={globalSemesterRef} type="text" placeholder="e.g.,2024-2025 Spring" pattern="^\d{4}-\d{4} (Spring|Fall)$" title="Format must be: YYYY-YYYY Season (e.g., 2024-2025 Spring)" required />
              <button className="set-term-button" type="submit">Set Term</button>
            </form>
            <form className="globals-inputs" onSubmit={(e) => { e.preventDefault(); handleSetProctoringCap(); }}>
              <label>Proctoring Cap - {globalProctoringCap}</label>
              <input ref={globalProctoringCapRef} type="number" min={0} placeholder="e.g., 6" required />
              <button className="set-proctoring-button" type="submit">Set Proctoring Cap</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdminLogsPage;
