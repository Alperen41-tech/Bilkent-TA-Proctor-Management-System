// src/PaidProctoringPage.jsx
import React, { useState } from "react";
import Navbar from "./Navbar";
import PaidProctoringItem from "./PaidProctoringItem";
import "./PaidProctoringPage.css";
import TAItem from "../TAItem";

const sampleTasks = [
  {
    id: 1,
    date: "March 23",
    weekday: "Fri",
    timeStart: "9:00AM",
    timeEnd: "10:30AM",
    title: "Paid Proctoring",
    duration: 1.5,
    currentTAs: 0,
    totalTAs: 7,
    department: "CS",
    course: "CS 202",
  },
  {
    id: 2,
    date: "March 15",
    weekday: "Thu",
    timeStart: "9:00AM",
    timeEnd: "10:30AM",
    title: "Paid Proctoring",
    duration: 1,
    currentTAs: 0,
    totalTAs: 4,
    department: "CS",
    course: "CS 315",
  },
  {
    id: 3,
    date: "March 23",
    weekday: "Fri",
    timeStart: "9:00AM",
    timeEnd: "10:30AM",
    title: "Paid Proctoring",
    duration: 1.5,
    currentTAs: 0,
    totalTAs: 2,
    department: "CS",
    course: "CS 224",
  },
];


const createTAItem = (firstName, lastName, email, onClickHandler, selectedTAKey) => {
  const ta = { firstName, lastName, email };
  const key = `${firstName}-${lastName}-${email}`;
  const isSelected = selectedTAKey === key;

  return (
    <TAItem
      key={key}
      ta={ta}
      onClick={onClickHandler}
      isSelected={isSelected}
    />
  );
};




const PaidProctoringPage = () => {
  const [selectedTA, setSelectedTA] = useState(null); 


  const [selectedTaskId, setSelectedTaskId] = useState(null);
  const selectedTask = sampleTasks.find((task) => task.id === selectedTaskId);

  const [enrolledTaskIds, setEnrolledTaskIds] = useState([]);

  const handleTaskClick = (taskId) => {
    setSelectedTaskId(taskId);
    if (!enrolledTaskIds.includes(taskId)) {
      setEnrolledTaskIds((prev) => [...prev, taskId]);
    }
    else{
      setEnrolledTaskIds((prev) => prev.filter(id => id !== taskId));
    }
  };

  const handleTAClick = (ta) => {
    setSelectedTA(null);
  };



  return (
    <div className="paid-proctoring-page">
      <Navbar />

      <div className="proctoring-content">
        {/* LEFT: List of proctoring tasks */}
        <div className="proctoring-left">
          <div className="card">
            <h3>Paid Proctoring List</h3>
            
            {/* Task List Placeholder */}
            <div className="task-list">
              {sampleTasks.map((task) => (
                <PaidProctoringItem
                  key={task.id}
                  task={task}
                  isSelected={selectedTaskId === task.id}
                  isEnrolled={enrolledTaskIds.includes(task.id)}
                  onClick={handleTaskClick}
                />
              ))}
            </div>
          </div>
        </div>

        {/* RIGHT: Selected task details */}
        <div className="proctoring-right">
          <div className="card">
            <h3>TAs Applied for this Task</h3>

            {/* Applied TA Placeholder */}
            <div className="assigned-tas">
              {createTAItem("Ahmet", "Yılmaz","dasdad@email", handleTAClick, selectedTA)}
              {createTAItem("Merve", "Kara","dasdad@email", handleTAClick, selectedTA)}
              {createTAItem("John", "Doe", "dasdad@email", handleTAClick, selectedTA)}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default PaidProctoringPage;
