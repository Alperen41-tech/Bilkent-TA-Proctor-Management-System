import React from "react";
import "./PaidProctoringItem.css";

const PaidProctoringItem = ({ task, isSelected, isEnrolled, onClick }) => {
  const handleClick = () => {
    onClick?.(task.id);
  };

  return (
    <div
      className={`proctoring-card ${isSelected ? "selected" : ""}`}
      onClick={handleClick}
    >
      <div className="left-block">
        <div className="date">
          <div>{task.date}</div>
          <div className="weekday">{task.weekday}</div>
        </div>
        <div className="time">{task.timeStart} - {task.timeEnd}</div>
        <div className="title">{task.title}</div>
        <div className="duration">{task.duration} hours</div>
        <div>{`${task.currentTAs} / ${task.totalTAs} TAs`}</div>
        <div className="course">{task.course}</div>
      </div>

      <div className="right-block">
        <div className="department-label">Department</div>
        <div className="department">{task.department}</div>
        <div>
          {isEnrolled ? (
            <span className="status enrolled" >Enrolled</span>
          ) : (
            <button className="status apply " >Apply Proctoring</button>
          )}
        </div>
      </div>
    </div>
  );
};

export default PaidProctoringItem;
