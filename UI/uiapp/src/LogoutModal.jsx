import React from "react";
import "./LogoutModal.css";


/**
 * LogoutModal Component
 * Displays a modal dialog asking the user to confirm logout from the system.
 */



/**
 * Renders the logout confirmation modal with Cancel and Confirm buttons.
 */
const LogoutModal = ({ isOpen, onCancel, onConfirm }) => {
  if (!isOpen) return null;

  return (
    <div className="modal-overlay">
      <div className="modal">
        <h3>Logout Confirmation</h3>
        <p>You are about to quit Bilkent TA Management System. Do you want to continue?</p>
        <div className="modal-buttons">
          <button className="cancel-button" onClick={onCancel}>Cancel</button>
          <button className="apply-button" onClick={onConfirm}>Confirm</button>
        </div>
      </div>
    </div>
  );
};

export default LogoutModal;
