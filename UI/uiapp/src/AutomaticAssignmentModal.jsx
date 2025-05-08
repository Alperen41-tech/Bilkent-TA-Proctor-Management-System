import React, { useState } from "react";
import "./AutomaticAssignmentModal.css";
import TAItem from "./TAItem";
import axios from "axios";

const AutomaticAssignmentModal = ({ isOpen, onClose, suggestedTAs, selectedExamId, refreshAfterAssignment }) => {
  const [selectedTAKeys, setSelectedTAKeys] = useState([]);
  const [forceAssign, setForceAssign] = useState(false);

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

  const handleConfirm = async () => {
    const selectedTAs = suggestedTAs
      .filter((ta) =>
        selectedTAKeys.includes(
          `${ta.firstName || ta.name}-${ta.lastName || ta.surname}-${ta.email}`
        )
      )
      .map((ta) => ({ userId: ta.userId || ta.id }));

    if (selectedTAs.length === 0) {
      alert("Please select at least one TA.");
      return;
    }

    const endpoint = forceAssign
      ? "http://localhost:8080/authStaffProctoringRequestController/forcedAssign"
      : "http://localhost:8080/authStaffProctoringRequestController/unforcedAssign";

    const token = localStorage.getItem("token");

    try {
      const response = await axios.post(endpoint, selectedTAs, {
        params: {
          classProctoringId: selectedExamId,
          senderId: 9,
        },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (response.data === true) {
        alert(" TAs successfully assigned.");
        if (refreshAfterAssignment) {
          refreshAfterAssignment(); 
        }
      } else {
        alert(" Assignment failed.");
      }
      

      onClose();
    } catch (error) {
      console.error("Assignment error:", error);
      alert("An error occurred during assignment.");
    }
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

        <div className="force-assign-checkbox" style={{ marginTop: "1rem" }}>
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
          <button className="auto-button cancel" onClick={onClose}>Cancel</button>
          <button className="auto-button confirm" onClick={handleConfirm}>Confirm</button>
        </div>
      </div>
    </div>
  );
};

export default AutomaticAssignmentModal;
