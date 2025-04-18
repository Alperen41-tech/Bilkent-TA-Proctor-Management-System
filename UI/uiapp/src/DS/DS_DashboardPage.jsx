import React, { act, useState } from "react";
import NavbarDS from "./NavbarDS";
import "./DS_DashboardPage.css";
import DS_PaidProctoringRequestItem from "./DS_PaidProctoringRequestItem";
import { id } from "date-fns/locale";
import DS_DashboardTAItem from "./DS_DashboardTAItem";

const DS_DashboardPage = () => {
  const [selectedAppliedStudentsId, setSelectedAppliedStudentsId] = useState([]);
  const [selectedPPRId, setSelectedPPRId] = useState(null);
  const [activeTab, setActiveTab] = useState("pending");
  const [selectedRequest, setSelectedRequest] = useState(null);
  const [searchText, setSearchText] = useState("");
  const [sortName, setSortName] = useState("");  
  const [sortWorkload, setSortWorkload] = useState("");
  const [tas, setTas] = useState([
    { name: 'Ali 1', id: 1 , email: "basdaas@gmail.com"},
    { name: 'Ali 12', id: 2 , email: "basdaas@gmail.com"},
    { name: 'Ali 13', id: 3, email: "basdaas@gmail.com" },
    { name: 'Ali 14', id: 4, email: "basdaas@gmail.com" },
    { name: 'Ali 15', id: 5, email: "basdaas@gmail.com" },
    { name: 'Ali 16', id: 6, email: "basdaas@gmail.com" },
    { name: 'Ali 17', id: 7, email: "basdaas@gmail.com" },
    { name: 'Ali 18', id: 8, email: "basdaas@gmail.com" },
    { name: 'Ali 19', id: 9, email: "basdaas@gmail.com" },
    { name: 'Ali 20', id: 10, email: "basdaas@gmail.com" },
    { name: 'Ali 21', id: 11, email: "basdaas@gmail.com" },
    // You can add more TAs dynamically later
  ]);
  
  const handleSearch = () => {
    console.log("Searching for:", searchText);
  };

  const handleTabClick = (tab) => {
    setActiveTab(tab);
    setSelectedRequest(null);
    setSelectedPPRId(null);
  };
  const handlePPRClick = (id) => {
    setSelectedPPRId(id);
  }
  const handleASC = (id) => { // Handle applied students click
    if (!selectedAppliedStudentsId.includes(id)) {
      setSelectedAppliedStudentsId((prev) => [...prev, id]);
    }
    else {
      setSelectedAppliedStudentsId((prev) => prev.filter((studentId) => studentId !== id));
    }
    console.log("Selected TA:", id);
  }

  const createPaidProctoringRequests = () => {
    const paidProctoringRequests = [
      {
        id: 1,
        date: { month: "Jan", day: 1, weekday: "Mon" },
        time: { start: "10:00 AM", end: "12:00 PM" },
        role: "Proctor",
        duration: 2,
        name: "Ali",
        numOfTaNeeded: 2,
      },
      {
        id: 2,
        date: { month: "Jan", day: 1, weekday: "Mon" },
        time: { start: "10:00 AM", end: "12:00 PM" },
        role: "Proctor",
        duration: 2,
        name: "Ali",
        numOfTaNeeded: 2,
      },
      // Add more requests as needed
    ];
    return paidProctoringRequests.map((request) => (
      <DS_PaidProctoringRequestItem id={request.id} {...request} onInform={() => console.log("TAs informed for this request")} isSelected={selectedPPRId === request.id} onSelect={handlePPRClick} />
    ));
  }

  const createAppliedTAItems = () => {
    return tas.map((ta) => (
      <DS_DashboardTAItem name={ta.name} onSelect={handleASC} isSelected={selectedAppliedStudentsId.includes(ta.id)} id={ta.id} bgColor={""} email={ta.email}/>
    ));
  }
  const createAvaliableTAItems = () => {
    return tas.map((ta) => (
      <DS_DashboardTAItem name={ta.name} onSelect={handleASC} isSelected={selectedAppliedStudentsId.includes(ta.id)} id={ta.id} bgColor={""} email={ta.email}/>
    ));
  }


  return (
    <div className="dashboard-page">
      <NavbarDS />
      <div className="dashboard-grid">
        {/* LEFT SIDE */}
        <div className="dashboard-left">
          {/* Tabs */}
          <div className="top-left">
          <div className="tab-bar">
            <button onClick={() => handleTabClick("pending")} className={activeTab === "pending" ? "active" : ""}>Paid Proctoring Requests</button>
            <button onClick={() => handleTabClick("received")} className={activeTab === "received" ? "active" : ""}>Select Paid Proctoring TAs</button>
          </div>

          {/* Top Left Panel */}
          <div className="tab-content">
            {activeTab === "pending" && (
              <div>{createPaidProctoringRequests()}</div>
            )}
            {activeTab === "received" && (
              <div className="placeholder">[ Load RECEIVED requests from DB — click to select one ]</div>
            )}
          </div>
          </div>
          {/* Bottom Left Panel */}
          <div className="bottom-left">
            {activeTab === "pending"  ? (
              <div className="details-panel">
                <h3>Details</h3>
                {selectedRequest ? (
                  <div>
                    <p><strong>To:</strong> {selectedRequest.receiver}</p>
                    <p><strong>Reason:</strong> {selectedRequest.reason}</p>
                    <p><strong>Time:</strong> {selectedRequest.timestamp}</p>
                  </div>
                ) : (
                  <p className="placeholder">[ Click a request to see its details ]</p>
                )}
              </div>
            ) : activeTab === "received" ? (
                <div className="ta-list-container">
                    <h3 className="ta-list-title">Applied Studens</h3>
                    <div className="ta-list">
                    {tas.length > 0 ? (
                        <div>{createAppliedTAItems()}</div>
                    )
                   : (
                    <div className="no-ta">No TAs available</div>
                  )}
                </div>
                <div className="buttons">
                    <button >Accept</button>
                    <button >Automatic</button>
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

          <div className="right-bottom">
            {activeTab === "pending" && (
                <div className="card ta-list">
                <h3>Available TAs</h3>
      
                <div className="filters">
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
                  <button onClick={handleSearch}>Apply</button>
                  <button onClick={handleSearch}>Automatic Assign</button>
                </div>
      
                <div className="placeholder">[ TA list from DB ]</div>
              </div>
            )}
            {activeTab === "received" && (
                <div className="ta-list-container">
                <h3 className="ta-list-title">TA List</h3>
                <div className="ta-list">
                  {tas.length > 0 ? (
                    <div>
                      {selectedAppliedStudentsId.map((id) => {
                        return (
                          tas.filter((ta) => ta.id === id).map(({ name, id }) => (
                          <div className="ds-dashboard-ta-list-item-content">
                            <div className="ds-dashboard-ta-list-item-name">Name: {name}</div>
                            <div className="ds-dashboard-ta-list-item-id">Student ID: {id}</div>
                          </div>
                        )))
                      }               
                      )}
                    </div>
                    
                  ) : (
                    <div className="no-ta">No TAs available</div>
                  )}
                </div>
                <button>Notify Deans Office</button>
              </div>
            )}
         </div>
        </div>
      </div>
    </div>
  );
};
export default DS_DashboardPage;