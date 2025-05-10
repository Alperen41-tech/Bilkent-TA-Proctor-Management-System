import React, { useState, useEffect, useRef } from "react";
import NavbarINS from "./NavbarINS";
import "./INS_DashboardPage.css";
import PendingRequestItem from "../PendingRequestItem";
import ReceivedRequestItem from "../ReceivedRequestItem";
import WorkloadEntryItem from "../WorkloadEntryItem";
import NotificationItem from "../NotificationItem";
import axios from "axios";

const INS_DashboardPage = () => {
  const [activeTab, setActiveTab] = useState("pending");
  const [selectedRequest, setSelectedRequest] = useState(null);
  const [taskTypes, setTaskTypes] = useState([]);
  const [showRespondedWorkloadDetails, setShowRespondedWorkloadDetails] = useState(false);
  const [notifications, setNotifications] = useState([]);
  const [receivedRequests, setReceivedRequests] = useState([]);
  const [pendingRequests, setPendingRequests] = useState([]);
  const [selectedRespondedWorkloadRequest, setSelectedRespondedWorkloadRequest] = useState(null);
  const [instructorCourses, setInstructorCourses] = useState([]);
  const [selectedCreateTaskCourse, setSelectedCreateTaskCourse] = useState({});
  const [selectedDeleteTaskCourse, setSelectedDeleteTaskCourse] = useState({});

// Refs for form inputs
  const newTaskTypeNameRef = useRef();
  const newTaskLimitRef = useRef();
  const selectedForDeleteTaskType = useRef();
//---------------------------------------------

  const handleTabClick = (tab) => {
    setActiveTab(tab);
    setSelectedRequest(null);
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

  const createWorkloadEntry = (courseCode, taskTitle, date, duration, comment, status, onSelect) => {
    return <div onClick={() => {setShowRespondedWorkloadDetails(true);onSelect();}}> <WorkloadEntryItem courseCode={courseCode} taskTitle={taskTitle} date={date} duration={duration} comment={comment} status={status} /> </div>;
  }

  const fetchInstructorCourses = async () => {
    try {
      const token = localStorage.getItem("token")
      const response = await axios.get("http://localhost:8080/course/getCoursesOfInstructor", {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      if (response.data) {
        console.log("Fetched Courses:", response.data);
        setInstructorCourses(response.data);
      } else {
        alert("Could not fetch courses. Try again.");
      }
    } catch (error) {
      console.error("There was an error with fetching courses:", error);
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
      if(response.data){
        const sortedNotifications = response.data.sort((a, b) => new Date(b.date) - new Date(a.date));
        setNotifications(sortedNotifications);
        console.log(notifications);
      }
      else {
        alert("Could not fetch notifications. Try again.");
      }
    } catch (error) {
      console.error("Error fetching notifications:", error);
    }
  };

  const fetchReceivedRequests = async () => {
    try {
      const token = localStorage.getItem("token")
      const response = await axios.get("http://localhost:8080/request/getByReceiverId", {
        headers: {
          Authorization: `Bearer ${token}`
        }
      }); // Adjust the URL as needed
      const sortedRequests = response.data.sort((a, b) => new Date(b.sentDateTime) - new Date(a.sentDateTime));
      setReceivedRequests(sortedRequests);
      console.log(receivedRequests);
    } catch (error) {
      console.error("Error fetching received requests:", error);
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
      const sortedRequests = response.data.sort((a, b) => new Date(b.sentDateTime) - new Date(a.sentDateTime));
      setPendingRequests(sortedRequests);
      console.log(receivedRequests);
    } catch (error) {
      console.error("Error fetching pending requests:", error);
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
        fetchReceivedRequests(); 
        fetchPendingRequests(); 
      } else {
        alert("Failed to accept the request. Please try again.");
      }
    } catch (error) {
      console.error("Error accepting request:", error);
      alert("An error occurred while accepting the request. Please try again.");
    }
  };

  const postTaskType = async (taskTypeName, taskLimit) => {
    try {
      const response = await axios.post(`http://localhost:8080/taskType/createTaskType?courseId=${selectedCreateTaskCourse.course.id}`, {
        courseName: selectedCreateTaskCourse.course.name,
        taskTypeName: taskTypeName,
        taskLimit: parseInt(taskLimit,10),       
      });

      if (!response.data) {
        alert("Could not created the Task Type. Try again.");
      } 
      else {
        alert("Task Type created successfully!");
        console.log("Task Type created successfully:", response.data);
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
            courseId: selectedDeleteTaskCourse.course.id,
            taskTypeName: taskTypeName,
           }
      });
      if (!response.data) {
        alert("Could not delete the Task Type. Try again.");
      } 
      else {
        alert("Task Type deleted successfully!");
        console.log("Task Type deleted successfully:", response.data);
      }
    } catch (error) {
      console.error("There was an error with the Task Type deletion:", error);
      alert(error.response.data.message);
    }
  };

  const fetchTaskTypes = async () => {
    if (!selectedDeleteTaskCourse || !selectedDeleteTaskCourse.course) {
      console.warn("No valid course selected for fetching task types.");
      return;
    }
  
    try {
      const response = await axios.get(
        `http://localhost:8080/taskType/getTaskTypeNames?courseId=${selectedDeleteTaskCourse.course.id}`
      );
      if (response.data) {
        console.log("Fetched Task Types:", response.data);
        setTaskTypes(response.data);
      } else {
        alert("Could not fetch Task Types. Try again.");
      }
    } catch (error) {
      console.error("There was an error with fetching Task Types:", error);
      alert("An error occurred. Please try again.");
    }
  };
  

  const cancelPendingRequest = async (requestId) => {
    try {
      const response = await axios.delete(`http://localhost:8080/request/deleteRequest?id=${requestId}`);
      if (response.data) {
        alert("Request canceled successfully.");
        fetchPendingRequests();
        setSelectedRequest(null);
      } else {
        alert("Failed to cancel the request. Please try again.");
      }
    } catch (error) {
      console.error("Error canceling request:", error);
      alert("An error occurred while canceling the request. Please try again.");
    }
  };

  useEffect(() => {
    fetchReceivedRequests();
    fetchPendingRequests();
    fetchNotifications();
    fetchInstructorCourses();
  }, []);

  useEffect(() => {
    if (selectedDeleteTaskCourse) {
      fetchTaskTypes();
    }
  }
  , [selectedDeleteTaskCourse]);

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
                  {receivedRequests.filter((enr, index) => {return enr.status === null}).map((req, index) => createReceivedRequest(req, index))}
                </div>
              )}
              {activeTab === "tasks" && (
                <div >{receivedRequests.filter((rq,index) =>{
                  if (rq.requestType !== "TAWorkloadRequest") return false;                  
                  return rq.status === "APPROVED" || rq.status === "REJECTED";
                }).map((ent, idx) =>
                  createWorkloadEntry(
                    ent.courseCode,
                    ent.taskTypeName,
                    ent.sentDateTime,
                    ent.timeSpent,
                    ent.description,
                    ent.status,
                    () => setSelectedRespondedWorkloadRequest(ent)  // Set the selected request for details
                  ) 
                )}
                </div>
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
              <div className="bottom-left-task">
                <div className="task-type-create-form">
                  <h3>Create Task Type</h3>
                  <form onSubmit={(e) => {
                    e.preventDefault();
                    if(selectedCreateTaskCourse === "Select Course"){
                      alert("Please select a course.");
                      return;
                    }
                    postTaskType(newTaskTypeNameRef.current.value, newTaskLimitRef.current.value); 
                  }}>
                    <label>Select Course</label>
                    <select required onChange={(e) => {
                      if(e.target.value === "Select Course"){
                        setSelectedCreateTaskCourse({});
                      }
                      else {
                        const obj = instructorCourses.find((course) => course.course.id.toString() === e.target.value);
                        setSelectedCreateTaskCourse(obj);}}
                      }>
                      <option value="Select Course">Select Course</option>
                      {instructorCourses.map((course, index) => (
                        <option key={index} value={course.course.id}>{course.course.name}</option>
                      ))}
                    </select>
                    <label>Task Type</label>
                    <input required ref={newTaskTypeNameRef} type="text" placeholder="Task Type" />
                    <label>Maximum Time Limit</label>
                    <div className="time-inputs">
                      <input required ref={newTaskLimitRef} type="number" placeholder="Hours" min={0} />
                    </div>
                    <button className="button" type="submit">Create Task Type</button>
                  </form>
                </div>
                <div className="task-type-delete-form">
                  <h3>Delete Task Type</h3>
                  <form onSubmit={(e) => {
                    e.preventDefault();
                    if(selectedDeleteTaskCourse === "Select Course"){
                      alert("Please select a course.");
                      return;
                    }
                    deleteTaskType(selectedForDeleteTaskType.current.value); e.preventDefault();}}>
                    <label>Select Course</label>
                      <select required onChange={(e) => {
                        if(e.target.value === "Select Course"){
                          setSelectedDeleteTaskCourse({});
                        }
                        else {
                          const obj = instructorCourses.find((course) => course.course.id.toString() === e.target.value);
                          setSelectedDeleteTaskCourse(obj);
                        }
                       }}>
                        <option value="Select Course">Select Course</option>
                        {instructorCourses.map((course, index) => (
                          <option key={index} value={course.course.id}>{course.course.name}</option>
                        ))}
                      </select>
                    <label>Select Task Type</label>
                    <select ref={selectedForDeleteTaskType}>
                      <option value="task1">Select Task Type to Delete</option>
                      {selectedDeleteTaskCourse ? taskTypes.map((taskType, index) => (
                        <option key={index} value={taskType}>{taskType}</option>
                      )) : null}
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
      {showRespondedWorkloadDetails && (
      <div className="modal-overlay">
        <div className="modal">
          <h3>Workload Request Details</h3>
          <div className="modal-content">
            <p><strong>Course Code:</strong> {selectedRespondedWorkloadRequest.courseCode}</p>
            <p><strong>Task Title:</strong> {selectedRespondedWorkloadRequest.taskTypeName}</p>
            <p><strong>Date:</strong> {selectedRespondedWorkloadRequest.sentDateTime.split("T")[0]}</p>
            <p><strong>Duration:</strong> {selectedRespondedWorkloadRequest.timeSpent} hours</p>
            <p><strong>Comment:</strong> {selectedRespondedWorkloadRequest.description}</p>
            <p><strong>Status:</strong> {selectedRespondedWorkloadRequest.status}</p>
            <p><strong>Sender Name:</strong> {selectedRespondedWorkloadRequest.senderName}</p>
          </div>
          <div className="modal-buttons">
            <button className="cancel-button" onClick={() => setShowRespondedWorkloadDetails(false)}>Return</button>
          </div>
        </div>
      </div>
    )}
    </div>
  );
};

export default INS_DashboardPage;
