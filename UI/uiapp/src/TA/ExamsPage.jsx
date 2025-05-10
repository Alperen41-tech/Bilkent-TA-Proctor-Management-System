import React, { useEffect ,useState, useRef, use } from "react";
import "./ExamsPage.css";
import Navbar from "./Navbar";
import TaskItem from "../TaskItem";
import TAItem from "../TAItem";
import axios, { all } from "axios";

const ExamsPage = () => {
  const [lastSelectedTask, setLastSelectedTask] = useState({});
  const [selectedTA, setSelectedTA] = useState({}); 
  const [tasProctorings, setTasProctorings] = useState([]);
  const [avaliableTAs, setAvailableTAs] = useState([]);
  const swapRequestRef = useRef();

  const createTaskItem = (id, course, name, date, timeInterval, classroom, swapRequestable, isAboutSwap, onClickHandler, selectedTaskId) => {
    const task = { id, course, name, date, timeInterval, classroom };
    const isSelected = selectedTaskId === id;
    console.log("Task ID:", id); 
    return <TaskItem key={id} task={task} onClick={onClickHandler} isSelected={isSelected} swapRequestable={swapRequestable} isAboutSwap={isAboutSwap}/>;
  };
  
  const createTAItem = (ta, onClickHandler) => {
    const isSelected = selectedTA === ta;
  
    return (
      <TAItem
        key={ta.bilkentId}
        ta={ta}
        onClick={onClickHandler}
        isSelected={isSelected}
      />
    );
  };

  const handleTAClick = (ta) => {
    setSelectedTA(ta);
  };

  const handleRequestSwap = async () => {
    if (lastSelectedTask && selectedTA) {
      const token = localStorage.getItem("token");
      const response = await axios.post("http://localhost:8080/swapRequest/createSwapRequest", {
          receiverId: selectedTA.userId,
          description: (swapRequestRef.current.value ? swapRequestRef.current.value : "-"),
          classProctoringId: lastSelectedTask.id,
        }, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        }
      );
      if (response.data) {
        alert("Swap request sent successfully!");
        console.log("Swap request sent successfully!");
        setAvailableTAs([]);
        fetchAvailableTAs(); // Refresh the available TAs after sending the request
        fetchTasProctorings(); // Refresh the tasks after sending the request
      }
      else {
        alert("Failed to send swap request. Please try again.");
        console.log("Failed to send swap request. Please try again.");
      }
    } else {
      alert("Please select a task and a TA to request a swap.");
      console.log("Please select a task and a TA to request a swap.");
    }
  };

  const handleTaskClick1 = (task) => {
    setLastSelectedTask(task);
  };

  useEffect(() => {
    fetchAvailableTAs(); 
  }, [lastSelectedTask]);

  useEffect(() => {

  }, [selectedTA]);

  
  const fetchTasProctorings = async () => {
      try {
        const token = localStorage.getItem("token");
        const response = await axios.get("http://localhost:8080/classProctoringTARelation/getTAsClassProctoringsByDepartment",{
          headers: {
            Authorization: `Bearer ${token}`
          }});
        const sortedTasProctorings = response.data.sort((a, b) => new Date(b.classProctoringDTO.startDate) - new Date(a.classProctoringDTO.startDate));
        setTasProctorings(sortedTasProctorings);
        console.log(tasProctorings);
      } catch (error) {
        console.error("Error fetching tasks:", error);
        if (error.response.data.message) {
          alert(error.response.data.message);
        }
        else{
          alert("An error occurred. Please try again.");
        }
      }
  };
  const fetchAvailableTAs = async () => {
    try {
      if (!lastSelectedTask.id) {
        return; // Avoid calling backend with invalid ID
      }
      const token = localStorage.getItem("token");
      const response = await axios.get(`http://localhost:8080/swapRequest/getAvailableTAProfilesForClassProctoring?classProctoringId=${lastSelectedTask.id}`, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      setAvailableTAs(response.data);
      console.log(avaliableTAs);
    } catch (error) {
      console.error("Error fetching available TAs:", error);
      if (error.response.data.message) {
        alert(error.response.data.message);
      }
      else{
        alert("An error occurred. Please try again.");
      }
    }
  };

  useEffect(() => {
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
              {tasProctorings.map((proctoring, index) => (createTaskItem(proctoring.classProctoringDTO.id, proctoring.classProctoringDTO.courseName, proctoring.classProctoringDTO.proctoringName, proctoring.classProctoringDTO.startDate, proctoring.classProctoringDTO.endDate, proctoring.classProctoringDTO.classrooms, proctoring.swapRequestable, true, handleTaskClick1, lastSelectedTask?.id)))}
            </div>
            <div className="details-section">
              <label htmlFor="details">Details</label>
              <textarea ref={swapRequestRef} id="details" placeholder="Enter details..." />
              <button className="swap-button" onClick={()=>{
                if(!lastSelectedTask.id || !selectedTA.userId){
                  alert("Please select a task and a TA to request a swap.");
                  return;
                }
                handleRequestSwap();}}>Request Swap</button>
            </div>
          </div>
        </div>

        <div className="right-section">
          <div className="card">
            <h3>TAs Avaliable for this Task</h3>
            <div className="assigned-tas">
              {avaliableTAs.map((ta, index) => (
                  createTAItem(ta, handleTAClick)
                )
              )}
            </div>

            
          </div>
        </div>
      </div>


    </div>
  );
};

export default ExamsPage;
