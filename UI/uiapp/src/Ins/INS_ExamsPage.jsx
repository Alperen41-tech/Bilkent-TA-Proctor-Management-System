import React, { use, useState, useEffect } from "react";
import NavbarINS from "./NavbarINS";
import "./NavbarINS.css";
import "./INS_ExamsPage.css";
import TAItem from "../TAItem";
import TaskItem from "../TaskItem";
import axios from "axios";

const INS_ExamsPage = () => {
  const [selectedTask, setSelectedTask] = useState(null);
  const [selectedTA, setSelectedTA] = useState(null);
  const [proctoringTasks, setProctoringTasks] = useState([]);

  const [searchText, setSearchText] = useState("");
  const [sortName, setSortName] = useState("");
  const [sortWorkload, setSortWorkload] = useState("");
  const [taCount, setTaCount] = useState(2);
  const [autoAssign, setAutoAssign] = useState(false);

  const fetchProctoringTasks = async () => {
    try{
      const response = await axios.get("http://localhost:8080/classProctoringTARelation/getClassProctoringOfCreator?creatorId=1&creatorId=9");
      if (response.data) {
        setProctoringTasks(response.data);
        console.log("Proctoring tasks fetched successfully:", response.data);
      }
      else {
        console.error("No data found for proctoring tasks.");
      }
    }catch (error) {
      console.error("Error fetching proctoring tasks:", error);
    }
  };

  const handleSearch = () => {
    console.log("Searching for:", searchText);
    console.log(proctoringTasks);
  };

  const handleSort = () => {
    console.log("Sort Name:", sortName, "Workload:", sortWorkload);
  };


  const createTaskItem = (id, course, name, date, timeInterval, classroom, onClickHandler, selectedTaskId) => {
    const task = { id, course, name, date, timeInterval, classroom };
    const isSelected = selectedTaskId === id;
    return <TaskItem key={id} task={task} onClick={onClickHandler} isSelected={isSelected} />;
  };

  const handleTaskClick = (task) => {
    setSelectedTask(task);
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

  useEffect(() => {
    fetchProctoringTasks();
  }, []);

  return (
    <div className="ins-exam-exams-page">
      <NavbarINS />

      <div className="ins-exam-grid-container">
        {/* Instructor's Proctor Assignments */}
        <div className="ins-exam-card ins-exam-assignments">
          <h3>Your Assignments with Proctors</h3>
          <div className="task-row">

            </div>
        </div>

        {/* TAs Assigned to Selected Task */}
        <div className="ins-exam-card ins-exam-assigned-tas">
          <h3>TAs Assigned for this Task</h3>
          <div className="ins-exam-assigned-tas">

          </div>

        </div>

        {/* Create New Task */}
        <div className="ins-exam-card ins-exam-create-task">
          <h3>Create a New Task with Proctoring</h3>

          <label>Select Task Type</label>
          <select>
            <option value="">Dropdown</option>
          </select>

          <label>Time Interval</label>

          <input type="date" placeholder="Select date" />

          <label>Start</label>
          <div className="ins-exam-interval-inputs">
            <input type="time" placeholder="start" />
            <label>End</label>
            <input type="time" placeholder="end" />
          </div>


          <label>Proctoring Details</label>
          <input type="text" placeholder="Task Title" />
          <input type="text" placeholder="Enter classroom" />
          

          <label>TA count</label>
          <input type="number" className="ta-count-input" value={taCount} onChange={(e) => setTaCount(e.target.value)} />

          <div className="ins-exam-assignment-buttons">
            <button
              className={autoAssign ? "active" : ""}
              onClick={() => setAutoAssign(!autoAssign)}
            >
              {autoAssign ? "✔ Automatic Assigning" : "Automatic Assigning"}
            </button>

          </div>
        </div>

        {/* TA List + Sort/Search */}
        <div className="ins-exam-card ins-exam-ta-list">
          <h3>Available TAs</h3>

          <div className="ins-exam-filters">
            <input
              type="text"
              placeholder="🔍 Search by name"
              value={searchText}
              onChange={(e) => setSearchText(e.target.value)}
            />
            <select value={sortName} onChange={(e) => setSortName(e.target.value)}>
              <option value="">Sort by Name</option>
              <option value="asc">A → Z</option>
              <option value="desc">Z → A</option>
            </select>
            <select value={sortWorkload} onChange={(e) => setSortWorkload(e.target.value)}>
              <option value="">Sort by Workload</option>
              <option value="low">Low to High</option>
              <option value="high">High to Low</option>
            </select>
            <button onClick={handleSearch}>Apply</button>
          </div>

          <div className="ins-exam-assigned-tas">

          </div>
        </div>
      </div>
    </div>
  );
};

export default INS_ExamsPage;
