// components/ManualAssignModal.jsx
import React from "react";
import "./ManualAssignmentModal.css";

const ManualAssignmentModal = ({ isOpen, onForceAssign, onSendRequest, onCancel }) => {
  if (!isOpen) return null;

  return (
    <div className="modal-overlay">
      <div className="modal">
        <h3>Manual Assignment</h3>
        <p>Please choose how to proceed with this TA assignment:</p>
        <div className="modal-buttons">
          <button className="force-button" onClick={onForceAssign}>Force Assign</button>
          <button className="request-button" onClick={onSendRequest}>Send Request</button>
          <button className="cancel-button" onClick={onCancel}>Cancel</button>
        </div>
      </div>
    </div>
  );
};

export default ManualAssignmentModal;
