import React, { useEffect, useState } from "react";
import "./AutomaticAssignmentModal.css";
import TAItem from "./TAItem";

const AutomaticAssignmentModal = ({ isOpen, onClose, suggestedTAs }) => {
  const [selectedTAKeys, setSelectedTAKeys] = useState([]);
  const [forceAssign, setForceAssign] = useState(false); // ✅ NEW

  const toggleSelectTA = (ta) => {
    const key = `${ta.firstName || ta.name}-${ta.lastName || ta.surname}-${ta.email}`;
    setSelectedTAKeys((prev) =>
      prev.includes(key) ? prev.filter((k) => k !== key) : [...prev, key]
    );
  };

  const isSelected = (ta) => {
    const key = `${ta.firstName || ta.name}-${ta.lastName || ta.surname}-${ta.email}`;
    return selectedTAKeys.includes(key);
  };

  const handleConfirm = () => {
    const selectedTAs = suggestedTAs.filter((ta) =>
      selectedTAKeys.includes(
        `${ta.firstName || ta.name}-${ta.lastName || ta.surname}-${ta.email}`
      )
    );
    console.log("✅ Selected TAs to assign:", selectedTAs);
    console.log("🚨 Force Assign Enabled:", forceAssign);
    // TODO: Send selectedTAs and forceAssign flag to backend
    onClose();
  };

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
                isSelected={isSelected(ta)}
                onClick={() => toggleSelectTA(ta)}
                inInstructor={true}
              />
            ))
          ) : (
            <p>No TAs found.</p>
          )}
        </div>

        {/* ✅ Force Assign Checkbox */}
        <div style={{ marginTop: "1rem" }}>
          <label>
            <input
              type="checkbox"
              checked={forceAssign}
              onChange={(e) => setForceAssign(e.target.checked)}
            />
            {" "}Force Assign
          </label>
        </div>

        <div className="auto-modal-actions">
          <button className="auto-button cancel" onClick={onClose}>
            Cancel
          </button>
          <button className="auto-button confirm" onClick={handleConfirm}>
            Confirm
          </button>
        </div>
      </div>
    </div>
  );
};

export default AutomaticAssignmentModal;
