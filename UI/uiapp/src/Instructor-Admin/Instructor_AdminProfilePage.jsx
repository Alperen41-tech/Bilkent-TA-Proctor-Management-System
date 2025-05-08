// src/Instructor_AdminProfilePage.jsx
import React from "react";
import "./Instructor_AdminProfilePage.css"; // Reusing the same CSS
import Instructor_AdminNavbar from "./Instructor_AdminNavbar";
import axios from "axios";
import { useState, useEffect, useRef} from "react";

const Instructor_AdminProfilePage = () => {
  const [showChangePasswordModal, setShowChangePasswordModal] = useState(false);
  const [insProfileInfo, setInsProfileInfo] = useState({courses: []});
  const [selectedFile, setSelectedFile] = useState(null);

  //Refs for password inputs
  const oldPasswordRef = useRef();
  const newPasswordRef = useRef();
  const confirmNewPasswordRef = useRef();
  //-----------------------------------------------------------

  const handleImportClick = async () => {
    if (!selectedFile) {
      alert("Please select a file.");
      return;
    }

    const formData = new FormData();
    formData.append("file", selectedFile);

    try {
      const response = await axios.post("http://localhost:8080/excel/processTAAssignmentExcel", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });

      if (response.status === 200) {
        alert("Excel file successfully. Imported.");
      } else {
        alert("Upload failed.");
      }
    } catch (error) {
      console.error("File dump error:", error);
      alert("A problem occured.");
    }
  };

  const handleFileChange = (e) => {
      if (e.target.files && e.target.files[0]) {
        setSelectedFile(e.target.files[0]);
      }
    };

  const handleGetTaConstraints = async () => {
    try {
      const response = await axios.get("http://localhost:8080/excel/getRequirementsExcel", {
        responseType: "blob", // 🔥 this is crucial to receive binary data correctly
      });
  
      // Create a blob and a temporary download link
      const blob = new Blob([response.data], {
        type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
      });
  
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", "TARequirements.xlsx"); // file name
      document.body.appendChild(link);
      link.click();
      link.remove(); // clean up
  
      console.log("TA constraints downloaded successfully.");
    } catch (error) {
      console.error("Error downloading TA constraints:", error);
      alert("Failed to download TA constraints.");
    }
  };
  

  const handleChangePassword = async () => {
    try {
      console.log("Email:", insProfileInfo.email);
      console.log("Old Password:", oldPasswordRef.current.value);
      console.log("New Password:", newPasswordRef.current.value);
      console.log("Confirm New Password:", confirmNewPasswordRef.current.value);
      const response = await axios.put("http://localhost:8080/auth/changePassword", {
        email: insProfileInfo.email,
        oldPassword: oldPasswordRef.current.value,
        newPassword: newPasswordRef.current.value,
        userTypeName: "instructor"
      });
      if (response.data) {
        alert("Password changed successfully.");
        setShowChangePasswordModal(false);
      } else {
        alert("Failed to change password. Please try again.");
      }
    } catch (error) {
      console.error("Error changing password:", error);
    }
  
  };

  useEffect(() => {
    const fetchProfileInformation = async () => {
      try {
        const token = localStorage.getItem("token");
        const response = await axios.get("http://localhost:8080/instructor/profile", {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });
        setInsProfileInfo(response.data);
        console.log(insProfileInfo);
      } catch (error) {
        console.error("Error fetching tasks:", error);
      }
    };
    fetchProfileInformation();
  }, []);


  return (
    <div className="profile-container">
      <Instructor_AdminNavbar />

      <div className="profile-content">
        {/* Info Section */}
        <div className="info-card">
          <h3>Personal Information</h3>
          <p><strong>Name</strong><br />{insProfileInfo.name}</p>
          <p><strong>Surname</strong><br />{insProfileInfo.surname}</p>
          <p><strong>Email</strong><br />{insProfileInfo.email}</p>
          <p><strong>ID</strong><br />{insProfileInfo.bilkentId}</p>
          <p><strong>Role</strong><br />{insProfileInfo.role}</p>
          <p><strong>Department</strong><br />{insProfileInfo.departmentName}</p>
          <p><strong>Course(s)</strong><br />{insProfileInfo.courses.map((courseName, index) => {
            console.log(courseName);
            return (
              <span key={index}>
                {courseName}{index < insProfileInfo.courses.length - 1 ? ", " : ""}
              </span>
            );
          })}</p>
        </div>

        {/* Manage Account Section */}
        <div className="right-section">
          <div className="manage-card">
            <h3>Manage Account</h3>
            <button className="purple-button" onClick={() => setShowChangePasswordModal(true)}>Change Password</button>
            <button className="purple-button" onClick={() => handleGetTaConstraints()}>Get Instructor's TA constraints</button>
          </div>
          <div className="ins-admin-dump-new-data">
            <h3>Dump New Data</h3>
            <div className="ins-admin-upload-container">
              {!selectedFile && 
                <div className="ins-admin-drag-drop-area">              
                  <p>Drag &amp; Drop file here</p>
                  <p>or</p>
                  <label htmlFor="file-upload" className="ins-admin-choose-file-label">
                    Choose File 📄
                  </label>
                  <input
                    id="file-upload"
                    type="file"
                    onChange={handleFileChange}
                    style={{ display: "none" }}
                  />
                  <p>Supported formats: Excel</p>
                </div>
              }
              {selectedFile && (
                <div className="ins-admin-drag-drop-area" style={{
                  backgroundColor: "#e0ffe0",
                  border: "2px dashed #4CAF50",
                  padding: "16px",
                  borderRadius: "8px",
                  textAlign: "center",
                }}
            >
                  
                  <p style={{ fontWeight: "bold", color: "#2e7d32" }}>
                    ✅ File successfully selected!
                  </p>
                  <p><strong>Name:</strong> {selectedFile.name}</p>
                  <p><strong>Size:</strong> {(selectedFile.size / 1024).toFixed(2)} KB</p>
                </div>
              )}
              <div className="ins-admin-upload-buttons">
                <button onClick={handleImportClick} className="ins-admin-import-button">Import</button>
                <button className="ins-admin-cancel-button" onClick={() => setSelectedFile(null)}>Cancel</button>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Change Password Modal */}
      {showChangePasswordModal && (
        <div className="modal-overlay">
          <div className="modal">
            <h3>Change Password</h3>
            <label>Old Password</label>
            <input ref={oldPasswordRef} type="password" placeholder="Enter your old password"/>
            <label>New Password</label>
            <input ref={newPasswordRef} type="password" placeholder="At least 8 characters long" />
            <label>Confirm New Password</label>
            <input ref={confirmNewPasswordRef} type="password" placeholder="Confirm new password" />
            <div className="modal-buttons">
              <button className="cancel-button" onClick={() => setShowChangePasswordModal(false)}>Cancel</button>
              <button className="apply-button" onClick={() => {
                if(!oldPasswordRef.current.value || !newPasswordRef.current.value || !confirmNewPasswordRef.current.value) {
                  alert("Please fill in all fields.");
                  return;
                }
                if(newPasswordRef.current.value !== confirmNewPasswordRef.current.value) {
                  alert("New password and confirmation do not match.");
                  return;
                }
                if(newPasswordRef.current.value.length < 8) {
                  alert("New password must be at least 8 characters long.");
                  return;
                }
                handleChangePassword();
              }}>Apply</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Instructor_AdminProfilePage;
