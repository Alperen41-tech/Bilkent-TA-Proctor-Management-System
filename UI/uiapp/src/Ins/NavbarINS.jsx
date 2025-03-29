import React from "react";
import { NavLink, useNavigate, useLocation } from "react-router-dom";
import "./NavbarINS.css";
import LogoutModal from "../LogoutModal";

const NavbarINS = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [isModalOpen, setIsModalOpen] = React.useState(false);

  const openModal = () => setIsModalOpen(true);
  const closeModal = () => setIsModalOpen(false);

  const handleLogout = () => {
    setIsModalOpen(false);
    navigate("/");
  };



  //CHANGED ALL TO ins-tas FOR NOW!!!!!!!!!!!!!!!!!!!!!
  return (
    <>
      <header className="navbar">
        <div className="title">Bilkent Instructor Panel</div>
        <nav className="nav-links">
          
          <NavLink to="/ins-tas" className={({ isActive }) => (isActive ? "active" : "")}>TAs</NavLink>
          <NavLink to="/ins-tas" className={({ isActive }) => (isActive ? "active" : "")}>Dashboard</NavLink>
          <NavLink to="/ins-schedule" className={({ isActive }) => (isActive ? "active" : "")}>My Schedule</NavLink>
          <NavLink to="/ins-tas" className={({ isActive }) => (isActive ? "active" : "")}>Exams</NavLink>
          <NavLink to="/instructor-profile" className={({ isActive }) => (isActive ? "active" : "")}>Profile</NavLink>

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

export default NavbarINS;
