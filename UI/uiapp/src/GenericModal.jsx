import React from "react";
import "./GenericModal.css";

/**
 * GenericModal Component
 * Reusable modal component that accepts a title, visibility toggle, close handler, and children elements.
 * Used across the application to wrap content inside a consistent modal layout.
 */
const GenericModal = ({ open, onClose, title, children }) => {
  if (!open) return null;

  // Prevent clicks inside the container from closing the modal

    /**
   * Prevents click events inside the modal from propagating to the backdrop,
   * which would otherwise close the modal unintentionally.
   */
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
