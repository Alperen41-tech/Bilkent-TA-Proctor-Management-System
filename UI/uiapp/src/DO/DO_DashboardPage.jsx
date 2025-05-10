import React, { useState, useEffect } from "react";
import NavbarDO from "./NavbarDO";
import PendingRequestItem from "../PendingRequestItem";
import ReceivedRequestItem from "../ReceivedRequestItem";
import NotificationItem from "../NotificationItem";
import "./DO_DashboardPage.css";
import axios from "axios";

const DO_Dashboard = () => {
  const [activeTab, setActiveTab] = useState("pending");
  const [selectedRequest, setSelectedRequest] = useState(null);
  const [notifications, setNotifications] = useState([]);
  const [receivedRequests, setReceivedRequests] = useState([]);
  const [pendingRequests, setPendingRequests] = useState([]);

  const handleTabClick = (tab) => {
    setActiveTab(tab);
    setSelectedRequest(null);
    fetchReceivedRequests();
    fetchPendingRequests();
  };

  const handleRequestResponse = async (requestId, answer) => {
    try {
      const response = await axios.put("http://localhost:8080/request/respond", null, {
        params: { id: requestId, response: answer },
      });
      if (response.data) {
        alert("Request is processed successfully.");
        fetchReceivedRequests();
      } else {
        alert("Failed to accept the request. Please try again.");
      }
    } catch (error) {
      console.error("Error accepting request:", error);
      alert("An error occurred while accepting the request. Please try again.");
    }
  };

const fetchReceivedRequests = async () => {
  try {
    const token = localStorage.getItem("token");
    const response = await axios.get("http://localhost:8080/taFromDeanRequest/getUnapprovedInstructorAdditionalTARequests", {
      headers: {
        Authorization: `Bearer ${token}`
      },
    });
    const sortedReceivedRequests = response.data.sort((a, b) => new Date(b.sentDateTime) - new Date(a.sentDateTime));
    setReceivedRequests(sortedReceivedRequests);
  } catch (error) {
    console.error("Error fetching received requests:", error);
  }
};


  const fetchPendingRequests = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.get("http://localhost:8080/proctoringApplication/getProctoringApplications", {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      const sortedPendingRequests = response.data.sort((a, b) => new Date(b.classProctoringDTO.startDate) - new Date(a.classProctoringDTO.startDate));
      setPendingRequests(sortedPendingRequests);
    } catch (error) {
      console.error("Error fetching pending requests:", error);
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
      
    } catch (error) {
      console.error("Error fetching notifications:", error);
    }
  };

  useEffect(() => {
    fetchNotifications();
    fetchReceivedRequests();
    fetchPendingRequests();
  }, []);

  const createPendingRequest = (request) => {
    const { applicationId, classProctoringDTO, applicantCountLimit, isVisibleForTAs, isComplete, finishDate } = request;
    const requestType = "InstructorAdditionalTARequest";

    const prepared = {
      requestType,
      sentDateTime: classProctoringDTO.startDate,
      classProctoringEventName: classProctoringDTO.proctoringName,
      classProctoringStartDate: classProctoringDTO.startDate,
      classProctoringEndDate: classProctoringDTO.endDate,
      courseCode: classProctoringDTO.courseName,
      taCountNeeded: applicantCountLimit,
      isComplete,
      senderName: "You have sent this"
,
      status: null,
      responseDateTime: null,
      isSelected: selectedRequest === request,
      onCancel: () => console.log("cancel pending app", applicationId)
    };

    return (
      <div key={applicationId} onClick={() => setSelectedRequest(request)}>
        <PendingRequestItem {...prepared} />
      </div>
    );
  };

  const createReceivedRequest = (request, index) => (
    <div key={index} onClick={() => setSelectedRequest(request)}>
      <ReceivedRequestItem
        {...request}
        onAccept={() => handleRequestResponse(request.requestId, true)}
        onReject={() => handleRequestResponse(request.requestId, false)}
        isSelected={selectedRequest === request}
      />
    </div>
  );

  return (
    <div className="ta-dashboard-dashboard-page">
      <NavbarDO />
      <div className="ta-dashboard-dashboard-grid">
        <div className="ta-dashboard-dashboard-left">
          <div className="ta-dashboard-left-up-panel">
            <div className="ta-dashboard-tab-bar">
              <button onClick={() => handleTabClick("pending")} className={activeTab === "pending" ? "active" : ""}>Pending Requests</button>
              <button onClick={() => handleTabClick("received")} className={activeTab === "received" ? "active" : ""}>Received Requests</button>
            </div>
            <div className="ta-dashboard-tab-content">
              {activeTab === "pending" && pendingRequests.map(createPendingRequest)}
              {activeTab === "received" && (
                <div>
                  {receivedRequests.filter(req => !req.isApproved).map(createReceivedRequest)}
                </div>
              )}
            </div>
          </div>

          <div className="ta-dashboard-bottom-left">
            <div className="ta-dashboard-details-panel">
              <h3>Details</h3>
              {selectedRequest && activeTab === "pending" ? (
                <div>
                  <p><strong>Course:</strong> {selectedRequest.classProctoringDTO.courseName}</p>
                  <p><strong>TA Count:</strong> {selectedRequest.applicantCountLimit}</p>
                  <p><strong>Start:</strong> {selectedRequest.classProctoringDTO.startDate ? new Date(selectedRequest.classProctoringDTO.startDate).toLocaleDateString() + " " + new Date(selectedRequest.classProctoringDTO.startDate).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }) : "—"}</p>

                  <p><strong>End:</strong> {selectedRequest.classProctoringDTO.endDate ? new Date(selectedRequest.classProctoringDTO.endDate).toLocaleDateString() + " " + new Date(selectedRequest.classProctoringDTO.endDate).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }) : "—"}</p>


                  <p><strong>Visible to TAs:</strong> {selectedRequest.isVisibleForTAs ? "Yes" : "No"}</p>
                  <p><strong>Completed:</strong> {selectedRequest.isComplete ? "Yes" : "No"}</p>
                </div>
              ) : activeTab === "pending" ? (
                <p className="ta-dashboard-placeholder">[ Click a pending request to see its details ]</p>
              ) : selectedRequest ? (
                <div>
                  <p><strong>Sender:</strong> {selectedRequest.senderName || selectedRequest.name || "—"} ({selectedRequest.senderEmail || selectedRequest.email || "—"})</p>
                  <p><strong>Course:</strong> {selectedRequest.courseName || selectedRequest.courseCode || selectedRequest.classProctoringEventName || "—"}</p>
                  <p><strong>TA Count:</strong> {typeof selectedRequest.taCount === "number" ? selectedRequest.taCount : "—"}</p>
                  {selectedRequest.sentDateTime && (() => {
                    const [date, time] = selectedRequest.sentDateTime.split("T");
                    return (
                      <>
                        <p><strong>Sent Date:</strong> {date}</p>
                        <p><strong>Sent Time:</strong> {time}</p>
                      </>
                    );
                  })()}
                  <p><strong>Comment:</strong> {selectedRequest.description || "—"}</p>
                  <p><strong>Status:</strong> {selectedRequest.isApproved ? 'Approved' : selectedRequest.isComplete ? 'Completed' : 'Pending'}</p>
                </div>
              ) : (
                <p className="ta-dashboard-placeholder">[ Click a request to see its details ]</p>
              )}
            </div>
          </div>
        </div>

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

export default DO_Dashboard;
