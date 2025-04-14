import React from "react";
import "./TaskItem.css";

const TaskItem = ({ task, onClick }) => {
  // For example, assume `task` has: { id, name, date, timeInterval, classroom }
  const handleClick = () => {
    if (onClick) {
      onClick(task);
    }
  };

  return (
    <div className="task-item" onClick={handleClick}>
      <p><strong>Task:</strong> {task.name}</p>
      <p><strong>Date:</strong> {task.date}</p>
      <p><strong>Time:</strong> {task.timeInterval}</p>
      <p><strong>Classroom:</strong> {task.classroom}</p>
    </div>
  );
};

export default TaskItem;
