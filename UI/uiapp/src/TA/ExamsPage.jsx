import React, { useState } from "react";
import "./ExamsPage.css";
import Navbar from "./Navbar";
import TaskItem from "../TaskItem";
import GenericModal from "../GenericModal";

// Utility function that creates a TaskItem component
const createTaskItem = (id, name, date, timeInterval, classroom, onClickHandler) => {
  const task = { id, name, date, timeInterval, classroom };
  return <TaskItem key={id} task={task} onClick={onClickHandler} />;
};

const ExamsPage = () => {
  const [selectedTask, setSelectedTask] = useState(null);

  const handleTaskClick = (task) => {
    setSelectedTask(task);
  };

  const closeModal = () => {
    setSelectedTask(null);
  };

  return (
    <div className="exams-container">
      <Navbar />

      <div className="exams-content">
        <div className="left-section">
          <div className="card">
            <h3>Choose the task you wish to get</h3>
            <div className="task-row">
              {createTaskItem(1, "Quiz Proctor", "15/03/2025", "10:30 - 11:30", "EE - 214", handleTaskClick)}
              {createTaskItem(2, "Quiz Proctor", "16/03/2025", "09:30 - 10:30", "EE - 312", handleTaskClick)}
              {createTaskItem(3, "Midterm Proctor", "21/03/2025", "19:00 - 21:00", "B - 103", handleTaskClick)}
              {createTaskItem(4, "Midterm Proctor", "21/03/2025", "19:00 - 21:00", "B - 104", handleTaskClick)}
            </div>
          </div>

          <div className="card">
            <h3>Choose one of your tasks</h3>
            <div className="task-row">
              {createTaskItem(5, "Quiz Proctor", "12/03/2025", "10:30 - 11:30", "EE - 214", handleTaskClick)}
              {createTaskItem(6, "Quiz Proctor", "16/04/2025", "09:30 - 10:30", "EE - 319", handleTaskClick)}
              {createTaskItem(7, "Midterm Proctor", "21/03/2025", "14:30 - 16:30", "B - 103", handleTaskClick)}
            </div>
          </div>
        </div>

        <div className="right-section">
          <div className="card">
            <h3>TAs Assigned for this Task</h3>
            <div className="assigned-tas placeholder-row">[ Assigned TAs will load here ]</div>
            <div className="details-section">
              <label htmlFor="details">Details</label>
              <textarea id="details" placeholder="Enter details..." />
              <button className="swap-button">Request Swap</button>
            </div>
          </div>
        </div>
      </div>

      {/* Modal to show task info on click */}
      <GenericModal open={!!selectedTask} onClose={closeModal} title="Task Info">
        {selectedTask && (
          <div>
            <p><strong>{selectedTask.name}</strong></p>
            <p>{selectedTask.date}</p>
            <p>{selectedTask.timeInterval}</p>
            <p>{selectedTask.classroom}</p>
          </div>
        )}
      </GenericModal>
    </div>
  );
};

export default ExamsPage;
