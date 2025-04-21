import React from "react";
import "./TaskItemSearch.css";

const TaskItemSearch = ({ task, onClick, isSelected }) => {
  const handleClick = () => {
    if (onClick) {
      onClick(task);
    }
  };

  return (
    <div className={`taskv2-item-row ${isSelected ? "selected" : ""}`} onClick={handleClick}>
      <div className="taskv2-cell"><strong>{task.course}</strong></div>
      <div className="taskv2-cell">{task.name}</div>
      <div className="taskv2-cell">{task.date}</div>
      <div className="taskv2-cell">{task.timeInterval}</div>
      <div className="taskv2-cell">{task.classroom}</div>
    </div>
  );
};

export default TaskItemSearch;
