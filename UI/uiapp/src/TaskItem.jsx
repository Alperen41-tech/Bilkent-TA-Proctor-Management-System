import React from "react";
import "./TaskItem.css";

const TaskItem = ({ task, onClick, isSelected }) => {
  // For example, assume `task` has: { id, name, date, timeInterval, classroom }
  const handleClick = () => {
    if (onClick) {
      onClick(task);
    }
  };

  return (
    <div className={`task-item ${isSelected ? 'selected' : ''}`}
    onClick={handleClick}>
      <div className="property"><strong>Course:</strong> {task.course}</div>
      <div className="property"><strong>Task:</strong> {task.name}</div>
      <div className="property"><strong>Date:</strong> {task.date}</div>
      <div className="property"><strong>Time:</strong> {task.timeInterval}</div>
      <div className="property"><strong>Classrooms:</strong> {task.classroom}</div>
    </div>
  );
};

export default TaskItem;
