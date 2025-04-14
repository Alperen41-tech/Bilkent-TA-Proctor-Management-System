import React from "react";
import "./GenericModal.css";

const GenericModal = ({ open, onClose, title, children }) => {
  if (!open) return null;

  // Prevent clicks inside the container from closing the modal
  const stopPropagation = (e) => e.stopPropagation();

  return (
    <div className="modal-backdrop" onClick={onClose}>
      <div className="modal-container" onClick={stopPropagation}>
        <h2>{title}</h2>
        {children}
        <button onClick={onClose} className="close-button">
          Close
        </button>
      </div>
    </div>
  );
};

export default GenericModal;
