// components/ManualAssignModal.jsx
import React from "react";
import "./ManualAssignmentModal.css";


/**
 * ManualAssignmentModal Component
 * Displays a modal that lets instructors either force assign a TA or send a request for assignment.
 */

/**
 * Renders the manual assignment modal UI with options to force assign, send request, or cancel.
 */
const ManualAssignmentModal = ({ isOpen, onForceAssign, onSendRequest, onCancel }) => {
  if (!isOpen) return null;

  return (
    <div className="manual-modal-overlay">
      <div className="manual-modal">
        <h3>Manual Assignment</h3>
        <p>Please choose how to proceed with this TA assignment:</p>
        <div className="manual-modal-buttons">
          <button className="manual-force-button" onClick={onForceAssign}>Force Assign</button>
          <button className="manual-request-button" onClick={onSendRequest}>Send Request</button>
          <button className="manual-cancel-button" onClick={onCancel}>Cancel</button>
        </div>
      </div>
    </div>
  );
};

export default ManualAssignmentModal;
