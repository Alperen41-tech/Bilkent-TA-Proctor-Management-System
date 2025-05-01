// DO_Dashboard.jsx  – Dean’s Office (final with Pending/Received components)
import React, { useState, useEffect, useRef } from "react";
import NavbarDO from "./NavbarDO";
import PendingRequestItem from "../PendingRequestItem";
import ReceivedRequestItem from "../ReceivedRequestItem";
import "./DO_DashboardPage.css";
import axios from "axios";
import NotificationItem from "../NotificationItem";


const DO_Dashboard = () => {
  const [activeTab, setActiveTab] = useState("pending");
  const [selectedRequest, setSelectedRequest] = useState(null);




    const [receivedRequests, setReceivedRequests] = useState([]);
    const [pendingRequests, setPendingRequests] = useState([]);

  

  const handleTabClick = (tab) => {
    setActiveTab(tab);
    setSelectedRequest(null);
  };

  const createPendingRequest = (request, index) => {
    return (
      <div key={index} onClick={() => setSelectedRequest(request)}>
        <PendingRequestItem {...request} onCancel={() => console.log("canceled")} isSelected={selectedRequest === request}/>
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
      alert("An error occurred while accepting the request. Please try again.");
    }
  };


  const fetchReceivedRequests = async () => {
    try {
      const response = await axios.get("http://localhost:8080/request/getByReceiverId?receiverId=9"); // assuming reciever id is 9
      setReceivedRequests(response.data);
      console.log(receivedRequests);
    } catch (error) {
      console.error("Error fetching received requests:", error);
    }
  };

  const fetchPendingRequests = async () => {
    try {
      const response = await axios.get("http://localhost:8080/request/getBySenderId?senderId=9"); // Adjust the URL as needed
      setPendingRequests(response.data);
      console.log(receivedRequests);
    } catch (error) {
      console.error("Error fetching pending requests:", error);
    }
  };

    useEffect(() => {
      fetchReceivedRequests();
      fetchPendingRequests();
    }, []);






  return (
    <div className="ta-dashboard-dashboard-page">
      <NavbarDO />

      <div className="ta-dashboard-dashboard-grid">
        {/* LEFT side */}
        <div className="ta-dashboard-dashboard-left">
  {/* Top-left panel: Tabs */}
  <div className="ta-dashboard-left-up-panel">
    <div className="ta-dashboard-tab-bar">
      <button onClick={() => handleTabClick("pending")} className={activeTab === "pending" ? "active" : ""}>
        Pending Requests
      </button>
      <button onClick={() => handleTabClick("received")} className={activeTab === "received" ? "active" : ""}>
        Received Requests
      </button>
    </div>

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
    </div>
  </div>

  {/* Bottom-left panel: Details */}
  <div className="ta-dashboard-bottom-left">
  <div className="ta-dashboard-details-panel">
    <h3>Details</h3>
    {selectedRequest ? (
      <div>
        <p><strong>Name:</strong> {selectedRequest.senderName || selectedRequest.name || "—"}</p>
        <p><strong>Email:</strong> {selectedRequest.senderEmail || selectedRequest.email || "—"}</p>

        {/* Sent Date/Time Handling */}
        {selectedRequest.sentDateTime && (() => {
          const [date, time] = selectedRequest.sentDateTime.split("T");
          return (
            <>
              <p><strong>Sent Date:</strong> {date}</p>
              <p><strong>Sent Time:</strong> {time}</p>
            </>
          );
        })()}

        {/* Event-specific Fields */}
        {(selectedRequest.requestType === 'AuthStaffProctoringRequest' || selectedRequest.requestType === 'TASwapRequest') && (
          <>
            <p><strong>Event:</strong> {selectedRequest.classProctoringEventName}</p>
            <p><strong>Event Start:</strong> {selectedRequest.classProctoringStartDate?.split("T")[0] || "—"}</p>
            <p><strong>Event End:</strong> {selectedRequest.classProctoringEndDate?.split("T")[0] || "—"}</p>
          </>
        )}

        {/* Task-specific Fields */}
        {selectedRequest.requestType === 'TAWorkloadRequest' && (
          <p><strong>Task:</strong> {selectedRequest.taskTypeName}</p>
        )}

        <p><strong>Comment:</strong> {selectedRequest.description || "—"}</p>
        <p><strong>Status:</strong> {selectedRequest.status || "—"}</p>
      </div>
    ) : (
      <p className="ta-dashboard-placeholder">[ Click a request to see its details ]</p>
    )}
  </div>
</div>



      
</div>

        {/* RIGHT side */}
        <div className="ta-dashboard-dashboard-right">
          <div className="ta-dashboard-notifications">
            <h3>Notifications</h3>
            <div className="ta-dashboard-placeholder">[ Pull real-time notifications from DB ]</div>

          </div>

          <div className="ta-dashboard-stats-box">
            <div className="ta-dashboard-stat">Request From Others: {receivedRequests.length}</div>
            <div className="ta-dashboard-stat">Your Requests: {pendingRequests.length}</div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DO_Dashboard;
