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
import { set } from "date-fns";

const DS_DashboardPage = () => {
  const [selectedAppliedStudentsId, setSelectedAppliedStudentsId] = useState([]);
  const [activeTab, setActiveTab] = useState("pending");
  const [selectedRequest, setSelectedRequest] = useState(null);
  const [searchText, setSearchText] = useState("");
  const [sortName, setSortName] = useState("");  
  const [sortWorkload, setSortWorkload] = useState("");
  const [sortedSearchAppliedTAs, setSortedSearchAppliedTAs] = useState([]);
  const [sortedSearchAvailableTAs, setSortedSearchAvailableTAs] = useState([]);
  
  const [avaliableTAs, setAvailableTAs] = useState([]);
  const [appliedTAs, setAppliedTAs] = useState([]);
  const [notifications, setNotifications] = useState([]);
  const [receivedRequests, setReceivedRequests] = useState([]);
  const [pendingRequests, setPendingRequests] = useState([]);
  const [paidProctorings, setPaidProctorings] = useState([]);
  const [isAppliedAssignment, setIsAppliedAssignment] = useState(false);
  const [isManualAssignment, setIsManualAssignment] = useState(false);
  const [selectedPPR, setSelectedPPR] = useState(null);

  const [taSelectRestrictionsCount, setTASelectRestrictionsCount] = useState(1);
  const [taSelectRestrictionsEligibility, setTASelectRestrictionsEligibility] = useState(false);
  const [taSelectRestrictionsOneDay, setTASelectRestrictionsOneDay] = useState(false);

  const handleTabClick = (tab) => {
    setActiveTab(tab);
    setSelectedRequest(null);
    setSelectedPPR(null);
    fetchReceivedRequests();
    fetchPendingRequests();
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

  const handleForceAssignment = async () => {
    try {
      if (selectedAppliedStudentsId.length === 0) {
        alert("Please select at least one student to assign.");
        return;
      }
      const token = localStorage.getItem("token");
      const response = await axios.post(
        "http://localhost:8080/authStaffProctoringRequestController/forcedAssign",
        selectedAppliedStudentsId.map((id) => ({ userId: id })),
        {
          params: {
            classProctoringId: selectedPPR.classProctoringDTO.id,
          },
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
  
      if (response.data) {
        alert("Force assignment completed successfully.");
        fetchAppliedStudents();
        fetchAvaliableTAs();
      } else {
        alert("Failed to complete force assignment. Please try again.");
      }
    } catch (error) {
      console.error("Error during force assignment:", error);
      if (error.response?.data?.message) {
        alert(error.response.data.message);
      } else {
        alert("An error occurred during force assignment. Please try again.");
      }
    }
  };
  
  const handleOfferAssignment = async () => {
    try {
      if (selectedAppliedStudentsId.length === 0) {
        alert("Please select at least one student to assign.");
        return;
      }

      const token = localStorage.getItem("token");
      const response = await axios.post(
        "http://localhost:8080/authStaffProctoringRequestController/unforcedAssign",
        selectedAppliedStudentsId.map((id) => ({ userId: id })),
        {
          params: {
            classProctoringId: selectedPPR.classProctoringDTO.id,
          },
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
  
      if (response.data) {
        alert("Unforced assignment completed successfully.");
        fetchAppliedStudents();
        fetchAvaliableTAs();
      } else {
        alert("Failed to complete unforce assignment. Please try again.");
      }
    } catch (error) {
      console.error("Error during force assignment:", error);
      if (error.response?.data?.message) {
        alert(error.response.data.message);
      } else {
        alert("An error occurred during force assignment. Please try again.");
      }
    }
  };

  const fetchReceivedRequests = async () => {
    try {
      const token = localStorage.getItem("token");
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

  const fetchPaidProctoringRequests = async () => {
    try {
      const token = localStorage.getItem("token");

      const response = await axios.get(
          "http://localhost:8080/proctoringApplication/getAllApplicationsByDepartment",
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
      );

      if (response.data) {
        const sortedRequests = response.data.sort(
            (a, b) =>
                new Date(b.classProctoringDTO.startDate) -
                new Date(a.classProctoringDTO.startDate)
        );
        setPaidProctorings(sortedRequests);
      } else {
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

  const fetchAvaliableTAs = async () => {
    try {
      const token = localStorage.getItem("token");
      console.log("Selected PPR:", selectedPPR);
      const response = await axios.get("http://localhost:8080/ta/getAvailableTAsByDepartmentExceptProctoring", {
        params: {
          departmentCode: selectedPPR.departmentName === "Computer Engineering" ? "CS" : "IE",
          proctoringId: selectedPPR.classProctoringDTO.id,
        },
        headers: {
          Authorization: `Bearer ${token}`
        }
      }); // Adjust the URL as needed);
      if (response.data) {
        setAvailableTAs(response.data);
      }
      else{
        console.log("No paid proctoring requests found.");
      }
    } catch (error) {
      console.error("Error fetching paid proctoring requests:", error);
      if (error.response && error.response.data) {
        alert(error.response.data.message);
      }
      else {
        alert("An error occurred during fetching avaliable TAs. Please try again.");
      }
    }
  };

  const handleAutomaticSelect = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.get(`http://localhost:8080/authStaffProctoringRequestController/selectAuthStaffProctoringRequestAutomaticallyInDepartment`, {
        params: {
          classProctoringId: selectedPPR.classProctoringDTO.id,
          departmentCode: selectedPPR.departmentName === "Computer Engineering" ? "CS" : "Industrial Engineering" ? "IE" : null,
          count: taSelectRestrictionsCount,
          eligibilityRestriction: taSelectRestrictionsEligibility,
          oneDayRestriction: taSelectRestrictionsOneDay,
        },
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      if (response.data) {
        alert("Automatic selection completed successfully.");
        setSelectedAppliedStudentsId(response.data.map((student) => student.userId));
      } else {
        alert("Failed to complete automatic selection. Please try again.");
      }
    } catch (error) {
      console.error("Error during automatic selection:", error);
      if (error.response && error.response.data) {
        alert(error.response.data.message);
      }
      else {
        alert("An error occurred during automatic selection. Please try again.");
      }
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
        if (answer) {
          alert("Request accepted successfully.");
        } else {
          alert("Request rejected successfully.");
        }
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

  const isFinished = () => {
    const currentDate = new Date();
    const selectedPPRApplicationFinishDate = new Date(selectedPPR.finishDate);
    return currentDate > selectedPPRApplicationFinishDate;
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
    return sortedSearchAppliedTAs.map((ta) => (
      <DS_DashboardTAItem name={ta.name} surname={ta.surname} onSelect={()=> handleASC(ta.userId)} isSelected={selectedAppliedStudentsId.includes(ta.userId)} id={ta.bilkentId} bgColor={""} email={ta.email} paidProctoringCount={ta.paidProctoringCount}/>
    ));
  }
  const createAvaliableTAItems = () => {
    return sortedSearchAvailableTAs.map((ta) => (
      <DS_DashboardTAItem name={ta.name} surname={ta.surname} onSelect={()=> handleASC(ta.userId)} isSelected={selectedAppliedStudentsId.includes(ta.userId)} id={ta.bilkentId} bgColor={""} email={ta.email} paidProctoringCount={ta.paidProctoringCount}/>
    ));
  }

  const fetchNotifications = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.get("http://localhost:8080/notification/get",{
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      const sortedNotifications = response.data.sort((a, b) => new Date(b.date) - new Date(a.date));
      setNotifications(sortedNotifications);
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
    if (selectedPPR) {
      fetchAppliedStudents();
      fetchAvaliableTAs();
    }
  }, [paidProctorings, selectedPPR]);

  useEffect(() => {
    const sortAndSearch = (taList) => {
      let filtered = [...taList];
  
      // Search (by name or surname)
      if (searchText.trim() !== "") {
        const lower = searchText.toLowerCase();
        filtered = filtered.filter(
          (ta) =>
            ta.name.toLowerCase().includes(lower) ||
            ta.surname.toLowerCase().includes(lower)
        );
      }
  
      // Sort by name
      if (sortName === "asc") {
        filtered.sort((a, b) => a.name.localeCompare(b.name));
      } else if (sortName === "desc") {
        filtered.sort((a, b) => b.name.localeCompare(a.name));
      }
  
      // Sort by workload
      if (sortWorkload === "low") {
        filtered.sort((a, b) => a.workload - b.workload);
      } else if (sortWorkload === "high") {
        filtered.sort((a, b) => b.workload - a.workload);
      }
  
      return filtered;
    };
  
    setSortedSearchAppliedTAs(sortAndSearch(appliedTAs));
    setSortedSearchAvailableTAs(sortAndSearch(avaliableTAs));
  }, [appliedTAs, avaliableTAs, searchText, sortName, sortWorkload]);
  
  
  useEffect(() => {
    console.log(paidProctorings);
    console.log(selectedPPR);
    console.log(appliedTAs);
    console.log(avaliableTAs);
    console.log(selectedAppliedStudentsId);
    console.log(taSelectRestrictionsCount);
    console.log(taSelectRestrictionsEligibility);
    console.log(taSelectRestrictionsOneDay);
  }, [appliedTAs, selectedAppliedStudentsId, selectedPPR, paidProctorings, taSelectRestrictionsCount, taSelectRestrictionsEligibility, taSelectRestrictionsOneDay, avaliableTAs]);

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
                <div className="ds-ta-select-restrictions">
                    <div className="ds-ta-select-restrictions-inputs">
                      <label htmlFor="taSelectRestrictionsCount">TA Select Restrictions Count</label>
                      <input type="number" min={1} id="taSelectRestrictionsCount" name="taSelectRestrictionsCount" value={taSelectRestrictionsCount} onChange={(e) => setTASelectRestrictionsCount(e.target.value)} />
                    </div>
                    <div className="ds-ta-select-restrictions-inputs">
                      <label htmlFor="taSelectRestrictionsEligibility">TA Select Restrictions Eligibility</label>
                      <input type="checkbox" id="taSelectRestrictionsEligibility" name="taSelectRestrictionsEligibility" value={taSelectRestrictionsEligibility} checked={taSelectRestrictionsEligibility} onChange={(e) => setTASelectRestrictionsEligibility(e.target.checked)} />
                    </div>
                    <div className="ds-ta-select-restrictions-inputs">
                      <label htmlFor="taSelectRestrictionsOneDay">TA Select Restrictions One Day</label>
                      <input type="checkbox" id="taSelectRestrictionsOneDay" name="taSelectRestrictionsOneDay" value={taSelectRestrictionsOneDay} checked={taSelectRestrictionsOneDay} onChange={(e) => setTASelectRestrictionsOneDay(e.target.checked)} />
                    </div>
                    
                  </div>
                <button onClick={()=> handleAutomaticSelect()}>Automatic Select</button>
              </div>
              <div className="ds-dashboard-avaliable-ta-list">{createAvaliableTAItems()}</div>
            </div>) : null}
          </div>
        </div>

        {/* RIGHT SIDE */}
        <div className="dashboard-right">
          <div className="notifications">
          <h3>Notifications</h3>
          <div className="ds-dashboard-notification-panel">
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
                      return (isManualAssignment ? (
                        avaliableTAs.filter((ta) => ta.userId === id).map(({ name,surname, bilkentId }) => (
                          <div className="ds-dashboard-ta-list-item-content">
                            <div className="ds-dashboard-ta-list-item-name">Name: {name} {surname}</div>
                            <div className="ds-dashboard-ta-list-item-id">Student ID: {bilkentId}</div>
                          </div>
                        )))
                        :(
                        appliedTAs.filter((ta) => ta.userId === id).map(({ name,surname, bilkentId }) => (
                        <div className="ds-dashboard-ta-list-item-content">
                          <div className="ds-dashboard-ta-list-item-name">Name: {name} {surname}</div>
                          <div className="ds-dashboard-ta-list-item-id">Student ID: {bilkentId}</div>
                        </div>)
                      )))
                    }               
                    )}
                  </div>
                  
                ) : (
                  <div className="no-ta">No TAs available</div>
                )}
              </div>
              <div className="buttons">
                {isAppliedAssignment ? (isFinished() ? <button onClick={()=> handleForceAssignment()}>Force Assignment</button> : null) : <button onClick={()=> handleForceAssignment()}>Force Assignment</button>}
                {isManualAssignment ? <button onClick={()=> handleOfferAssignment()}>Offer Assignment</button>: null}
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