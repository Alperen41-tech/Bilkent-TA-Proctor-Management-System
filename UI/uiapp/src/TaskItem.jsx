import React from "react";
import "./TaskItem.css";

/**
 * TaskItem Component
 * Renders a single proctoring or workload task with details such as course, date, and time.
 * Allows conditional click handling based on `isAboutSwap` and `swapRequestable` flags.
 */

const TaskItem = ({ task, onClick, isSelected, swapRequestable, isAboutSwap }) => {
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
      style={{ background: isAboutSwap ? (swapRequestable ? null : "#f8d7da") : null}}>
      <div className="property"><strong>Course:</strong> {task.course}</div>
      <div className="property"><strong>Task:</strong> {task.name}</div>
      <div className="property"><strong>Date:</strong> {task.date ? task.date.split("T")[0]: null}</div>
      <div className="property"><strong>Time:</strong> {task.timeInterval && task.date ? task.date.split("T")[1].substring(0,5)+"-"+task.timeInterval.split("T")[1].substring(0,5): null}</div>
      <div className="property"><strong>Classrooms:</strong> {task.classroom}</div>
    </div>
  );
};

export default TaskItem;
