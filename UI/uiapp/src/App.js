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
