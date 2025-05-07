import React from "react";
import "./AutomaticAssignmentModal.css";

const AutomaticAssignmentModal = ({ isOpen, onClose }) => {
  if (!isOpen) return null;

  return (
    <div className="auto-modal-backdrop">
      <div className="auto-modal">
        <h2>Suggested TAs</h2>

        <div className="auto-modal-list">
          {/* Example placeholder: Replace with fetched TAs */}
          <p>No TAs loaded yet.</p>
        </div>

        <div className="auto-modal-actions">
          <button className="auto-button cancel" onClick={onClose}>Cancel</button>
          <button className="auto-button dismiss" onClick={onClose}>Dismiss TA</button>
          <button className="auto-button confirm" onClick={onClose}>Confirm</button>
        </div>
      </div>
    </div>
  );
};

export default AutomaticAssignmentModal;
