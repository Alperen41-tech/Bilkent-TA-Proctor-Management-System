import React, { useState, useEffect } from "react";
import "./InstructorAdditionalTAModal.css";

const InstructorAdditionalTAModal = ({ isOpen, onCancel, onConfirm }) => {
  const [taCount, setTaCount] = useState(1);
  const [description, setDescription] = useState("");

  useEffect(() => {
    if (isOpen) {
      setTaCount(1);
      setDescription("");
    }
  }, [isOpen]);

  if (!isOpen) return null;

  return (
    <div className="modal-overlay">
      <div className="modal">
        <h3>Request Additional TAs</h3>
        <p>Please enter how many TAs you need and an optional description:</p>

        <label>TA Count</label>
        <input
          type="number"
          min="1"
          value={taCount}
          onChange={(e) => setTaCount(Number(e.target.value))}
          className="modal-input"
        />

        <label>Description</label>
        <textarea
          rows="4"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          className="modal-textarea"
          placeholder="Provide a reason (optional)..."
        />

        <div className="modal-buttons">
          <button className="cancel-button" onClick={onCancel}>Cancel</button>
          <button className="apply-button" onClick={() => onConfirm(taCount, description)}>Confirm</button>
        </div>
      </div>
    </div>
  );
};

export default InstructorAdditionalTAModal;
