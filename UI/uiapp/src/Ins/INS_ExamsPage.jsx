import React, { useState, useEffect } from "react";
import NavbarINS from "./NavbarINS";
import "./NavbarINS.css";
import "./INS_ExamsPage.css";
import TAItem from "../TAItem";
import TaskItem from "../TaskItem";
import axios from "axios";
import ManualAssignmentModal from "../ManualAssignmentModal"; // adjust path if needed


const INS_ExamsPage = () => {
  const [selectedTask, setSelectedTask] = useState({
    classProctoringTARelationDTO: {
      classProctoringDTO: {
        id: null,
        courseName: "",
        proctoringName: "",
        startDate: "",
        endDate: "",
        classrooms: "",
      },
    },
    taProfileDTOList: [],
  });

  const [showManualModal, setShowManualModal] = useState(false);

  const [selectedTA, setSelectedTA] = useState(null);
  const [proctoringTasks, setProctoringTasks] = useState([]);
  const [availableTAs, setAvailableTAs] = useState([]);

  const [searchText, setSearchText] = useState("");
  const [sortName, setSortName] = useState("");
  const [sortWorkload, setSortWorkload] = useState("");
  const [taCount, setTaCount] = useState(2);

  const fetchProctoringTasks = async () => {
    try {
      const response = await axios.get("http://localhost:8080/classProctoringTARelation/getClassProctoringOfInstructor?instructorId=4");
      if (response.data) {
        setProctoringTasks(response.data);
      }
    } catch (error) {
      console.error("Error fetching proctoring tasks:", error);
    }
  };

  const handleDiscardTA = async () => {
    if (!selectedTA) return alert("No TA selected to discard.");
    try {
      const response = await axios.delete(`http://localhost:8080/classProctoringTARelation/removeTAFromClassProctoring?classProctoringId=${selectedTask.classProctoringTARelationDTO.classProctoringDTO.id}&taId=${selectedTA.userId}&removerId=4`);
      if (response.data) {
        alert("TA discarded successfully.");
        setSelectedTA(null);
        fetchProctoringTasks();
      }
    } catch (error) {
      console.error("Failed to discard TA:", error);
    }
  };

  const fetchAvailableTAs = async () => {
    try {
      const proctoringId = selectedTask?.classProctoringTARelationDTO?.classProctoringDTO?.id;
      if (!proctoringId) return;

      const response = await axios.get("http://localhost:8080/ta/getAvailableTAsByDepartmentExceptProctoring", {
        params: {
          departmentCode: "CS",
          proctoringId,
          userId: 9,
        },
      });

      // Filter out already assigned TAs
      const assignedEmails = new Set(selectedTask.taProfileDTOList.map(ta => ta.email));
      const filtered = (response.data || []).filter(ta => !assignedEmails.has(ta.email));
      setAvailableTAs(filtered);
    } catch (error) {
      console.error("Error fetching available TAs:", error);
    }
  };

  const handleTaskClick = (task) => {
    setSelectedTask(task);
    setSelectedTA(null);
  };

  const handleTAClick = (ta) => {
    setSelectedTA(ta);
  };

  const createTaskItem = (id, course, name, date, timeInterval, classroom, onClickHandler) => {
    const task = { id, course, name, date, timeInterval, classroom };
    const isSelected = selectedTask.classProctoringTARelationDTO.classProctoringDTO.id === id;
    return <TaskItem key={id} task={task} onClick={onClickHandler} isSelected={isSelected} />;
  };

  const createTAItem = (ta, onClickHandler) => {
    const isSelected = selectedTA?.email === ta.email;
    return (
      <TAItem
        key={ta.bilkentId || ta.email}
        ta={ta}
        onClick={onClickHandler}
        isSelected={isSelected}
        inInstructor={true}
      />
    );
  };

  useEffect(() => {
    fetchProctoringTasks();
    fetchAvailableTAs();
  }, [selectedTask]);

  const handleManualAssign = () => {
    if (!selectedTA || !selectedTask?.classProctoringTARelationDTO?.classProctoringDTO?.id) {
      alert("Please select both a TA and a task.");
      return;
    }

    setShowManualModal(true);
  };

  const confirmManualAssign = async () => {
    const classProctoringId = selectedTask.classProctoringTARelationDTO.classProctoringDTO.id;
    const taId = selectedTA.userId;

    try {
      const response = await axios.post("http://localhost:8080/authStaffProctoringRequestController/forceAuthStaffProctoringRequest", null, {
        params: {
          classProctoringId,
          taId,
          senderId: 4 // hardcoded instructor ID
        },
      });

      if (response.data === true) {
        alert("TA manually assigned.");
        setSelectedTA(null);
        fetchProctoringTasks();
      } else {
        alert("Assignment failed.");
      }
    } catch (error) {
      console.error("Manual assignment error:", error);
      alert("Error occurred.");
    } finally {
      setShowManualModal(false);
    }
  };


  return (
    <div className="ins-exam-exams-page">
      <NavbarINS />
      <div className="ins-exam-grid-container">
        {/* Instructor's Proctor Assignments */}
        <div className="ins-exam-card ins-exam-assignments">
          <h3>Your Assignments with Proctors</h3>
          <div className="task-row">
            {proctoringTasks.map((task) =>
              createTaskItem(
                task.classProctoringTARelationDTO.classProctoringDTO.id,
                task.classProctoringTARelationDTO.classProctoringDTO.courseName,
                task.classProctoringTARelationDTO.classProctoringDTO.proctoringName,
                task.classProctoringTARelationDTO.classProctoringDTO.startDate,
                task.classProctoringTARelationDTO.classProctoringDTO.endDate,
                task.classProctoringTARelationDTO.classProctoringDTO.classrooms,
                () => handleTaskClick(task)
              )
            )}
          </div>
        </div>

        {/* Assigned TAs */}
        <div className="ins-exam-card ins-exam-assigned-tas">
          <h3>TAs Assigned for this Task</h3>
          <div className="ins-exams-ta-list-header">
            <span>Name</span><span>Email</span><span>Department</span><span>Bilkent ID</span><span>Workload</span>
          </div>
          <div className="ins-exam-ta-list-items">
            {selectedTask.taProfileDTOList.map((ta) =>
              createTAItem(ta, () => handleTAClick(ta))
            )}
          </div>
          <button className="ins-exam-assign-ta-button" onClick={handleDiscardTA}>
            Discard TA
          </button>
        </div>

        {/* New Task */}
        <div className="ins-exam-card ins-exam-create-task">
          <h3>Create a New Task with Proctoring</h3>
          <label>Select Task Type</label>
          <select><option value="">Dropdown</option></select>
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
          <input
            type="number"
            className="ta-count-input"
            value={taCount}
            onChange={(e) => setTaCount(e.target.value)}
          />
        </div>

        {/* Available TAs */}
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

          </div>
          <div className="ins-exams-ta-list-header">
            <span>Name</span><span>Email</span><span>Department</span><span>Bilkent ID</span><span>Workload</span>
          </div>
          <div className="ins-exam-ta-list-items">
            {availableTAs.length > 0 ? (
              availableTAs.map((ta) => createTAItem(ta, () => handleTAClick(ta)))
            ) : (
              <div>No available TAs</div>
            )}
          </div>

          <div className="ins-exam-assign-actions">
            <button onClick={() => { /* optional auto assign */ }}>Automatic Assign</button>
            <button onClick={handleManualAssign}>Manual Assign</button>
          </div>

          <ManualAssignmentModal
            isOpen={showManualModal}
            onConfirm={confirmManualAssign}
            onCancel={() => setShowManualModal(false)}
          />

        </div>
      </div>
    </div>
  );
};

export default INS_ExamsPage;
