import React, { useEffect, useState } from "react";
import "./AutomaticAssignmentModal.css";
import TAItem from "./TAItem";
import axios from "axios";

const AutomaticAssignmentModal = ({ isOpen, onClose, suggestedTAs }) => {
  if (!isOpen) return null;

  return (
    <div className="auto-modal-backdrop">
      <div className="auto-modal">
        <h2>Suggested TAs</h2>

        <div className="auto-modal-list">
          {suggestedTAs.length > 0 ? (
            suggestedTAs.map((ta) => (
              <TAItem
                key={`${ta.firstName || ta.name}-${ta.lastName || ta.surname}-${ta.email}`}
                ta={ta}
                isSelected={false}
                inInstructor={true}
              />
            ))
          ) : (
            <p>No TAs found.</p>
          )}
        </div>

        <div className="auto-modal-actions">
          <button className="auto-button cancel" onClick={onClose}>Close</button>
        </div>
      </div>
    </div>
  );
};
  

export default AutomaticAssignmentModal;
