import React, { useState } from "react";
import { BrowserRouter as Router, Routes, Route, useNavigate } from "react-router-dom";
import LoginPage from "./LoginPage";
import LogoutModal from "./LogoutModal";


// Updated paths for components now inside TA folder
import ProfilePage from "./TA/ProfilePage";
import ExamsPage from "./TA/ExamsPage";
import PaidProctoringPage from "./TA/PaidProctoringPage";
import SchedulePage from "./TA/SchedulePage";
import DashboardPage from "./TA/DashboardPage";

import INS_TAsPage from "./Ins/INS_TAsPage";
import InstructorProfilePage from "./Ins/InstructorProfilePage";
import INS_SchedulePage from "./Ins/INS_SchedulePage";
import INS_DashboardPage from "./Ins/INS_DashboardPage";


import INS_ExamsPage from "./Ins/INS_ExamsPage";
import DS_ProfilePage from "./DS/DS_ProfilePage";
import DS_DashboardPage from "./DS/DS_DashboardPage";



import AdminProfilePage from "./Admin/AdminProfilePage";
//import AdminDatabasePage from "./Admin/AdminDatabasePage";
import AdminLogsPage from "./Admin/AdminLogsPage";

import DO_ProfilePage from "./DO/DO_ProfilePage";


function App() {
  const [showLogoutModal, setShowLogoutModal] = useState(false);
  const navigate = useNavigate();

  const handleLogout = () => setShowLogoutModal(true);
  const confirmLogout = () => {
    setShowLogoutModal(false);
    navigate("/");
  };
  const cancelLogout = () => setShowLogoutModal(false);

  return (
    <>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/profile" element={<ProfilePage onLogout={handleLogout} />} />
        <Route path="/exams" element={<ExamsPage />} />
        <Route path="/proctoring" element={<PaidProctoringPage />} />
        <Route path="/schedule" element={<SchedulePage />} />
        <Route path="/dashboard" element={<DashboardPage />} />

        <Route path="/ins-tas" element={<INS_TAsPage />} />
        <Route path="/instructor-profile" element={<InstructorProfilePage />} />
        <Route path="/ins-schedule" element={<INS_SchedulePage />} />
        <Route path="/ins-dashboard" element={<INS_DashboardPage />} />
        <Route path="/ins-exam-page" element={<INS_ExamsPage />} />
        <Route path="/ds-profile" element={<DS_ProfilePage />} />
        <Route path="/ds-dashboard" element={<DS_DashboardPage />} />

        <Route path="/admin-profile" element={<AdminProfilePage />} />
        <Route path="/admin-logs" element={<AdminLogsPage />} />

        <Route path="/do-profile" element={<DO_ProfilePage />} />        
        







      </Routes>

      <LogoutModal
        isOpen={showLogoutModal}
        onCancel={cancelLogout}
        onConfirm={confirmLogout}
      />
    </>
  );
}

export default function WrappedApp() {
  return (
    <Router>
      <App />
    </Router>
  );
}
