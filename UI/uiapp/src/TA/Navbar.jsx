import React from "react";
import { NavLink, useNavigate, useLocation } from "react-router-dom";
import "./Navbar.css";
import LogoutModal from "../LogoutModal";


/**
 * Navbar component
 * Displays the navigation header for TAs with links to main pages like Dashboard, Schedule, Proctoring, Exams, and Profile.
 * Includes a logout button that triggers a confirmation modal.
 */
const Navbar = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [isModalOpen, setIsModalOpen] = React.useState(false);
  /**
   * Opens the logout confirmation modal.
   */
  const openModal = () => setIsModalOpen(true);
  const closeModal = () => setIsModalOpen(false);
  /**
   * Handles user logout and redirects to the home/login page.
   */
  const handleLogout = () => {
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
