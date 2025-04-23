import React, { useState, useEffect } from "react";
import Navbar from "./Navbar";
import "./DashboardPage.css";
import PendingRequestItem from "../PendingRequestItem";
import ReceivedRequestItem from "../ReceivedRequestItem";
import WorkloadEntryItem from "../WorkloadEntryItem";
import ProctoringDutyItem from "../ProctoringDutyItem";
import axios from "axios";

const DashboardPage = () => {
  const [activeTab, setActiveTab] = useState("pending");
  const [selectedRequest, setSelectedRequest] = useState(null);
  const [selectedProctoring, setSelectedProctoring] = useState(null);
  const [selectedProctoringId, setSelectedProctoringId] = useState(null);
  const [tasProctorings, setTasProctorings] = useState([]); // Initialize as an empty array

  const handleSelect = (id) => {
    setSelectedProctoringId(id); // Only one selected at a time
  };


  const handleTabClick = (tab) => {
    setActiveTab(tab);
    setSelectedRequest(null);
    setSelectedProctoring(null);
  };

  const createPendingRequest = (request, index) => {
    return (
      <div key={index} onClick={() => setSelectedRequest(request)}>
        <PendingRequestItem {...request} />
      </div>
    );
  };

  const createReceivedRequest = (request, index) => {
    return (
      <div key={index} onClick={() => setSelectedRequest(request)}>
        <ReceivedRequestItem {...request} />
      </div>
    );
  };

  const createWorkloadEntry = (courseCode, taskTitle, date, duration, comment, status) => {
    return <WorkloadEntryItem courseCode={courseCode} taskTitle={taskTitle} date={date} duration={duration} comment={comment} status={status} />;
  }

  const fetchTasProctorings = async () => {
    try {
      const response = await axios.get("http://localhost:8080/classProctoringTARelation/getTAsClassProctorings?id=2"); // Adjust the URL as needed
      setTasProctorings(response.data);
      console.log(tasProctorings);
    } catch (error) {
      console.error("Error fetching tasks:", error);
    }
  };

  useEffect(() => {
    fetchTasProctorings(tasProctorings);
  }, []);

  const handleLockedStatusChange = async (id) => {
    try {
      const proctoringToBeLocked = tasProctorings.find((duty) => duty.classProctoringDTO.id === id);
      console.log(proctoringToBeLocked);
      const responseLocked = await axios.put("http://localhost:8080/classProctoringTARelation/updateTAsClassProctorings?id=2", {
        classProctoringDTO: {
          id: proctoringToBeLocked.classProctoringDTO.id,
        },
        isOpenToSwap: !proctoringToBeLocked.isOpenToSwap,
      });

      if (!responseLocked.data) {
        alert("Could not locked the swap. Try again.");
      }
      fetchTasProctorings(tasProctorings);
    } catch (error) {
      console.error("There was an error with the login request:", error);
      alert("An error occurred. Please try again.");
    }
  }
  // Sample data for pending requests
  const pendingRequests = [
    {
      date: { month: "March", day: "23", weekday: "Fri" },
      time: { start: "8:00AM", end: "10:30AM" },
      role: "Quiz Proctoring",
      duration: 1.5,
      name: "Ali Kılıç",
      email: "ali.kilic@ug.bilkent.edu.tr",
      status: "Pending TA’s answer",
      onCancelHandler: () => console.log("Ali's request canceled"),
      onAcceptHandler: () => console.log("Ali's request accepted"),
      onRejectHandler: () => console.log("Ali's request rejected"),
    },
    {
      date: { month: "April", day: "2", weekday: "Tue" },
      time: { start: "1:00PM", end: "3:00PM" },
      role: "Midterm Invigilation",
      duration: 2,
      name: "Ayşe Yılmaz",
      email: "ayse.yilmaz@ug.bilkent.edu.tr",
      status: "Pending TA’s answer",
      onCancelHandler: () => console.log("Ayşe's request canceled"),
      onAcceptHandler: () => console.log("Ayşe's request accepted"),
      onRejectHandler: () => console.log("Ayşe's request rejected"),
    },
  ];
  //Sample data for workload entries
  const workloadEntries = [
    {
      courseCode: "CS 476",
      taskTitle: "Quiz Reading",
      date: "15/02/2025",
      duration: 3.5,
      comment: "It took longer then expected.",
    },
    {
      courseCode: "CS 319",
      taskTitle: "Assignment Check",
      date: "20/02/2025",
      duration: 2,
      comment: "",
    },
  ];
  // Sample data for proctoring duties
  const proctoringDuties = [
    { id: 1, title: "Quiz Proctor", date: "15/03/2025", time: "10.30 - 11.30", location: "EE - 214", status: "locked" },
    { id: 2, title: "Quiz Proctor", date: "16/03/2025", time: "9.30 - 10.30", location: "EE - 312", status: "open" },
    { id: 3, title: "Midterm Proctor", date: "21/03/2025", time: "19.00 - 21.00", location: "B - 103", status: "open" },
    { id: 4, title: "Midterm Proctor", date: "21/03/2025", time: "19.00 - 21.00", location: "B - 104", status: "open" },
  ];
  //--------------------------------------------------------------

  return (
    <div className="ta-dashboard-dashboard-page">
      <Navbar />
      <div className="ta-dashboard-dashboard-grid">
        {/* LEFT SIDE */}
        <div className="ta-dashboard-dashboard-left">
          {/* Tabs */}
          <div className="ta-dashboard-left-up-panel">
            <div className="ta-dashboard-tab-bar">
              <button onClick={() => handleTabClick("pending")} className={activeTab === "pending" ? "active" : ""}>Pending Requests</button>
              <button onClick={() => handleTabClick("received")} className={activeTab === "received" ? "active" : ""}>Received Requests</button>
              <button onClick={() => handleTabClick("tasks")} className={activeTab === "tasks" ? "active" : ""}>Tasks</button>
              <button onClick={() => handleTabClick("proctorings")} className={activeTab === "proctorings" ? "active" : ""}>Proctorings</button>
            </div>

            {/* Top Left Panel */}
            <div className="ta-dashboard-tab-content">
              {activeTab === "pending" && (
                <div>
                  {pendingRequests.map((req, index) => createPendingRequest(req, index))}

                </div>
              )}
              {activeTab === "received" && (
                <div>
                  {pendingRequests.map((req, index) => createReceivedRequest(req, index))}

                </div>
              )}
              {activeTab === "tasks" && (
                <div>{workloadEntries.map((ent, idx) =>
                  createWorkloadEntry(
                    ent.courseCode,
                    ent.taskTitle,
                    ent.date,
                    ent.duration,
                    ent.comment,
                    "rejected" // Assuming all entries are accepted for simplicity
                  )
                )}</div>
              )}
              {activeTab === "proctorings" && (
                <div>
                  {tasProctorings.map((duty) => (
                    <ProctoringDutyItem
                      key={duty.classProctoringDTO.id}
                      duty={duty}
                      isSelected={selectedProctoringId === duty.classProctoringDTO.id}
                      onSelect={handleSelect}
                      onLockedStatusChange={() => {
                        handleLockedStatusChange(duty.classProctoringDTO.id); // Function to handle the locked status change
                        console.log(`Locked status changed for duty ID: ${duty.classProctoringDTO.id}`); //Will push new lock status of the duty to the database
                      }}
                    />
                  ))}
                </div>
              )}
            </div>
          </div>
          {/* Bottom Left Panel */}
          <div className="ta-dashboard-bottom-left">
            {activeTab === "pending" || activeTab === "received" ? (
              <div className="ta-dashboard-details-panel">
                <h3>Details</h3>
                {selectedRequest ? (
                  <div>
                    <p><strong>Name:</strong> {selectedRequest.name}</p>
                    <p><strong>Email:</strong> {selectedRequest.email}</p>
                    <p><strong>Date:</strong> {selectedRequest.date.weekday}, {selectedRequest.date.month} {selectedRequest.date.day}</p>
                    <p><strong>Time:</strong> {selectedRequest.time.start} - {selectedRequest.time.end}</p>
                    <p><strong>Role:</strong> {selectedRequest.role}</p>
                    <p><strong>Status:</strong> {selectedRequest.status}</p>
                  </div>
                ) : (
                  <p className="ta-dashboard-placeholder">[ Click a request to see its details ]</p>
                )}

              </div>
            ) : activeTab === "tasks" ? (
              <div className="ta-dashboard-task-entry-form">
                <h3>Enter Task</h3>
                <form>
                  <label>Task Type</label>
                  <select>
                    <option>Quiz Reading</option>
                    <option>Homework Grading</option>
                    <option>Project Evaluation</option>
                  </select>

                  <label>Time Spent</label>
                  <div className="ta-dashboard-time-inputs">
                    <input type="number" placeholder="Hours" />
                    <input type="number" placeholder="Minutes" />
                  </div>

                  <label>Details</label>
                  <textarea placeholder="Optional comments" />

                  <button type="submit">Send</button>
                </form>
              </div>
            ) : activeTab === "proctorings" ? (
              <div className="ta-dashboard-swap-form">
                <h3>Proctoring Information</h3>
                {selectedProctoring ? (
                  <form>
                    <label>Select TA</label>
                    <select>
                      <option>TA 1</option>
                      <option>TA 2</option>
                    </select>

                    <label>Details</label>
                    <textarea placeholder="Reason for swap" />

                    <button type="submit">Send Swap Request</button>
                  </form>
                ) : (
                  <p className="ta-dashboard-placeholder">[ Select a proctoring duty to send swap request ]</p>
                )}
              </div>
            ) : null}
          </div>
        </div>

        {/* RIGHT SIDE */}
        <div className="ta-dashboard-dashboard-right">
          <div className="ta-dashboard-notifications">
            <h3>Notifications</h3>
            <div className="ta-dashboard-placeholder">[ Pull real-time notifications from DB ]</div>
          </div>

          <div className="ta-dashboard-stats-box">
            <div className="ta-dashboard-stat">Total Hours Worked: 7</div>
            <div className="ta-dashboard-stat">Hours Awaiting Approval: 2.5</div>
            <div className="ta-dashboard-stat">Upcoming Proctoring Duties: 1</div>
            <div className="ta-dashboard-stat">Days Until Next Proctoring: 3</div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DashboardPage;
