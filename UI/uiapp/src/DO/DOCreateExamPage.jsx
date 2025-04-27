import React, { useState, useEffect } from "react";
import NavbarDO from "./NavbarDO";
import "./DOCreateExamPage.css";
import AdminDatabaseItem from "../Admin/AdminDatabaseItem";
import TAItem from "../TAItem";
import axios from "axios";


const DOCreateExamPage = () => {




  // Form fields for creating a new task
  const [taskType, setTaskType] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [taskTitle, setTaskTitle] = useState("");
  const [taCount, setTaCount] = useState(2);
  const [isAutoAssigning, setIsAutoAssigning] = useState(false);
  const [isUnpaidProctoring, setIsUnpaidProctoring] = useState(false);
  const facultyId = 1; // Hardcoded for now, you can pass dynamically later
  const creatorId = 9;
  const [allTAs, setAllTAs] = useState([]);
  const [tas, setTAs] = useState([]);
  const [createdExams, setCreatedExams] = useState([]);
  const [examItems, setExamItems] = useState([]); // Real fetched exams
  const [departments, setDepartments] = useState([]); // Departments from backend
  const [selectedExamItem, setSelectedExamItem] = useState(null);
  const [taDepartmentFilter, setTaDepartmentFilter] = useState(""); // For department dropdown







  const [selectedTA, setSelectedTA] = useState(null);
  const [selectedExamKey, setSelectedExamKey] = useState(null);



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
    return createdExams
      .filter((item) => item.classProctoringTARelationDTO && item.classProctoringTARelationDTO.classProctoringDTO)
      .map((item) => {
        const key = `${item.classProctoringTARelationDTO.classProctoringDTO.courseName}-${item.classProctoringTARelationDTO.classProctoringDTO.startDate}`;
        const isSelected = selectedExamKey === key;

        return (
          <AdminDatabaseItem
            key={key}
            type="exam"
            data={{
              //bura şimdilik böyle kalsın

              id: item.classProctoringTARelationDTO.classProctoringDTO.id,
              course: item.classProctoringTARelationDTO.classProctoringDTO.courseName,
              date: item.classProctoringTARelationDTO.classProctoringDTO.startDate,
              time: item.classProctoringTARelationDTO.classProctoringDTO.timeInterval,
              location: item.classProctoringTARelationDTO.classProctoringDTO.classrooms,
            }}
            onDelete={(id) => console.log(`Deleted exam with ID: ${id}`)}
            onSelect={() => {
              setSelectedExamKey(key);
              setSelectedExamItem(item);

              setTaDepartmentFilter(""); // Reset dropdown
              const proctoringId = item?.classProctoringTARelationDTO?.classProctoringDTO?.id;
              fetchTAs("", proctoringId); // Fetch TAs for this exam
            }}
            isSelected={isSelected}
            inLog={true}
          />
        );
      });
  };



  const fetchTAs = async (departmentCode, proctoringId) => {
    try {
      let response;

      if (!departmentCode || departmentCode === "") {
        // Fetch all available TAs in faculty
        response = await axios.get('http://localhost:8080/ta/getAvailableTAsByFacultyExceptProctoring', {
          params: { facultyId: facultyId, proctoringId: proctoringId },
        });
      } else {
        // Fetch available TAs in selected department
        response = await axios.get('http://localhost:8080/ta/getAvailableTAsByDepartmentExceptProctoring', {
          params: { departmentCode: departmentCode, proctoringId: proctoringId },
        });
      }

      console.log("Fetched Available TAs: ", response.data);
      setAllTAs(response.data || []);
      setTAs(response.data || []);
    } catch (error) {
      console.error('Error fetching available TAs:', error);
    }
  };

  const fetchCreatedExams = async () => {
    try {
      const { data } = await axios.get(
        "http://localhost:8080/classProctoringTARelation/getClassProctoringOfCreator",
        { params: { creatorId } }
      );
      setCreatedExams(data);
    } catch (error) {
      console.error("Failed to fetch created exams:", error);
    }
  };

  const fetchDepartments = async () => {
    try {
      const response = await axios.get('http://localhost:8080/department/getAllDepartmentsInFaculty', {
        params: { facultyId: facultyId },
      });
      console.log("Fetched Departments: ", response.data);
      setDepartments(response.data || []);
    } catch (error) {
      console.error('Error fetching departments:', error);
    }
  };

  useEffect(() => {
    fetchCreatedExams();
    fetchDepartments();
  }, []);



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
              {selectedExamItem?.taProfileDTOList?.length > 0 ? (
                selectedExamItem.taProfileDTOList.map((ta) => (
                  createTAItem(ta.name, ta.surname, ta.email, handleTAClick, selectedTA)
                ))
              ) : (
                <div>No TAs assigned</div>
              )}
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

            <div className="choose-header">
              <select
                value={taDepartmentFilter}
                onChange={(e) => {
                  const selectedDepartment = e.target.value;
                  setTaDepartmentFilter(selectedDepartment);

                  const proctoringId = selectedExamItem?.classProctoringTARelationDTO?.classProctoringDTO?.id;
                  fetchTAs(selectedDepartment, proctoringId);
                }}
              >
                <option value="">Select Department</option>
                {departments.length > 0 ? (
                  departments.map((department) => (
                    <option key={department.departmentCode} value={department.departmentCode}>
                      {department.departmentName}
                    </option>
                  ))
                ) : (
                  <option disabled>Loading departments...</option>
                )}
              </select>
            </div>

            <div className="choose-list">
              {tas.length > 0 ? (
                tas.map((ta) => (
                  createTAItem(ta.name, ta.surname, ta.email, handleTAClick, selectedTA)
                ))
              ) : (
                <div>No TAs available</div>
              )}
            </div>

            <div className="choose-actions">
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
