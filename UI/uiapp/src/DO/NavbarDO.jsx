import React from "react";
import { NavLink, useNavigate, useLocation } from "react-router-dom";
import "./NavbarDO.css";
import LogoutModal from "../LogoutModal";

const NavbarDO = () => {
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
        <div className="title">Bilkent TA Managament System</div>
        <nav className="nav-links">
          
          <NavLink to="/" className={({ isActive }) => (isActive ? "active" : "")}>Dashboard</NavLink>
          <NavLink to="/" className={({ isActive }) => (isActive ? "active" : "")}>TA Requests</NavLink>
          <NavLink to="/do-exams" className={({ isActive }) => (isActive ? "active" : "")}>Exams</NavLink>
          <NavLink to="/" className={({ isActive }) => (isActive ? "active" : "")}>Create Exam</NavLink>
          <NavLink to="/do-profile" className={({ isActive }) => (isActive ? "active" : "")}>Profile</NavLink>

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

export default NavbarDO;
