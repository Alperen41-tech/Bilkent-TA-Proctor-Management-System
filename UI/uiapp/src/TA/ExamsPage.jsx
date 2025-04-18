import React, { useEffect ,useState } from "react";
import "./ExamsPage.css";
import Navbar from "./Navbar";
import TaskItem from "../TaskItem";
import TAItem from "../TAItem";
import axios from "axios";

const createTaskItem = (id, course, name, date, timeInterval, classroom, onClickHandler, selectedTaskId) => {
  const task = { id, course, name, date, timeInterval, classroom };
  const isSelected = selectedTaskId === id;
  console.log("Task ID:", id); 
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
  const [tasProctorings, setTasProctorings] = useState([]);//First declaration of tasProctorings as an empty array
  const [allDepartmantExams, setAllDepartmantExams] = useState(null);//Declaration of allDepartmantExams as an empty array
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
        const response = await axios.get("http://localhost:8080/classProctoringTARelation/getTAsClassProctorings?id=3"); // Adjust the URL as needed
        setTasProctorings(response.data);
        console.log(tasProctorings); // Log the fetched tasks to the console
        //console.log("TasProctorings:", response.data); // Log the fetched tasks to the console
        // You can set the tasks to state if needed
      } catch (error) {
        console.error("Error fetching tasks:", error);
      }
    };
    /*const fetchAllDepartmantExams = async () => {
      try {
        const response = await axios.get("http://localhost:8080/"); // Adjust the URL as needed
        allDepartmantExams = response.data;
        console.log(allDepartmantExams); // Log the fetched tasks to the console
        // You can set the tasks to state if needed
      } catch (error) {
        console.error("Error fetching tasks:", error);
      }
    };

    fetchAllDepartmantExams();*/
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
              
            </div>
          </div>

          <div className="card">
            <h3>Choose one of your tasks</h3>
            <div className="task-row">
            {tasProctorings.map((proctoring, index) => (createTaskItem(proctoring.classProctoringDTO.id, proctoring.classProctoringDTO.courseName, proctoring.classProctoringDTO.proctoringName, proctoring.classProctoringDTO.startDate, proctoring.classProctoringDTO.endDate, proctoring.classProctoringDTO.classrooms, handleTaskClick1, lastTask1?.id)))}
              
              
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
