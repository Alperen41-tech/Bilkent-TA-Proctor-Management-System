import React from "react";
import './ProctoringDutyItem.css';

const ProctoringDutyItem = ({ duty, isSelected, onSelect, onLockedStatusChange }) => {
  const {
    title,
    date,
    time,
    location,
    status, // "locked" or "open"
  } = duty;

  const isLocked = status === "proctoring-duty-item-locked";

  return (
    <div
      className={`proctoring-duty-item-duty-item ${isLocked ? 'proctoring-duty-item-locked' : ''} ${isSelected ? 'proctoring-duty-item-selected' : ''}`}
      onClick={() => onSelect(duty.id)}
    >
      <span className="proctoring-duty-item-duty-field">{title}</span>
      <span className="proctoring-duty-item-duty-field">{date}</span>
      <span className="proctoring-duty-item-duty-field">{time}</span>
      <span className="proctoring-duty-item-duty-field">{location}</span>
      <div className="proctoring-duty-item-duty-field proctoring-duty-item-status-container">
        <input
          type="checkbox"
          onClick={onLockedStatusChange}
          readOnly
        />
        <span className="proctoring-duty-item-status-text">{isLocked ? "Swap Locked" : "Open for Swap"}</span>
      </div>
    </div>
  );
};

export default ProctoringDutyItem;
