import React, { act, useState, useEffect, use } from "react";
import NavbarDS from "./NavbarDS";
import "./DS_DashboardPage.css";
import { id } from "date-fns/locale";
import DS_DashboardTAItem from "./DS_DashboardTAItem";
import DS_SelectPaidProctoringTAItem from "./DS_SelectPaidProctoringTAItem";
import NotificationItem from "../NotificationItem";
import axios from "axios";
import PendingRequestItem from "../PendingRequestItem";
import ReceivedRequestItem from "../ReceivedRequestItem";

const DS_DashboardPage = () => {
  const [selectedAppliedStudentsId, setSelectedAppliedStudentsId] = useState([]);
  const [selectedATAIds, setSelectedATAIds] = useState([]);
  
  const [activeTab, setActiveTab] = useState("pending");
  const [selectedRequest, setSelectedRequest] = useState(null);
  const [searchText, setSearchText] = useState("");
  const [sortName, setSortName] = useState("");  
  const [sortWorkload, setSortWorkload] = useState("");
  
  
  const [appliedTAs, setAppliedTAs] = useState([]);
  const [notifications, setNotifications] = useState([]);
  const [receivedRequests, setReceivedRequests] = useState([]);
  const [pendingRequests, setPendingRequests] = useState([]);
  const [paidProctorings, setPaidProctorings] = useState([]);
  const [isAppliedAssignment, setIsAppliedAssignment] = useState(false);
  const [isManualAssignment, setIsManualAssignment] = useState(false);
  const [selectedPPR, setSelectedPPR] = useState(null);

  const handleTabClick = (tab) => {
    setActiveTab(tab);
    setSelectedRequest(null);
    setSelectedPPR(null);
  };

  const handleASC = (id) => {
    if (selectedAppliedStudentsId && !selectedAppliedStudentsId.includes(id)) {
      setSelectedAppliedStudentsId((prev) => [...prev, id]);
    }
    else {
      setSelectedAppliedStudentsId((prev) => prev.filter((userId) => userId !== id));
    }
    console.log("Selected TA:", id);
  };

  const handleAvaTAClick = (id) => { 
    if (selectedATAIds && !selectedATAIds.includes(id)) {
      setSelectedATAIds((prev) => [...prev, id]);
    }
    else {
      setSelectedATAIds((prev) => prev.filter((studentId) => studentId !== id));
    }
    console.log("Selected TA:", id);
  };

  const handleForceAssignment = () => {
    console.log("Force Assignment clicked");
  };
  const handleOfferAssignment = () => {
    console.log("Offer Assignment clicked");
  };

  const fetchReceivedRequests = async () => {
    try {
      const response = await axios.get("http://localhost:8080/request/getByReceiverId?receiverId=3"); // Adjust the URL as needed
      setReceivedRequests(response.data);
      console.log(receivedRequests);
    } catch (error) {
      console.error("Error fetching received requests:", error);
    }
  };

  const fetchPendingRequests = async () => {
    try {
      const response = await axios.get("http://localhost:8080/request/getBySenderId?senderId=4"); // Adjust the URL as needed
      setPendingRequests(response.data);
      console.log(receivedRequests);
    } catch (error) {
      console.error("Error fetching pending requests:", error);
    }
  };

  const fetchPaidProctoringRequests = async () => {
    try {
      const response = await axios.get("http://localhost:8080/proctoringApplication/getAllApplicationsByDepartment?departmentId=1");
      if (response.data) {
        setPaidProctorings(response.data);
        console.log(receivedRequests);
      }
      else{
        console.log("No paid proctoring requests found.");
      }
    } catch (error) {
      console.error("Error fetching paid proctoring requests:", error);
    }
  };

  const fetchAppliedStudents = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/proctoringApplicationTARelation/getApplicantsForApplication?applicationId=${selectedPPR.applicationId}`); // Adjust the URL as needed
      if (response.data) {
        setAppliedTAs(response.data);
      }
      else{
        console.log("No paid proctoring requests found.");
      }
    } catch (error) {
      console.error("Error fetching paid proctoring requests applicants:", error);
    }
  };

  const handleAssignmentTypeSelect = (type, applicationId) => {
    try {
      const response = axios.put(`http://localhost:8080/proctoringApplication/setApplicationType?applicationId=${applicationId}&applicationType=${type}`);
      if (response) {
        alert("Assignment type selected successfully.");
        fetchPaidProctoringRequests(); 
      } else {
        alert("Failed to select assignment type. Please try again.");
        fetchPaidProctoringRequests(); 
      }
    } catch (error) {
      console.error("Error selecting assignment type:", error);
      alert("An error occurred while selecting assignment type. Please try again.");
    }
  }

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

  const createSelectPaidProctoringTAs = () => {
    return paidProctorings.map((request) => (
      <DS_SelectPaidProctoringTAItem  paidProctoring={request} isSelected={selectedPPR === request} onSelect={()=>{setSelectedPPR(request); setSelectedAppliedStudentsId([]);}} isAppliedAssignment={request.applicationType === "APPLICATION"} isForcedAssignment={request.applicationType === "ASSIGNMENT"} onAppliedAssignment={()=> handleAssignmentTypeSelect("APPLICATION", request.applicationId)} onForcedAssignment={()=> handleAssignmentTypeSelect("ASSIGNMENT", request.applicationId)}/>
    ));
  }

  const createAppliedTAItems = () => {
    return appliedTAs.map((ta) => (
      <DS_DashboardTAItem name={ta.name} surname={ta.surname} onSelect={()=> handleASC(ta.userId)} isSelected={selectedAppliedStudentsId.includes(ta.userId)} id={ta.bilkentId} bgColor={""} email={ta.email}/>
    ));
  }
  const createAvaliableTAItems = () => {
    /*return tas.map((ta) => (
      <DS_DashboardTAItem name={ta.name} onSelect={handleAvaTAClick} isSelected={selectedATAIds.includes(ta.id)} id={ta.id} bgColor={""} email={ta.email}/>
    ));*/
  }

  const fetchNotifications = async () => {
    try {
      const response = await axios.get("http://localhost:8080/notification/get?id=4");
      setNotifications(response.data);
      console.log(notifications);
    } catch (error) {
      console.error("Error fetching task types:", error);
    }
  };
  useEffect(() => {
      fetchReceivedRequests();
      fetchPendingRequests();
      fetchNotifications();
      fetchPaidProctoringRequests();
    }, []);
  
  useEffect(() => {
    setIsAppliedAssignment(selectedPPR && selectedPPR.applicationType === "APPLICATION");
    setIsManualAssignment(selectedPPR && selectedPPR.applicationType === "ASSIGNMENT");
    fetchAppliedStudents();
  }, [paidProctorings, selectedPPR]);
  
  useEffect(() => {
    console.log(paidProctorings);
    console.log(selectedPPR);
    console.log(appliedTAs);
    console.log(selectedAppliedStudentsId);
    console.log(selectedATAIds);
  }, [appliedTAs, selectedAppliedStudentsId, selectedATAIds]);

  return (
    <div className="dashboard-page">
      <NavbarDS />
      <div className="dashboard-grid">
        {/* LEFT SIDE */}
        <div className="dashboard-left">
          {/* Tabs */}
          <div className="top-left">
          <div className="tab-bar">
            <button onClick={() => handleTabClick("pending")} className={activeTab === "pending" ? "active" : ""}>Pending Requests</button>
            <button onClick={() => handleTabClick("received")} className={activeTab === "received" ? "active" : ""}>Received Requests</button>
            <button onClick={() => handleTabClick("pprTas")} className={activeTab === "pprTas" ? "active" : ""}>Select Paid Proctoring TAs</button>
          </div>

          {/* Top Left Panel */}
          <div className="ds-dashboard-tab-content">
            {activeTab === "pending" && (
              <div className="ds-dashboard-pending-request-panel">{pendingRequests.map((req, index) => createPendingRequest(req, index))}</div>
            )}
            {activeTab === "received" && (
              <div className="ds-dashboard-received-request-panel">{receivedRequests.filter((enr, index) => {return enr.status === null}).map((req, index) => createReceivedRequest(req, index))}</div>
            )}
            {activeTab === "pprTas" && (
              <div>{createSelectPaidProctoringTAs()}</div>
            )}
          </div>
          </div>
          {/* Bottom Left Panel */}
          <div>
            {activeTab === "pprTas" && isAppliedAssignment ? (
                
              <div className="ta-list-container">
                <h3 className="ta-list-title">Applied Students</h3>
                <div className="buttons">
                  <button >Automatic Select</button>
                </div>
                <div className="ta-list">
                {appliedTAs.length > 0 ? (
                    <div>{createAppliedTAItems()}</div>
                )
                : (
                <div className="no-ta">No TAs available</div>
                )}
                </div>
              </div>
            ) : activeTab === "pprTas" && isManualAssignment ? (
            <div className="ds-dashboard-card">
              <h3>Available TAs</h3>
              <div className="ds-dashboard-filters">
                <input
                  type="text"
                  placeholder="🔍 Search by name"
                  value={searchText}
                  onChange={(e) => setSearchText(e.target.value)}
                />
                <select value={sortName} onChange={(e) => setSortName(e.target.value)}>
                  <option value="">Sort by Name</option>
                  <option value="asc">A → Z</option>
                  <option value="desc">Z → A</option>
                </select>
                <select value={sortWorkload} onChange={(e) => setSortWorkload(e.target.value)}>
                  <option value="">Sort by Workload</option>
                  <option value="low">Low to High</option>
                  <option value="high">High to Low</option>
                </select>
                <button>Automatic Select</button>
              </div>
    
              <div className="ds-dashboard-avaliable-ta-list">{createAvaliableTAItems()}</div>
            </div>) : null}
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

          <div className="right-bottom">
            {activeTab === "pending" || activeTab === "received" ? (
              <div className="ds-dashboard-details-panel">
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
            ): activeTab === "pprTas" && (
              <div className="ta-list-container">
              <h3 className="ta-list-title">TA List</h3>
              <div className="ta-list">
                {appliedTAs.length > 0 ? (
                  <div>
                    {selectedAppliedStudentsId.map((id) => {
                      return (
                        appliedTAs.filter((ta) => ta.userId === id).map(({ name,surname, bilkentId }) => (
                        <div className="ds-dashboard-ta-list-item-content">
                          <div className="ds-dashboard-ta-list-item-name">Name: {name} {surname}</div>
                          <div className="ds-dashboard-ta-list-item-id">Student ID: {bilkentId}</div>
                        </div>
                      )))
                    }               
                    )}
                  </div>
                  
                ) : (
                  <div className="no-ta">No TAs available</div>
                )}
              </div>
              <div className="buttons">
                <button onClick={()=> handleForceAssignment()}>Force Assignment</button>
                <button onClick={()=> handleOfferAssignment()}>Offer Assignment</button>
              </div>
            </div>
          )}
         </div>
        </div>
      </div>
    </div>
  );
};
export default DS_DashboardPage;