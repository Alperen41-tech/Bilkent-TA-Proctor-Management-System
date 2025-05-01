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

  const selected = taRequests.find((r) => r.requestId === selectedRequestId);

  useEffect(() => {
    fetchDepartments();
    fetchTARequests();
  }, []);

  const handleSendToDepartments = () => {
    console.log("Sending TA requirements:", taCounts);
  };

  const fetchDepartments = async () => {
    try {
      const res = await axios.get(
        "http://localhost:8080/department/getAllDepartmentsExcept",
        { params: { facultyId: 1, departmentId: 1 } }
      );
      setDepartments(res.data || []);
      const initial = {};
      res.data.forEach((d) => (initial[d.departmentCode] = 0));
      setTACounts(initial);
    } catch (e) {
      console.error(e);
    }
  };

  const fetchTARequests = async () => {
    try {
      const res = await axios.get(
        "http://localhost:8080/taFromDeanRequest/getApprovedInstructorAdditionalTARequests",
        { params: { receiverId: 9 } });
      setTARequests(res.data || []);
    } catch (e) {
      console.error(e);
    }
  };

  return (
    <div className="do-TA-ta-page">
      <NavbarDO />
      <main className="do-TA-grid">
        <section className="do-TA-col do-left">
          <div className="do-TA-card">
            <h3 className="do-TA-card-title">In-Progress TA Requests</h3>
            <div className="do-TA-list">
              {taRequests.map((req) => {
                const dateObj = new Date(req.classProctoringStartDate);
                const month = dateObj.toLocaleString("default", { month: "short" });
                const day = dateObj.getDate();
                const wd = dateObj.toLocaleString("default", { weekday: "short" });
                const time = dateObj.toTimeString().slice(0, 5);

                return (
                  <button
                    key={req.requestId}
                    type="button"
                    className={`do-TA-list-item ${
                      selectedRequestId === req.requestId ? "do-selected" : ""
                    }`}
                    onClick={() => setSelectedRequestId(req.requestId)}
                  >
                    <span className="do-TA-li-date">{`${month} ${day} (${wd})`}</span>
                    <span className="do-TA-li-time">{time}</span>
                    <span className="do-TA-li-course">
                      {req.courseName ?? req.courseCode ?? req.classProctoringEventName}
                    </span>
                    <span className="do-TA-li-status">
                      {req.isApproved ? "Approved" : "Pending"}
                    </span>
                  </button>
                );
              })}
            </div>
          </div>

          <div className="do-TA-card">
            <h3 className="do-TA-card-title">Details</h3>
            {selected ? (
              <ul className="do-TA-details">
                <li>
                  <strong>Course:</strong>{" "}
                  {selected.courseName ??
                    selected.courseCode ??
                    selected.classProctoringEventName}
                </li>
                <li>
                  <strong>Date:</strong>{" "}
                  {new Date(selected.classProctoringStartDate).toLocaleDateString()}
                </li>
                <li>
                  <strong>Time:</strong>{" "}
                  {new Date(selected.classProctoringStartDate)
                    .toTimeString()
                    .slice(0, 5)}
                </li>
                <li>
                  <strong>Description:</strong> {selected.description}
                </li>
                <li>
                  <strong>TA Count:</strong> {selected.taCount}
                </li>
                <li>
                  <strong>Sender:</strong> {selected.senderName} ({selected.senderEmail})
                </li>
                <li>
                  <strong>Status:</strong>{" "}
                  {selected.isApproved
                    ? "Approved"
                    : selected.isComplete
                    ? "Completed"
                    : "Pending"}
                </li>
              </ul>
            ) : (
              <div className="do-TA-placeholder">Click a request to see its details</div>
            )}
          </div>
        </section>

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
                      setTACounts({
                        ...taCounts,
                        [dep.departmentCode]: parseInt(e.target.value, 10),
                      })
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
