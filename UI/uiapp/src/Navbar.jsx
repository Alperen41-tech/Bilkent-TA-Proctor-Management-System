import React from "react";
import { NavLink, useNavigate, useLocation } from "react-router-dom";
import "./Navbar.css";
import LogoutModal from "./LogoutModal";

const Navbar = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [isModalOpen, setIsModalOpen] = React.useState(false);

  const openModal = () => setIsModalOpen(true);
  const closeModal = () => setIsModalOpen(false);

  const handleLogout = () => {
    // TODO: Add real logout logic here if needed
    setIsModalOpen(false);
    navigate("/");
  };

  return (
    <>
      <header className="navbar">
        <div className="title">Bilkent TA Management System</div>
        <nav className="nav-links">
          <NavLink to="/dashboard" className={({ isActive }) => (isActive ? "active" : "")}>Dashboard</NavLink>
          <NavLink to="/schedule" className={({ isActive }) => (isActive ? "active" : "")}>My Schedule</NavLink>
          <NavLink to="/proctoring" className={({ isActive }) => (isActive ? "active" : "")}>Paid Proctoring</NavLink>
          <NavLink to="/exams" className={({ isActive }) => (isActive ? "active" : "")}>Exams</NavLink>
          <NavLink to="/profile" className={({ isActive }) => (isActive ? "active" : "")}>Profile</NavLink>
          <button className="logout" onClick={openModal}>Logout</button>
        </nav>
      </header>

      <LogoutModal
        isOpen={isModalOpen}
        onCancel={closeModal}
        onConfirm={handleLogout}
      />
    </>
  );
};

export default Navbar;
