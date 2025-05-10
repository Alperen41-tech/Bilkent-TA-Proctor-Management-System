import React, { useState, useEffect, useRef } from "react";
import Navbar from "./Navbar";
import "./DashboardPage.css";
import PendingRequestItem from "../PendingRequestItem";
import ReceivedRequestItem from "../ReceivedRequestItem";
import WorkloadEntryItem from "../WorkloadEntryItem";
import ProctoringDutyItem from "../ProctoringDutyItem";
import NotificationItem from "../NotificationItem";
import axios from "axios";

const DashboardPage = () => {
  const [activeTab, setActiveTab] = useState("pending");
  const [selectedRequest, setSelectedRequest] = useState(null);
  const [selectedProctoring, setSelectedProctoring] = useState(null);
  const [tasProctorings, setTasProctorings] = useState([]);
  const [taskType, setTaskType] = useState([]);
  const [taWorkloadRequests, setTaWorkloadRequests] = useState([]);
  const [notifications, setNotifications] = useState([]);
  const [receivedRequests, setReceivedRequests] = useState([]);
  const [pendingRequests, setPendingRequests] = useState([]);

  //Refs for the new workload entry
  const newTaskTypeEntry = useRef();
  const newTimeSpendHoursEntry = useRef();
  const newTimeSpendMinutesEntry = useRef();
  const newDetailsEntry = useRef();
  //-----------------------------
  
  const handleSelect = (proc) => {
    setSelectedProctoring(proc);
  };

  const handleTabClick = (tab) => {
    setActiveTab(tab);
    setSelectedRequest(null);
    setSelectedProctoring(null);
    fetchReceivedRequests();
    fetchPendingRequests();
  };

  const handlePrintClassroomInfo = async () => {
  try {
    const token = localStorage.getItem("token");
    const response = await axios.get(
      `http://localhost:8080/excel/getStudentsOfClassProctoring?classProctoringId=${selectedProctoring.classProctoringDTO.id}`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        responseType: "blob", // Excel dosyası için önemli!
      }
    );

    const url = window.URL.createObjectURL(new Blob([response.data]));
    const link = document.createElement("a");
    link.href = url;
    link.setAttribute("download", "classroom_info.xlsx");
    document.body.appendChild(link);
    link.click();
    link.remove();
  } catch (error) {
    console.error("Error printing classroom info:", error);
    if (error.response?.data?.message) {
      alert(error.response.data.message);
    } else {
      alert("An error occurred. Please try again.");
    }
  }
};


  const createPendingRequest = (request, index) => {
    return (
      <div key={index} onClick={() => setSelectedRequest(request)}>
        <PendingRequestItem {...request} onCancel={() => cancelPendingRequest(request.requestId)} isSelected={selectedRequest === request}/>
      </div>
    );
  };

  const createReceivedRequest = (request, index) => {
    return (
      <div key={index} onClick={() => setSelectedRequest(request)}>
        <ReceivedRequestItem {...request} onAccept={()=>handleRequestResponse(request.requestId, true)} onReject={()=>handleRequestResponse(request.requestId, false)} isSelected={selectedRequest === request}/>
      </div>
    );
  };

  const createWorkloadEntry = (taskTitle, courseCode ,date, duration, comment, status) => {
    return <WorkloadEntryItem taskTitle={taskTitle} courseCode={courseCode} date={date} duration={duration} comment={comment} status={status} />;
  }

  const fetchTasProctorings = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.get("http://localhost:8080/classProctoringTARelation/getTAsClassProctorings", {
        headers: {
          Authorization: `Bearer ${token}`
        }}); // Adjust the URL as needed
      const sortedProctorings = response.data.sort((a, b) => new Date(b.classProctoringDTO.startDate) - new Date(a.classProctoringDTO.startDate));
      setTasProctorings(sortedProctorings);
      console.log(tasProctorings);
    } catch (error) {
      console.error("Error fetching tasks:", error);
      if (error.response.data.message) {
        alert(error.response.data.message);
      }
      else{
        alert("An error occurred. Please try again.");
      }
    }
  };

  const fetchWorkloadTypes = async () => {
    try {
      const response = await axios.get("http://localhost:8080/taskType/getTaskTypeNames?courseId=1"); // Adjust the URL as needed
      setTaskType(response.data);
      console.log(taskType);
    } catch (error) {
      console.error("Error fetching task types:", error);
      if (error.response.data.message) {
        alert(error.response.data.message);
      }
      else{
        alert("An error occurred. Please try again.");
      }
    }
  };

  const fetchTaWorkloadRequests = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.get("http://localhost:8080/taWorkloadRequest/getByTA",{
        headers: {
          Authorization: `Bearer ${token}`
        }});
      setTaWorkloadRequests(response.data);
      console.log(taWorkloadRequests);
    } catch (error) {
      console.error("Error fetching task types:", error);
      if (error.response.data.message) {
        alert(error.response.data.message);
      }
      else{
        alert("An error occurred. Please try again.");
      }
    }
  };

  const fetchNotifications = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.get("http://localhost:8080/notification/get", {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      const sortedNotifications = response.data.sort((a, b) => new Date(b.date) - new Date(a.date));
      setNotifications(sortedNotifications);
      console.log(notifications);
    } catch (error) {
      console.error("Error fetching task types:", error);
      if (error.response.data.message) {
        alert(error.response.data.message);
      }
      else{
        alert("An error occurred. Please try again.");
      }
    }
  };

  const fetchReceivedRequests = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.get("http://localhost:8080/request/getByReceiverId",{
        headers: {
          Authorization: `Bearer ${token}`
        }
      }); // Adjust the URL as needed
      const sortedRecRequests = response.data.sort((a, b) => new Date(b.sentDateTime) - new Date(a.sentDateTime));
      setReceivedRequests(sortedRecRequests);
      console.log(receivedRequests);
    } catch (error) {
      console.error("Error fetching received requests:", error);
      if (error.response.data.message) {
        alert(error.response.data.message);
      }
      else{
        alert("An error occurred. Please try again.");
      }
    }
  };

  const fetchPendingRequests = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.get("http://localhost:8080/request/getBySenderId", {
        headers: {
          Authorization: `Bearer ${token}`
        }
      }); // Adjust the URL as needed
      const sortedPenRequests = response.data.sort((a, b) => new Date(b.sentDateTime) - new Date(a.sentDateTime));
      setPendingRequests(sortedPenRequests);
      //setPendingRequests(response.data);
      console.log(receivedRequests);
    } catch (error) {
      console.error("Error fetching pending requests:", error);
      if (error.response.data.message) {
        alert(error.response.data.message);
      }
      else{
        alert("An error occurred. Please try again.");
      }
    }
  };

  const postNewWorkloadEntry = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.post("http://localhost:8080/taWorkloadRequest/create?id=1", {
        taskTypeName: newTaskTypeEntry.current.value,
        timeSpent: parseInt(newTimeSpendHoursEntry.current.value,10)*60 + parseInt(newTimeSpendMinutesEntry.current.value, 10),
        description: newDetailsEntry.current.value,
      }, {
        headers: {
          Authorization: `Bearer ${token}`
        }});
      if (!response.data) {
        alert("Could not create the new workload entry. Try again.");
      } else {
        alert("New workload entry created successfully.");
        fetchTaWorkloadRequests(); 
        fetchPendingRequests();
      }
    } catch (error) {
      console.error(error.response.data.message);
      if (error.response.data.message) {
        alert(error.response.data.message);
      }
      else{
        alert("An error occurred. Please try again.");
      }
    }
  };

  const handleRequestResponse = async (requestId, answer) => {
    try {
      const response = await axios.put(`http://localhost:8080/request/respond`,null, {
        params: {
          id: requestId,
          response: answer,
        },
      }
      );
      if (response.data) {
        alert("Request accepted successfully.");
        fetchReceivedRequests(); // Refresh the received requests after accepting
      } else {
        alert("Failed to accept the request. Please try again.");
      }
    } catch (error) {
      console.error("Error accepting request:", error);
      if (error.response.data.message) {
        alert(error.response.data.message);
      }
      else{
        alert("An error occurred. Please try again.");
      }
    }
  };

  const cancelPendingRequest = async (requestId) => {
    try {
      const response = await axios.delete(`http://localhost:8080/request/deleteRequest?id=${requestId}`);
      if (response.data) {
        alert("Request canceled successfully.");
        fetchPendingRequests();
        fetchTaWorkloadRequests();
        setSelectedRequest(null);
      } else {
        alert("Failed to cancel the request. Please try again.");
      }
    } catch (error) {
      console.error("Error canceling request:", error);
      if (error.response.data.message) {
        alert(error.response.data.message);
      }
      else{
        alert("An error occurred. Please try again.");
      }
    }
  };

  useEffect(() => {
    fetchNotifications();
    fetchTasProctorings();
    fetchWorkloadTypes();
    fetchTaWorkloadRequests();
    fetchReceivedRequests();
    fetchPendingRequests();
  }, []);

  const handleLockedStatusChange = async (id) => {
    try {
      const proctoringToBeLocked = tasProctorings.find((duty) => duty.classProctoringDTO.id === id);
      const token = localStorage.getItem("token");
      console.log(proctoringToBeLocked);
      const responseLocked = await axios.put("http://localhost:8080/classProctoringTARelation/updateTAsClassProctorings", {
        classProctoringDTO: {
          id: proctoringToBeLocked.classProctoringDTO.id,
        },
        isOpenToSwap: !proctoringToBeLocked.isOpenToSwap,
      }, {headers: {
          Authorization: `Bearer ${token}`
        }});

      if (!responseLocked.data) {
        alert("Could not locked the swap. Try again.");
      }
      fetchTasProctorings();
    } catch (error) {
      console.error("There was an error with the login request:", error);
      if (error.response.data.message) {
        alert(error.response.data.message);
      }
      else{
        alert("An error occurred. Please try again.");
      }
    }
  }
  
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
                  {console.log(pendingRequests)}
                </div>
              )}
              {activeTab === "received" && (
                <div>
                  {receivedRequests.filter((request)=> request.status === null).map((req, index) => createReceivedRequest(req, index))}
                </div>
              )}
              {activeTab === "tasks" && (
                <div>{taWorkloadRequests.map((ent, idx) =>
                  createWorkloadEntry( 
                    ent.taskTypeName,
                    ent.courseCode,
                    ent.sentDateTime,
                    ent.timeSpent,
                    ent.description,
                    ent.status 
                  )
                )}</div>
              )}
              {activeTab === "proctorings" && (
                <div>
                  {tasProctorings.map((duty) => (
                    <ProctoringDutyItem
                      key={duty.classProctoringDTO.id}
                      duty={duty}
                      isSelected={selectedProctoring == duty}
                      onSelect={() => handleSelect(duty)}
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
                  <p><strong>Name:</strong> {selectedRequest.senderName || "—"}</p>
                  <p><strong>Email:</strong> {selectedRequest.senderEmail || "—"}</p>

                  {selectedRequest.sentDateTime && (() => {
                    const [ date, time ] = selectedRequest.sentDateTime.split("T");
                    return (
                      <>
                        <p><strong>Sent Date:</strong> {date}</p>
                        <p><strong>Sent Time:</strong> {time}</p>
                      </>
                    );
                  })()}

                  {selectedRequest.requestType === 'AuthStaffProctoringRequest' ||
                  selectedRequest.requestType === 'TASwapRequest' ? (
                    <>
                      <p><strong>Event:</strong> {selectedRequest.classProctoringEventName}</p>
                      <p><strong>Event Start Date:</strong> {selectedRequest.classProctoringStartDate ? selectedRequest.classProctoringStartDate.split("T")[0] : "—"}</p>
                      <p><strong>Event End Date:</strong> {selectedRequest.classProctoringEndDate ? selectedRequest.classProctoringEndDate.split("T")[0] : "—"}</p>
                    </>
                  ) : null}

                  {selectedRequest.requestType === 'TAWorkloadRequest' ? (
                    <p><strong>Task:</strong> {selectedRequest.taskTypeName}</p>
                  ) : null}

                  <p><strong>Comment:</strong> {selectedRequest.description || "—"}</p>
                  <p><strong>Status:</strong> {selectedRequest.status || "—"}</p>
                </div>
                  ) : (
                    <p className="ta-dashboard-placeholder">[ Click a request to see its details ]</p>
                  )}


              </div>


            ) : activeTab === "tasks" ? (
              <div className="ta-dashboard-task-entry-form">
                <h3>Enter Task</h3>
                <form onSubmit={(e) => {e.preventDefault();postNewWorkloadEntry();console.log(taskType);}}>
                  <label>Task Type</label>
                  <select ref={newTaskTypeEntry}>
                    <option>Select a Task Type</option>
                    {taskType ? taskType.map((type, index) => (
                      <option key={index}>{type}</option>
                    )): null}
                    <option>Other</option>
                  </select>

                  <label>Time Spent</label>
                  <div className="ta-dashboard-time-inputs">
                    <input ref={newTimeSpendHoursEntry} type="number" min={0}  placeholder="Hours" />
                    <input ref={newTimeSpendMinutesEntry} type="number" min={0} placeholder="Minutes" />
                  </div>

                  <label>Details</label>
                  <textarea ref={newDetailsEntry} placeholder="Optional comments" />

                  <button type="submit">Send</button>
                </form>
              </div>
            ) : activeTab === "proctorings" ? (
              <div className="ta-dashboard-swap-form">
                <h3>Proctoring Information</h3>
                {selectedProctoring ? (
                  <div>
                    <p><strong>Course:</strong> {selectedProctoring.classProctoringDTO.courseName}</p>
                    <p><strong>Proctoring:</strong> {selectedProctoring.classProctoringDTO.proctoringName}</p>
                    <p><strong>Start Date:</strong> {selectedProctoring.classProctoringDTO.startDate.split("T")[0]}</p>
                    <p><strong>Start Time:</strong> {selectedProctoring.classProctoringDTO.startDate.split("T")[1]}</p>
                    <p><strong>End Time:</strong> {selectedProctoring.classProctoringDTO.endDate.split("T")[1]}</p>
                    <p><strong>Classrooms:</strong> {selectedProctoring.classProctoringDTO.classrooms}</p>
                    <p><strong>Locked:</strong> {selectedProctoring.isOpenToSwap ? "No" : "Yes"}</p>
                    <p><strong>Proctoring Status:</strong> {selectedProctoring.isPaid ? "Yes" : "No"}</p>
                    <button onClick={()=> handlePrintClassroomInfo()}>Print Classroom Info</button>
                  </div>
                ) : (
                  <p className="ta-dashboard-placeholder">{selectedProctoring}</p>
                )}
              </div>
            ) : null}
            </div>
        </div>

        {/* RIGHT SIDE */}
        <div className="ta-dashboard-dashboard-right">
          <div className="ta-dashboard-notifications">
            <h3>Notifications</h3>
            {notifications.map((notification, index) => (
              <div key={index} className="ta-dashboard-notification-item">
                <NotificationItem
                  requestType={notification.requestType}
                  message={notification.message}
                  date={notification.date}
                  notificationType={notification.notificationType}
                />
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default DashboardPage;
