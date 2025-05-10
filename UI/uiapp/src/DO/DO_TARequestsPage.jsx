import React, { useState, useEffect } from "react";
import NavbarDO from "./NavbarDO";
import "./DO_TARequestsPage.css";
import axios from "axios";


/**
 * DO_TARequestsPage component
 * Allows the Dean’s Office to review approved TA requests and distribute TA counts to other departments.
 */
const DO_TARequestsPage = () => {
  const [selectedRequestId, setSelectedRequestId] = useState(null);
  const [departments, setDepartments] = useState([]);
  const [taCounts, setTACounts] = useState({});
  const [taRequests, setTARequests] = useState([]);

  const selected = taRequests.find((r) => r.requestId === selectedRequestId);

  useEffect(() => {
    fetchTARequests();
    fetchDepartmentsExcluding(999); // use a non-existing ID

  }, []);

  useEffect(() => {
    if (!selectedRequestId || taRequests.length === 0) return;

    const selected = taRequests.find((r) => r.requestId === selectedRequestId);
    const departmentId =
      selected?.senderDepartmentId ||
      selected?.sender?.departmentId ||
      selected?.departmentId;

    if (departmentId) {
      fetchDepartmentsExcluding(departmentId);
    } else {
      console.warn("No valid departmentId found for selected request.");
    }
  }, [selectedRequestId, taRequests]);



  /**
   * Submits selected TA counts to other departments for the selected request.
   * Validates input and sends data to backend for processing.
   */
  const handleSendToDepartments = async () => {
    if (!selectedRequestId) {
      alert("Please select a request first.");
      return;
    }

    const applications = Object.entries(taCounts)
      .filter(([_, count]) => count > 0)
      .map(([deptCode, count]) => {
        const dept = departments.find((d) => d.departmentCode === deptCode);
        return {
          visibleDepartmentId: dept?.departmentId,
          applicantCountLimit: count,
        };
      });

    // Safety checks
    if (applications.length === 0) {
      return alert("No departments selected or all TA counts are zero.");
    }

    if (applications.some((a) => !a.visibleDepartmentId)) {
      console.error("Missing departmentId in some applications:", applications);
      return alert("One or more departments could not be matched.");
    }

    try {
      console.log("Sending applications:", applications);
      const token = localStorage.getItem("token");
      const response = await axios.post(
        "http://localhost:8080/proctoringApplication/createProctoringApplications",
        applications,
        {
          params: {
            requestId: selected.requestId, classProctoringId: selected?.classProctoringId,
          },
          headers: {
            Authorization: `Bearer ${token}`
          }
        }
      );

      alert("Requests sent successfully!");
      fetchTARequests();

    } catch (error) {
      console.error("Failed to send applications:", error.response?.data || error.message);
      alert("Error while sending. Check console for details.");
    }
  };

  /**
   * Fetches all departments in the faculty, excluding the one provided.
   * Initializes TA counts to 0 for each department.
   */

  const fetchDepartmentsExcluding = async (excludeDepartmentId) => {
    try {
      const res = await axios.get(
        "http://localhost:8080/department/getAllDepartmentsInFaculty",
        {
          params: {
            facultyId: 1,
          },
        }
      );
      const filtered = res.data.filter(
        (d) => d.departmentId !== excludeDepartmentId
      );
      setDepartments(filtered);

      const initial = {};
      filtered.forEach((d) => (initial[d.departmentCode] = 0));
      setTACounts(initial);
    } catch (e) {
      console.error(e);
    }
  };

  /**
   * Fetches all approved TA requests that can be managed by the Dean’s Office.
   */
  const fetchTARequests = async () => {
    try {
      const token = localStorage.getItem("token");
      const res = await axios.get(
        "http://localhost:8080/taFromDeanRequest/getApprovedInstructorAdditionalTARequests",
        {
          headers: {
            Authorization: `Bearer ${token}`
          }
        }
      );
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
                const time = dateObj.toTimeString().slice(0, 5);

                return (
                  <button
                    key={req.requestId}
                    type="button"
                    className={`do-TA-list-item ${selectedRequestId === req.requestId ? "do-selected" : ""}`}
                    onClick={() => setSelectedRequestId(req.requestId)}
                  >
                    <span className="do-TA-li-date">
                      {dateObj.toLocaleDateString("en-US", {
                        weekday: "long",
                        year: "numeric",
                        month: "long",
                        day: "numeric",
                      })}
                    </span>

                    <span className="do-TA-li-course">
                      {req.classProctoringEventName}
                    </span>

                    <span className="do-TA-li-time">{time}</span>

                    <span className="do-TA-li-status">
                      {req.isApproved ? "Approved" : "Pending"}
                    </span>

                    <span className="do-TA-li-ta-count">
                      {req.taCountNeeded} TAs
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
                  <strong>Course Code:</strong> {selected.courseCode}
                </li>
                <li>
                  <strong>Course Name:</strong> {selected.courseName}
                </li>
                <li>
                  <strong>Proctoring Title:</strong>{" "}
                  {selected.classProctoringEventName}
                </li>
                <li>
                  <strong>Date:</strong>{" "}
                  {new Date(selected.classProctoringStartDate).toLocaleDateString("en-US", {
                    weekday: "long",
                    year: "numeric",
                    month: "long",
                    day: "numeric",
                  })}

                </li>
                <li>
                  <strong>Start Time:</strong>{" "}
                  {new Date(selected.classProctoringStartDate)
                    .toTimeString()
                    .slice(0, 5)}
                </li>
                <li>
                  <strong>End Time:</strong>{" "}
                  {new Date(selected.classProctoringEndDate).toTimeString().slice(0, 5)}
                </li>
                <li>
                  <strong>Description:</strong> {selected.description}
                </li>

                <li>
                  <strong>Sender:</strong> {selected.senderName} ({selected.senderEmail})
                </li>

                <li>
                  <strong>TA Count:</strong> {selected.taCountNeeded}
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
              <button
                className="do-TA-send-button"
                onClick={handleSendToDepartments}
                disabled={
                  !selected ||
                  Object.values(taCounts).reduce((sum, val) => sum + val, 0) > selected.taCountNeeded
                }
              >
                Send to Department Secretary
              </button>

              {selected &&
                Object.values(taCounts).reduce((sum, val) => sum + val, 0) > selected.taCountNeeded && (
                  <p style={{ color: "red", marginTop: "0.5rem" }}>
                    Total TA count exceeds the required amount ({selected.taCountNeeded}).
                  </p>
                )}


            </div>
          </div>
        </section>
      </main>
    </div>
  );
};

export default DO_TARequestsPage;
