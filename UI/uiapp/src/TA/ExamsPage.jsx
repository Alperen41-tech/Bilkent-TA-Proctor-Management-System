import React, { useEffect ,useState } from "react";
import "./ExamsPage.css";
import Navbar from "./Navbar";
import TaskItem from "../TaskItem";
import TAItem from "../TAItem";
import axios from "axios";

const createTaskItem = (id, course, name, date, timeInterval, classroom, onClickHandler, selectedTaskId) => {
  const task = { id, course, name, date, timeInterval, classroom };
  const isSelected = selectedTaskId === id;
  return <TaskItem key={id} task={task} onClick={onClickHandler} isSelected={isSelected} />;
};

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

const ExamsPage = () => {
  const [selectedTask, setSelectedTask] = useState(null);
  const [lastTask1, setLastTask1] = useState(null);
  const [lastTask2, setLastTask2] = useState(null);
  const [selectedTA, setSelectedTA] = useState(null); 
  const tasProctorings = [];//First declaration of tasProctorings as an empty array
  const allDepartmantExams = [];//Declaration of allDepartmantExams as an empty array
  const handleTaskClick1 = (task) => {
    setSelectedTask(task);
    setLastTask1(task);
  };

  const handleTaskClick2 = (task) => {
    setSelectedTask(task);
    setLastTask2(task);
  };

  const handleTAClick = (ta) => {
    const key = `${ta.firstName}-${ta.lastName}-${ta.email}`;
    setSelectedTA(key);
  };
  
  useEffect(() => {
    const fetchTasProctorings = async () => {
      try {
        const response = await axios.get("http://localhost:8080/classProctoring/getTAsClassProctorings?id=5"); // Adjust the URL as needed
        tasProctorings = response.data;
        console.log(tasProctorings); // Log the fetched tasks to the console
        // You can set the tasks to state if needed
      } catch (error) {
        console.error("Error fetching tasks:", error);
      }
    };
    const fetchAllDepartmantExams = async () => {
      try {
        const response = await axios.get("http://localhost:8080/"); // Adjust the URL as needed
        allDepartmantExams = response.data;
        console.log(allDepartmantExams); // Log the fetched tasks to the console
        // You can set the tasks to state if needed
      } catch (error) {
        console.error("Error fetching tasks:", error);
      }
    };

    fetchAllDepartmantExams();
    fetchTasProctorings();
  }, []); // Empty dependency array to run once on component mount


  return (
    <div className="exams-container">
      <Navbar />

      <div className="exams-content">
        <div className="left-section">
          <div className="card">
            <h3>Choose the task you wish to get</h3>
            <div className="task-row">
              {createTaskItem(1, "CS315", "Quiz Proctor", "15/03/2025", "10:30 - 11:30", "EE - 214", handleTaskClick1, lastTask1?.id)}
              {createTaskItem(2, "CS102", "Quiz Proctor", "16/03/2025", "09:30 - 10:30", "EE - 312", handleTaskClick1, lastTask1?.id)}
              {createTaskItem(3, "CS555", "Midterm Proctor", "21/03/2025", "19:00 - 21:00", "B - 103", handleTaskClick1, lastTask1?.id)}
              {createTaskItem(4, "CS2004", "Midterm Proctor", "21/03/2025", "19:00 - 21:00", "B - 104", handleTaskClick1, lastTask1?.id)}
            </div>
          </div>

          <div className="card">
            <h3>Choose one of your tasks</h3>
            <div className="task-row">
              {tasProctorings.map((task) => (createTaskItem(task.id, task.course, task.name, task.date, task.timeInterval, task.classroom, handleTaskClick1, lastTask1?.id)))}
              
              {createTaskItem(5, "CS315", "Quiz Proctor", "12/03/2025", "10:30 - 11:30", "EE - 214", handleTaskClick2, lastTask2?.id)}
              {createTaskItem(6, "CS102", "Quiz Proctor", "16/04/2025", "09:30 - 10:30", "EE - 319", handleTaskClick2, lastTask2?.id)}
              {createTaskItem(7, "CS102", "Midterm Proctor", "21/03/2025", "14:30 - 16:30", "B - 103", handleTaskClick2, lastTask2?.id)}
            </div>
          </div>
        </div>

        <div className="right-section">
          <div className="card">
            <h3>TAs Assigned for this Task</h3>
            <div className="assigned-tas">
              {createTAItem("Ahmet", "Yılmaz","dasdad@email", handleTAClick, selectedTA)}
              {createTAItem("Merve", "Kara","dasdad@email", handleTAClick, selectedTA)}
              {createTAItem("John", "Doe", "dasdad@email", handleTAClick, selectedTA)}
            </div>

            <div className="details-section">
              <label htmlFor="details">Details</label>
              <textarea id="details" placeholder="Enter details..." />
              <button className="swap-button">Request Swap</button>
            </div>
          </div>
        </div>
      </div>


    </div>
  );
};

export default ExamsPage;
