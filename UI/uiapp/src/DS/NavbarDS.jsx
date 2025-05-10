import React from "react";
import { NavLink, useNavigate, useLocation } from "react-router-dom";
import "./NavbarDS.css";
import LogoutModal from "../LogoutModal";


/**
 * NavbarDS component
 * Top navigation bar for Department Secretary users.
 * Includes navigation links and logout functionality.
 */

const NavbarDS = () => {
  const navigate = useNavigate();
  const location = useLocation();

  // Controls the visibility of the logout confirmation modal
  const [isModalOpen, setIsModalOpen] = React.useState(false);

  const openModal = () => setIsModalOpen(true);
  const closeModal = () => setIsModalOpen(false);

  /**
 * Handles logout logic (currently placeholder).
 * Redirects user to the login or landing page.
 */
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
          <NavLink to="/ds-dashboard" className={({ isActive }) => (isActive ? "active" : "")}>Dashboard</NavLink>
          <NavLink to="/ds-profile" className={({ isActive }) => (isActive ? "active" : "")}>Profile</NavLink>
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

export default NavbarDS;
