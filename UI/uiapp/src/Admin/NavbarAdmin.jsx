import React from "react";
import { NavLink, useNavigate, useLocation } from "react-router-dom";
import "./NavbarAdmin.css";
import LogoutModal from "../LogoutModal";

const NavbarAdmin = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [isModalOpen, setIsModalOpen] = React.useState(false);

  const openModal = () => setIsModalOpen(true);
  const closeModal = () => setIsModalOpen(false);

  const handleLogout = () => {
    setIsModalOpen(false);
    navigate("/");
  };




  return (
    <>
      <header className="navbar">
        <div className="title">Bilkent Admin Panel</div>
        <nav className="nav-links">
          
          <NavLink to="/admin-database" className={({ isActive }) => (isActive ? "active" : "")}>Database</NavLink>
          <NavLink to="/admin-logs" className={({ isActive }) => (isActive ? "active" : "")}>Logs</NavLink>
          <NavLink to="/admin-profile" className={({ isActive }) => (isActive ? "active" : "")}>Profile</NavLink>


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

export default NavbarAdmin;
