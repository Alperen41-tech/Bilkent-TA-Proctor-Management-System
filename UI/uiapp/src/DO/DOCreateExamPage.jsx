import React, { useState, useEffect } from "react";
import NavbarDO from "./NavbarDO";
import "./DOCreateExamPage.css";
import AdminDatabaseItem from "../Admin/AdminDatabaseItem";
import TAItem from "../TAItem";
import axios from "axios";

const DOCreateExamPage = () => {
  const [taskType, setTaskType] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [taskTitle, setTaskTitle] = useState("");
  const [taCount, setTaCount] = useState(2);
  const [isAutoAssigning, setIsAutoAssigning] = useState(false);
  const [isUnpaidProctoring, setIsUnpaidProctoring] = useState(false);
  const facultyId = 1;
  const creatorId = 9;

  const [allTAs, setAllTAs] = useState([]);
  const [tas, setTAs] = useState([]);
  const [createdExams, setCreatedExams] = useState([]);
  const [departments, setDepartments] = useState([]);
  const [selectedExamItem, setSelectedExamItem] = useState(null);
  const [selectedExamKey, setSelectedExamKey] = useState(null);

  const [taDepartmentFilter, setTaDepartmentFilter] = useState("");
  const [selectedTA, setSelectedTA] = useState(null);
  const [selectedTAObj, setSelectedTAObj] = useState(null);

  const handleTAClick = (ta) => {
    const key = `${ta.name}-${ta.surname}-${ta.email}`;
    setSelectedTA(key);
    setSelectedTAObj(ta);
  };

  const createTAItem = (ta, onClickHandler, selectedTAKey) => {
    const key = `${ta.name}-${ta.surname}-${ta.email}`;
    const isSelected = selectedTAKey === key;

    return (
      <TAItem
        key={key}
        ta={ta}
        onClick={() => onClickHandler(ta)}
        isSelected={isSelected}
      />
    );
  };

  const dismissTA = async () => {
    console.log("▶️ dismissTA called with:", { selectedTAObj, selectedExamItem });

    const taId = selectedTAObj?.userId ?? selectedTAObj?.id;

    const classProctoring = selectedExamItem
      .classProctoringTARelationDTO
      ?.classProctoringDTO;
    const classProctoringId = classProctoring?.id;

    console.log("   → taId =", taId, ", classProctoringId =", classProctoringId);

    if (!taId || !classProctoringId) {
      alert("Invalid TA or exam selection.");
      return;
    }

    try {
      const { data: success } = await axios.delete(
        "http://localhost:8080/classProctoringTARelation/removeTAFromClassProctoring",
        { params: { taId, classProctoringId } }
      );

      if (success) {
        alert("TA dismissed successfully.");
        // remove that TA from the assigned-list in state
        setSelectedExamItem(item => ({
          ...item,
          taProfileDTOList: item.taProfileDTOList.filter(t => t.id !== taId)
        }));
        setSelectedTA(null);
        setSelectedTAObj(null);
      } else {
        alert("Failed to dismiss the TA. Please try again.");
      }
    } catch (error) {
      console.error("Error dismissing TA:", error);
      alert("An error occurred. Please try again.");
    }
  };
  


  const fetchTAs = async (departmentCode, proctoringId) => {
    try {
      let response;

      if (!departmentCode) {
        response = await axios.get(
          "http://localhost:8080/ta/getAvailableTAsByFacultyExceptProctoring",
          {
            params: { facultyId, proctoringId },
          }
        );
      } else {
        response = await axios.get(
          "http://localhost:8080/ta/getAvailableTAsByDepartmentExceptProctoring",
          {
            params: { departmentCode, proctoringId },
          }
        );
      }

      setAllTAs(response.data || []);
      setTAs(response.data || []);
    } catch (error) {
      console.error("Error fetching available TAs:", error);
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
      const response = await axios.get(
        "http://localhost:8080/department/getAllDepartmentsInFaculty",
        {
          params: { facultyId },
        }
      );
      setDepartments(response.data || []);
    } catch (error) {
      console.error("Error fetching departments:", error);
    }
  };

  useEffect(() => {
    fetchCreatedExams();
    fetchDepartments();
  }, []);

  const handleAutomaticAssign = () => {
    alert("Automatically assigning TAs...");
  };

  const handleManualAssign = () => {
    alert("Manually assigning TAs...");
  };

  const createLogsDatabaseItems = () => {
    return createdExams
      .filter(
        (item) =>
          item.classProctoringTARelationDTO &&
          item.classProctoringTARelationDTO.classProctoringDTO
      )
      .map((item) => {
        const cp = item.classProctoringTARelationDTO.classProctoringDTO;
        const key = `${cp.courseName}-${cp.startDate}`;
        const isSelected = selectedExamKey === key;

        return (
          <AdminDatabaseItem
            key={key}
            type="exam"
            data={{
              id: cp.id,
              course: cp.courseName,
              date: cp.startDate,
              time: cp.timeInterval,
              location: cp.classrooms,
            }}
            onDelete={(id) => console.log(`Deleted exam with ID: ${id}`)}
            onSelect={() => {
              setSelectedExamKey(key);
              setSelectedExamItem(item);
              setTaDepartmentFilter("");
              fetchTAs("", cp.id);
            }}
            isSelected={isSelected}
            inLog={true}
          />
        );
      });
  };

  return (
    <div className="do-create-exam-container">
      <NavbarDO />
      <div className="do-create-exam-content">
        <div className="top-row">
          <div className="your-exams box">
            <h3>Your Exams</h3>
            <div className="exam-list">{createLogsDatabaseItems()}</div>
          </div>

          <div className="assigned-tas box">
            <h3>TAs Assigned for This Task</h3>
            <div className="ta-assigned-list">
              {selectedExamItem?.taProfileDTOList?.length > 0 ? (
                selectedExamItem.taProfileDTOList.map((ta) =>
                  createTAItem(ta, handleTAClick, selectedTA)
                )
              ) : (
                <div>No TAs assigned</div>
              )}
            </div>

            <button className="dismissTA-button" onClick={dismissTA}>
              Dismiss
            </button>
          </div>
        </div>

        <div className="bottom-row">
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

          <div className="choose-tas box">
            <h3>Choose TAs</h3>
            <div className="choose-header">
              <select
                value={taDepartmentFilter}
                onChange={(e) => {
                  const selectedDepartment = e.target.value;
                  setTaDepartmentFilter(selectedDepartment);

                  const proctoringId =
                    selectedExamItem?.classProctoringTARelationDTO?.classProctoringDTO?.id;
                  fetchTAs(selectedDepartment, proctoringId);
                }}
              >
                <option value="">Select Department</option>
                {departments.length > 0 ? (
                  departments.map((department) => (
                    <option
                      key={department.departmentCode}
                      value={department.departmentCode}
                    >
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
                tas.map((ta) =>
                  createTAItem(ta, handleTAClick, selectedTA)
                )
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
