import React, { useState, useEffect, useRef } from "react";
import NavbarINS from "./NavbarINS";
import "./INS_DashboardPage.css";
import PendingRequestItem from "../PendingRequestItem";
import ReceivedRequestItem from "../ReceivedRequestItem";
import WorkloadEntryItem from "../WorkloadEntryItem";
import axios from "axios";



const INS_DashboardPage = () => {
  const [activeTab, setActiveTab] = useState("pending");
  const [selectedRequest, setSelectedRequest] = useState(null);
  const [taskTypes, setTaskTypes] = useState([]);

  const newTaskTypeNameRef = useRef();
  const newTaskLimitRef = useRef();
  const selectedForDeleteTaskType = useRef();

  const handleTabClick = (tab) => {
    setActiveTab(tab);
    setSelectedRequest(null);
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

  const postTaskType = async (taskTypeName, taskLimit) => {
    try {
      const response = await axios.post("http://localhost:8080/taskType/createTaskType?courseId=3", {
        courseName: "Cacirology",
        taskTypeName: taskTypeName,
        taskLimit: parseInt(taskLimit,10),       
      });

      if (!response.data) {
        alert("Could not created the Task Type. Try again.");
      } 
      else {
        alert("Task Type created successfully!");
        console.log("Task Type created successfully:", response.data);
        fetchTaskTypes(); 
      }
    } catch (error) {
      console.error("There was an error with the Task Type creation:", error);
      alert("An error occurred. Please try again.");
    }
  };
  const deleteTaskType = async (taskTypeName) => {
    try {
      const response = await axios.delete("http://localhost:8080/taskType/deleteTaskType?", {
           params: {
            courseId: 3,
            taskTypeName: taskTypeName,
           }
      });

      if (!response.data) {
        alert("Could not delete the Task Type. Try again.");
      } 
      else {
        alert("Task Type deleted successfully!");
        console.log("Task Type deleted successfully:", response.data);
        fetchTaskTypes();
      }
    } catch (error) {
      console.error("There was an error with the Task Type deletion:", error);
      alert("An error occurred. Please try again.");
    }
  };

  const fetchTaskTypes = async () => {
    try {
      const response = await axios.get("http://localhost:8080/taskType/getTaskTypeNames?courseId=3");
      if (response.data) {
        console.log("Fetched Task Types:", response.data);
        setTaskTypes(response.data);
        
      } else {
        alert("Could not fetch Task Types. Try again.");
      }
      

    }catch (error) {
      console.error("There was an error with fetching Task Types:", error);
      alert("An error occurred. Please try again.");
    }
  };


  /// normally there is no email part but inst should see who did what for now it is like this
  const workloadEntries = [
    {
      courseCode: "CS 476",
      taskTitle: "Quiz Reading",
      date: "15/02/2025",
      duration: 3.5,
      comment: "someTA1@ug.bilkent.edu.tr",
      status: "accepted",
    },
    {
      courseCode: "CS 319",
      taskTitle: "Assignment Check",
      date: "20/02/2025",
      duration: 2,
      comment: "someTA2@ug.bilkent.edu.tr",
      status: "rejected",
    },
  ];



  // örnek datalar pending req
  const pendingRequests = [
    {
      date: { month: "Feb", day: "10", weekday: "Fri" },
      time: { start: "8:00AM", end: "10:30AM" },
      role: "Paid Proctoring Request",
      duration: 1.5,
      name: "Ali Kılıç",
      email: "ali.kilic@ug.bilkent.edu.tr",
      status: "Pending Dean's Office’s answer",
      onCancelHandler: () => console.log("Ali's request canceled"),
      onAcceptHandler: () => console.log("Ali's request accepted"),
      onRejectHandler: () => console.log("Ali's request rejected"),
    },
    {
      date: { month: "May", day: "3", weekday: "Wed" },
      time: { start: "07:00PM", end: "09:00PM" },
      role: "Paid Proctoring Request",
      duration: 2,
      name: "Ayşe Yılmaz",
      email: "ayse.yilmaz@ug.bilkent.edu.tr",
      status: "Pending Dean's Office’s answer",
      onCancelHandler: () => console.log("Ayşe's request canceled"),
      onAcceptHandler: () => console.log("Ayşe's request accepted"),
      onRejectHandler: () => console.log("Ayşe's request rejected"),
    },
  ];

  useEffect(() => {
    fetchTaskTypes();
  }, []);

  return (
    <div className="dashboard-page">
      <NavbarINS />
      <div className="dashboard-grid">
        {/* LEFT SIDE */}
        <div className="dashboard-left">
          {/* Tabs */}
          <div className="top-left">
            <div className="tab-bar">
              <button onClick={() => handleTabClick("pending")} className={activeTab === "pending" ? "active" : ""}>Pending Requests</button>
              <button onClick={() => handleTabClick("received")} className={activeTab === "received" ? "active" : ""}>Received Requests</button>
              <button onClick={() => handleTabClick("tasks")} className={activeTab === "tasks" ? "active" : ""}>Tasks</button>
            </div>

            {/* Top Left Panel */}
            <div className="tab-content">
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
                    ent.status // Assuming all entries are accepted for simplicity
                  )
                )}</div>
              )}


            </div>
          </div>
          {/* Bottom Left Panel */}
          <div className="bottom-left">
            {activeTab === "pending" || activeTab === "received" ? (
              <div className="details-panel">
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
  <p className="placeholder">[ Click a request to see its details ]</p>
)}

              </div>
            ) : activeTab === "tasks" ? (
              <div className="bottom-left-task">
                <div className="task-type-create-form">
                  <h3>Create Task Type</h3>
                  <form onSubmit={(e) => {postTaskType(newTaskTypeNameRef.current.value, newTaskLimitRef.current.value); e.preventDefault();}}>
                    <label>Task Type</label>
                    <input ref={newTaskTypeNameRef} type="text" placeholder="Task Type" />
                    <label>Maximum Time Limit</label>
                    <div className="time-inputs">
                      <input ref={newTaskLimitRef} type="number" placeholder="Hours" min={0} />
                    </div>
                    <button className="button" type="submit">Create Task Type</button>
                  </form>
                </div>
                <div className="task-type-delete-form">
                  <h3>Delete Task Type</h3>
                  <label>Select Task Type</label>
                  <form onSubmit={(e) => {deleteTaskType(selectedForDeleteTaskType.current.value); e.preventDefault();}}>
                    <select ref={selectedForDeleteTaskType}>
                      <option value="task1">Select Task Type to Delete</option>
                      {taskTypes.map((taskType, index) => (
                        <option key={index} value={taskType}>{taskType}</option>
                      ))}
                    </select>
                    <button type="submit">Delete Task Type</button>
                  </form>
                </div>
              </div>
            ) : null}
          </div>
        </div>

        {/* RIGHT SIDE */}
        <div className="dashboard-right">
          <div className="notifications">
            <h3>Notifications</h3>
            <div className="placeholder">[ Pull real-time notifications from DB ]</div>
          </div>

          <div className="stats-box">
            <div className="stat">Unapproved Works: 7</div>
            <div className="stat">Total Works Last Week: 45 Hours</div>
            <div className="stat">Upcoming TA Tasks: 1</div>
            <div className="stat">Days Until Next TA Task: 3 </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default INS_DashboardPage;
