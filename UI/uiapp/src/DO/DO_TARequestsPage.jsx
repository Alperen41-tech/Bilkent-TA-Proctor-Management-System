// DO_TARequestsPage.jsx
import React, { useState, useEffect } from "react";
import NavbarDO from "./NavbarDO";
import "./DO_TARequestsPage.css";
import axios from "axios";


const DO_TARequestsPage = () => {
  const [selectedRequestId, setSelectedRequestId] = useState(null);

  const [departments, setDepartments] = useState([]);
  const [taCounts, setTACounts] = useState({});
  const [taRequests, setTARequests] = useState([]);


  const selected = taRequests.find((r) => r.id === selectedRequestId);



  useEffect(() => {
    fetchDepartments();
    fetchTARequests();
  }, []);


  const handleSendToDepartments = () => {
    console.log("Sending TA requirements:", taCounts);
    //////
  };


  const fetchDepartments = async () => {
    try {
      const response = await axios.get('http://localhost:8080/department/getAllDepartmentsExcept', {
        params: { facultyId: 1, departmentId: 1 }, // Adjust facultyId dynamically as needed
      });
      setDepartments(response.data || []);

      // Initialize taCounts with 0 for each department
      const initialCounts = {};
      response.data.forEach(dep => {
        initialCounts[dep.departmentCode] = 0;
      });
      setTACounts(initialCounts);
    } catch (error) {
      console.error('Error fetching departments:', error);
    }
  };

  const fetchTARequests = async () => {
    try {
      const response = await axios.get("http://localhost:8080/taFromDeanRequest/getApprovedInstructorAdditionalTARequests", {
        params: { receiverId: 9 } // <-- Use real dean's user ID here
      });
      console.log("Fetched TA Requests: ", response.data);
      setTARequests(response.data || []);
    } catch (error) {
      console.error("Error fetching TA requests:", error);
    }
  };


  return (
    <div className="do-TA-ta-page">
      <NavbarDO />

      <main className="do-TA-grid">
        {/* ---------- LEFT COLUMN ---------- */}
        <section className="do-TA-col do-left">
          {/* -- Request List -- */}
          {/* -- Request List -- */}
          <div className="do-TA-card">
            <h3 className="do-TA-card-title">In-Progress TA Requests</h3>
            <div className="do-TA-list">
              {taRequests.map((req) => {
                const dateObj = new Date(req.date);
                const month = dateObj.toLocaleString("default", { month: "short" });
                const day = dateObj.getDate();
                const weekday = dateObj.toLocaleString("default", { weekday: "short" });
                const time = dateObj.toTimeString().slice(0, 5);

                return (
                  <button
                    key={req.id}
                    type="button"
                    className={`do-TA-list-item ${selectedRequestId === req.id ? "do-selected" : ""}`}
                    onClick={() => setSelectedRequestId(req.id)}
                  >
                    <span className="do-TA-li-date">{`${month} ${day} (${weekday})`}</span>
                    <span className="do-TA-li-time">{time}</span>
                    <span className="do-TA-li-course">{req.course}</span>
                    <span className="do-TA-li-status">{req.status}</span>
                  </button>
                );
              })}

            </div>
          </div>


          {/* -- Details -- */}
          <div className="do-TA-card">
            <h3 className="do-TA-card-title">Details</h3>
            {selected ? (
              <ul className="do-TA-details">
                <li><strong>Date:</strong> {new Date(selected.date).toLocaleDateString()}</li>
                <li><strong>Time:</strong> {new Date(selected.date).toTimeString().slice(0, 5)}</li>
                <li><strong>Course:</strong> {selected.course}</li>
                <li><strong>Faculty:</strong> {selected.faculty}</li>
                <li><strong>Department:</strong> {selected.department}</li>
                <li><strong>Status:</strong> {selected.status}</li>
              </ul>

            ) : (
              <div className="do-TA-placeholder">Click a request to see its details</div>
            )}
          </div>
        </section>

        {/* ---------- RIGHT COLUMN ---------- */}
        <section className="do-TA-col do-right">
          <div className="do-TA-card">
            <h3 className="do-TA-card-title">Choose Other Departments TA Number</h3>
            <div className="do-TA-ta-inputs">
              {departments.map((dep) => (
                <div key={dep.departmentCode} className="do-TA-dep-row">
                  <label>{dep.departmentName}</label>
                  <input
                    type="number"
                    min="0"
                    value={taCounts[dep.departmentCode] || 0}
                    onChange={(e) =>
                      setTACounts({ ...taCounts, [dep.departmentCode]: parseInt(e.target.value, 10) })
                    }
                  />
                </div>
              ))}

              <button className="do-TA-send-button" onClick={handleSendToDepartments}>
                Send to Department Secretary
              </button>


            </div>

          </div>

        </section>
      </main>
    </div>
  );
};

export default DO_TARequestsPage;