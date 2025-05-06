import React from "react";
import { NavLink, useNavigate, useLocation } from "react-router-dom";
import "./Instructor_AdminNavbar.css";
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
        <div className="title">Bilkent TA Management System</div>
        <nav className="nav-links">
          <NavLink to="/instructor-admin-profile" className={({ isActive }) => (isActive ? "active" : "")}>Profile</NavLink>

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
