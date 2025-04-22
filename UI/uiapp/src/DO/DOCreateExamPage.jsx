import React, { useState } from "react";
import NavbarDO from "./NavbarDO";
import "./DOCreateExamPage.css";
import AdminDatabaseItem from "../Admin/AdminDatabaseItem";
import TAItem from "../TAItem";


const DOCreateExamPage = () => {




  // Form fields for creating a new task
  const [taskType, setTaskType] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [taskTitle, setTaskTitle] = useState("");
  const [taCount, setTaCount] = useState(2);
  const [isAutoAssigning, setIsAutoAssigning] = useState(false);
  const [isUnpaidProctoring, setIsUnpaidProctoring] = useState(false);

      
  
  
  const [selectedTA, setSelectedTA] = useState(null);
  const [selectedExamKey, setSelectedExamKey] = useState(null);
  const ExamItems = [
    {
      type: 'exam',
      data: {
        id: 1,
        course: "CS 319",
        date: "2025-03-15",
        time: "10:00 AM",
        location: "EE-214"
      }
    },
    {
      type: 'exam',
      data: {
        id: 2,
        course: "CS 315",
        date: "2025-04-17",
        time: "10:30 AM",
        location: "EE-212"
      }
    },
    {
      type: 'exam',
      data: {
        id: 3,
        course: "CS 376",
        date: "2025-05-25",
        time: "11:00 AM",
        location: "BZ-04"
      }
    },
    {
      type: 'exam',
      data: {
        id: 4,
        course: "CS 202",
        date: "2025-06-05",
        time: "8:00 AM",
        location: "EE-214"
      }
    },
    
  ];


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



  const createLogsDatabaseItems = () => {
    return ExamItems.map((item) => {
      const key = `${item.data.course}-${item.data.date}`;
      const isSelected = selectedExamKey === key;
  
      return (
        <AdminDatabaseItem
          key={key}
          type={item.type}
          data={item.data}
          onDelete={(id) => console.log(`Deleted ${item.type} with ID: ${id}`)}
          onSelect={(data) => setSelectedExamKey(key)}
          isSelected={isSelected}
          inLog={true}
        />
      );
    });
  };

  const handleTAClick = (ta) => {
    const key = `${ta.firstName}-${ta.lastName}-${ta.email}`;
    setSelectedTA(key);
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
            {createLogsDatabaseItems()}

            </div>
          </div>

          {/* TAs Assigned for This Task */}
          <div className="assigned-tas box">
            <h3>TAs Assigned for This Task</h3>
            <div className="ta-assigned-list">
              {createTAItem("Ahmet", "Yılmaz", "ahmet.yilmaz@example.com", handleTAClick, selectedTA)}
              {createTAItem("Merve", "Kara", "merve.kara@example.com", handleTAClick, selectedTA)}
              {createTAItem("John", "Doe", "john.doe@example.com", handleTAClick, selectedTA)}
            </div>

            <button className="dismissTA-button" >
                Dismiss
            </button>
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
              {createTAItem("Cazi", "Yılmaz", "ahmet.yilmaz@example.com", handleTAClick, selectedTA)}
              {createTAItem("Cemil", "Kara", "merve.kara@example.com", handleTAClick, selectedTA)}
              {createTAItem("Jakir", "Doe", "john.doe@example.com", handleTAClick, selectedTA)}
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
