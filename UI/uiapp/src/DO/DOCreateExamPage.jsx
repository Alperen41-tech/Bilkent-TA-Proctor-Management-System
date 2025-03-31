import React, { useState } from "react";
import NavbarDO from "./NavbarDO";
import "./DOCreateExamPage.css";

const DOCreateExamPage = () => {
  // Dummy exam data
  const [exams, setExams] = useState([
    { title: "Midterm Proctor", date: "29/01/2025", time: "15:00 - 18:00", room: "B - 103" },
    { title: "Midterm Proctor", date: "29/01/2025", time: "15:00 - 18:00", room: "B - 103" },
    { title: "Midterm Proctor", date: "29/01/2025", time: "15:00 - 18:00", room: "B - 103" },
  ]);

  // Assigned TAs
  const [assignedTAs, setAssignedTAs] = useState([
    { name: "Ali 25", dept: "CS" },
    { name: "Ali 17", dept: "IE" },
  ]);

  // Unassigned TAs (for bottom-right box)
  const [unassignedTAs, setUnassignedTAs] = useState([
    { name: "Ali 1" },
    { name: "Ali 2" },
    { name: "Ali 3" },
    { name: "Ali 4" },
    { name: "Ali 5" },
  ]);

  // Form fields for creating a new task
  const [taskType, setTaskType] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [taskTitle, setTaskTitle] = useState("");
  const [taCount, setTaCount] = useState(2);
  const [isAutoAssigning, setIsAutoAssigning] = useState(false);
  const [isUnpaidProctoring, setIsUnpaidProctoring] = useState(false);

  // Handle "dismiss" from assigned TAs
  const handleDismissTA = (index) => {
    const newAssigned = [...assignedTAs];
    newAssigned.splice(index, 1);
    setAssignedTAs(newAssigned);
  };

  // Handle creation of a new task
  const handleCreateTask = () => {
    // Placeholder for logic to create a new task
    alert(`Task Created!\nType: ${taskType}\nTitle: ${taskTitle}\nTime: ${startTime} - ${endTime}\nTA Count: ${taCount}\nAuto? ${isAutoAssigning}\nUnpaid? ${isUnpaidProctoring}`);
  };

  // Handle automatic assignment
  const handleAutomaticAssign = () => {
    // Placeholder for logic
    alert("Automatically assigning TAs...");
  };

  // Handle manual assignment
  const handleManualAssign = () => {
    // Placeholder for logic
    alert("Manually assigning TAs...");
  };

  return (
    <div className="do-create-exam-container">
      <NavbarDO />

      <div className="do-create-exam-content">
        {/* Top row: Your Exams (left) and TAs Assigned (right) */}
        <div className="top-row">
          {/* Your Exams */}
          <div className="your-exams box">
            <h3>Your Exams</h3>
            <div className="exam-list">
              {exams.map((exam, idx) => (
                <div className="exam-item" key={idx}>
                  <span>{exam.title}</span>
                  <span>{exam.date}</span>
                  <span>{exam.time}</span>
                  <span>{exam.room}</span>
                </div>
              ))}
            </div>
          </div>

          {/* TAs Assigned for This Task */}
          <div className="assigned-tas box">
            <h3>TAs Assigned for This Task</h3>
            <div className="ta-assigned-list">
              {assignedTAs.map((ta, idx) => (
                <div className="ta-row" key={idx}>
                  <span>{ta.name}</span>
                  <span>{ta.dept}</span>
                  <button className="dismiss-btn" onClick={() => handleDismissTA(idx)}>
                    dismiss
                  </button>
                </div>
              ))}
            </div>
          </div>
        </div>

        {/* Bottom row: Create New Task Type (left) and Choose TAs (right) */}
        <div className="bottom-row">
          {/* Create New Task Type */}
          <div className="create-task box">
            <div className="type-form">
              <label>Create Exam</label>
              <input type="text" placeholder="Enter Exam Name" />

              <label>Date</label>
              <input type="date" />

              <input type="text" placeholder="Enter Classroom" />

              <label>Department</label>
              <select>
                <option value="">Select Department</option>
                <option value="CS">CS</option>
                <option value="IE">IE</option>
                <option value="Other">Other</option>
              </select>

              <label>Course Name</label>
              <input type="text" placeholder="e.g., CS 202" />

              <label>Start Time</label>
              <input type="time" />

              <label>End Time</label>
              <input type="time" />

              <label>TA Count</label>
              <input
                type="number"
                value={taCount}
                onChange={(e) => setTaCount(Number(e.target.value))}
              />

              <button className="create-type-button">Create</button>
            </div>

            




          </div>

          {/* Choose TAs */}
          <div className="choose-tas box">
            <h3>Choose TAs</h3>
            <div className="ta-list">
              {unassignedTAs.map((ta, idx) => (
                <div className="ta-item" key={idx}>
                  {ta.name}
                </div>
              ))}
            </div>
            <div className="assign-buttons">
              <button className="assign-btn" onClick={handleAutomaticAssign}>
                Automatically Assign
              </button>
              <button className="assign-btn" onClick={handleManualAssign}>
                Manually Assign
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DOCreateExamPage;
