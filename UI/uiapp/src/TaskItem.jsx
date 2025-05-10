import React from "react";
import "./TaskItem.css";

const TaskItem = ({ task, onClick, isSelected, swapRequestable, isAboutSwap }) => {
  // For example, assume `task` has: { id, name, date, timeInterval, classroom }
  const handleClick = () => {
    if (onClick) {
      onClick(task);
    }
  };

  return (
    <div className={`task-item ${isSelected ? 'selected' : ''}`}
    onClick={()=> {
      if(!isAboutSwap || swapRequestable){handleClick();}
    } }
      style={{ background: swapRequestable ? "#f3f4f6" : "#f8d7da"}}>
      <div className="property"><strong>Course:</strong> {task.course}</div>
      <div className="property"><strong>Task:</strong> {task.name}</div>
      <div className="property"><strong>Date:</strong> {task.date ? task.date.split("T")[0]: null}</div>
      <div className="property"><strong>Time:</strong> {task.timeInterval && task.date ? task.date.split("T")[1].substring(0,5)+"-"+task.timeInterval.split("T")[1].substring(0,5): null}</div>
      <div className="property"><strong>Classrooms:</strong> {task.classroom}</div>
    </div>
  );
};

export default TaskItem;
