import React from "react";
import './ProctoringDutyItem.css';

const ProctoringDutyItem = ({ duty, isSelected, onSelect, onLockedStatusChange }) => {
  const isLocked = duty.isOpenToSwap;

  return (
    <div
      className={`proctoring-duty-item-duty-item ${isLocked ? 'proctoring-duty-item-locked' : ''} ${isSelected ? 'proctoring-duty-item-selected' : ''}`}
      onClick={() => onSelect(duty.id)}
    >
      <span className="proctoring-duty-item-duty-field">{duty.proctoringName}</span>
      <span className="proctoring-duty-item-duty-field">{duty.startDate}</span>
      <span className="proctoring-duty-item-duty-field">{duty.endDate}</span>
      <span className="proctoring-duty-item-duty-field">{duty.classrooms}</span>
      <div className="proctoring-duty-item-duty-field proctoring-duty-item-status-container">
        <input
          type="checkbox"
          onClick={onLockedStatusChange}
          checked={isLocked}
          readOnly
        />
        <span className="proctoring-duty-item-status-text">{isLocked ? "Swap Locked" : "Open for Swap"}</span>
      </div>
    </div>
  );
};

export default ProctoringDutyItem;
