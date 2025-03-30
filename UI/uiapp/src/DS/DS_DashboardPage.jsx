import React, { act, useState } from "react";
import NavbarDS from "./NavbarDS";
import "./DS_DashboardPage.css";

const DS_DashboardPage = () => {
  const [activeTab, setActiveTab] = useState("pending");
  const [selectedRequest, setSelectedRequest] = useState(null);
  const [searchText, setSearchText] = useState("");
  const [sortName, setSortName] = useState("");  
  const [sortWorkload, setSortWorkload] = useState("");
  const [tas, setTas] = useState([
    { name: 'Ali 1' },
    { name: 'Ali 12' },
    { name: 'Ali 15' },
    { name: 'Ali 15' },
    { name: 'Ali 15' },
    { name: 'Ali 15' },
    { name: 'Ali 15' },{ name: 'Ali 15' },{ name: 'Ali 15' },{ name: 'Ali 15' },{ name: 'Ali 15' },{ name: 'Ali 15' }, // You can dynamically add or remove TAs here
    // You can add more TAs dynamically later
  ]);
  
  const handleSearch = () => {
    console.log("Searching for:", searchText);
  };

  const handleTabClick = (tab) => {
    setActiveTab(tab);
    setSelectedRequest(null);
  };

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
              <div className="placeholder">[ Load and display SENT requests from DB — click to select one ]</div>
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
                        tas.map((ta, index) => (
                      <div key={index} className="ta-item">
                        {ta.name}
                      </div>
                    ))
                  ) : (
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
                    tas.map((ta, index) => (
                      <div key={index} className="ta-item">
                        {ta.name}
                      </div>
                    ))
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