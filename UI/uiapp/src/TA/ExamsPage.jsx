import React, { useEffect ,useState, useRef } from "react";
import "./ExamsPage.css";
import Navbar from "./Navbar";
import TaskItem from "../TaskItem";
import TAItem from "../TAItem";
import axios, { all } from "axios";

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
  const [lastSelectedTask1, setLastSelectedTask1] = useState(null);
  const [lastSelectedTask2, setLastSelectedTask2] = useState(null);
  const [selectedTA, setSelectedTA] = useState(null); 
  const [tasProctorings, setTasProctorings] = useState([]);
  const [avaliableTAs, setAvailableTAs] = useState([]);
  const handleTaskClick1 = (task) => {
    setLastSelectedTask1(task);
  };
  const swapRequestRef = useRef();


  const handleTAClick = (ta) => {
    const key = `${ta.firstName}-${ta.lastName}-${ta.email}`;
    setSelectedTA(key);
  };

  const handleRequestSwap = () => {
    if (lastSelectedTask1 && selectedTA) {
      

      console.log("Requesting swap for task ID:with TA:");
      // Add your swap request logic here
    } else {
      alert("Please select a task and a TA to request a swap.");
      console.log("Please select a task and a TA to request a swap.");
    }
  };

  
  const fetchTasProctorings = async () => {
      try {
        const response = await axios.get("http://localhost:8080/classProctoringTARelation/getTAsClassProctorings?id=1");
        setTasProctorings(response.data);
        console.log(tasProctorings);
      } catch (error) {
        console.error("Error fetching tasks:", error);
      }
  };
  const fetchAvailableTAs = async () => {
    try {
      const response = await axios.get("http://localhost:8080/");
      setAvailableTAs(response.data);
      console.log(avaliableTAs);
    } catch (error) {
      console.error("Error fetching available TAs:", error);
    }
  };

  useEffect(() => {
    fetchAvailableTAs();
    fetchTasProctorings(); 
  }, []);


  return (
    <div className="exams-container">
      <Navbar />

      <div className="exams-content">
        <div className="left-section">
          

          <div className="card">
            <h3>Choose one of your tasks</h3>
            <div className="task-row">
              {tasProctorings.map((proctoring, index) => (createTaskItem(proctoring.classProctoringDTO.id, proctoring.classProctoringDTO.courseName, proctoring.classProctoringDTO.proctoringName, proctoring.classProctoringDTO.startDate, proctoring.classProctoringDTO.endDate, proctoring.classProctoringDTO.classrooms, handleTaskClick1, lastSelectedTask1?.id)))}
            </div>
            <div className="details-section">
              <label htmlFor="details">Details</label>
              <textarea ref={swapRequestRef} id="details" placeholder="Enter details..." />
              <button className="swap-button" onClick={()=>handleRequestSwap()}>Request Swap</button>
            </div>
          </div>
        </div>

        <div className="right-section">
          <div className="card">
            <h3>TAs Avaliable for this Task</h3>
            <div className="assigned-tas">
              {avaliableTAs.filter((proctoring) => proctoring.classProctoringTARelationDTO.classProctoringDTO.id === lastSelectedTask2?.id).map((proctoring, index) => (
                proctoring.taProfileDTOList.map((ta) => (
                  createTAItem(ta.name, ta.surname, ta.email, handleTAClick, selectedTA)
                ))
              ))}
            </div>

            
          </div>
        </div>
      </div>


    </div>
  );
};

export default ExamsPage;
