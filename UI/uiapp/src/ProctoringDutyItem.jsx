import React from "react";
import './ProctoringDutyItem.css';

const ProctoringDutyItem = ({ duty, isSelected, onSelect, onLockedStatusChange }) => {
  const isLocked = !duty.isOpenToSwap;
  console.log("Duty item props:", duty.isOpenToSwap);

  return (
    <div
      className={`proctoring-duty-item-duty-item ${isLocked ? 'proctoring-duty-item-locked' : ''} ${isSelected ? 'proctoring-duty-item-selected' : ''}`}
      onClick={() => onSelect(duty.classProctoringDTO.id)}
    >
      <span className="proctoring-duty-item-duty-field">{duty.classProctoringDTO.courseCode}</span>
      <span className="proctoring-duty-item-duty-field">{duty.classProctoringDTO.proctoringName}</span>
      <span className="proctoring-duty-item-duty-field">{duty.classProctoringDTO.startDate ? duty.classProctoringDTO.startDate.split("T")[0]: null}</span>
      <span className="proctoring-duty-item-duty-field">{duty.classProctoringDTO.startDate && duty.classProctoringDTO.endDate ? duty.classProctoringDTO.startDate.split("T")[1].substring(0,5) + "-"+ duty.classProctoringDTO.endDate.split("T")[1].substring(0,5) : null}</span>
      <span className="proctoring-duty-item-duty-field">{duty.classProctoringDTO.classrooms}</span>
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
