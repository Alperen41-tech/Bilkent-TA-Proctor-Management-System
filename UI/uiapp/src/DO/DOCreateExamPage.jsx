import React, { useState, useEffect } from "react";
import NavbarDO from "./NavbarDO";
import "./DOCreateExamPage.css";
import AdminDatabaseItem from "../Admin/AdminDatabaseItem";
import TAItem from "../TAItem";
import axios from "axios";

const DOCreateExamPage = () => {
  const creatorId = 9;

  const [createdExams, setCreatedExams] = useState([]);
  const [selectedExamKey, setSelectedExamKey] = useState(null);
  const [selectedTA, setSelectedTA] = useState(null);

  // Form fields
  const [examTitle, setExamTitle] = useState("");
  const [examDate, setExamDate] = useState("");
  const [classroom, setClassroom] = useState("");
  const [department, setDepartment] = useState("");
  const [courseName, setCourseName] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [taCount, setTaCount] = useState(2);

  const [taDepartmentFilter, setTaDepartmentFilter] = useState("");
const [tas, setTAs] = useState([]);
const [departmentsList, setDepartmentsList] = useState([]);


  // For Choose TAs section
  const [chooseDepartmentFilter, setChooseDepartmentFilter] = useState("");
  const [availableTAs, setAvailableTAs] = useState([]);

  const departments = ["CS", "IE", "Other"];

  useEffect(() => {
    fetchCreatedExams();
    fetchAvailableTAs();
    fetchTAs();
    fetchDepartments();
  }, []);

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
        params: { facultyId: 1 },
      });
      console.log("Fetched Departments:", response.data);
      setDepartmentsList(response.data);
    } catch (error) {
      console.error('Error fetching departments:', error);
    }
  };
  
  const fetchTAs = async (departmentCode, proctoringId) => {
    try {
      let response;
  
      if (!departmentCode || departmentCode === "") {
        // Fetch all available TAs in faculty
        response = await axios.get('http://localhost:8080/ta/getAvailableTAsByFacultyExceptProctoring', {
          params: { facultyId: 1, proctoringId: proctoringId },
        });
      } else {
        // Fetch available TAs in selected department
        response = await axios.get('http://localhost:8080/ta/getAvailableTAsByDepartmentExceptProctoring', {
          params: { departmentCode: departmentCode, proctoringId: proctoringId },
        });
      }
  
      console.log("Fetched Available TAs:", response.data);
      setTAs(response.data || []);
    } catch (error) {
      console.error('Error fetching available TAs:', error);
    }
  };
  

  const handleCreateExam = () => {
    alert(`
    Exam Created!
    Title: ${examTitle}
    Date: ${examDate}
    Classroom: ${classroom}
    Department: ${department}
    Course Name: ${courseName}
    Start: ${startTime}
    End: ${endTime}
    TA Count: ${taCount}
    `);
    // later, axios.post() to create exam
  };

  const fetchAvailableTAs = async (departmentCode) => {
    try {
      if (!selectedExamKey) {
        console.warn("No exam selected yet, can't fetch TAs!");
        return;
      }
      const selectedExam = createdExams.find((item) => {
        const dto = item.classProctoringTARelationDTO.classProctoringDTO;
        const key = `${dto.courseName}-${dto.startDate}`;
        return key === selectedExamKey;
      });
  
      const proctoringId = selectedExam?.classProctoringTARelationDTO.classProctoringDTO.id;
  
      if (!proctoringId) {
        console.warn("No proctoring ID found for selected exam!");
        return;
      }
  
      let response;
      if (!departmentCode) {
        // No department selected: fetch faculty-wide available TAs
        response = await axios.get('http://localhost:8080/ta/getAvailableTAsByFacultyExceptProctoring', {
          params: { facultyId: 1, proctoringId: proctoringId },
        });
      } else {
        // Department selected
        response = await axios.get('http://localhost:8080/ta/getAvailableTAsByDepartmentExceptProctoring', {
          params: { departmentCode: departmentCode, proctoringId: proctoringId },
        });
      }
  
      console.log("Fetched Available TAs:", response.data);
      setAvailableTAs(response.data || []);
    } catch (error) {
      console.error('Error fetching available TAs:', error);
      setAvailableTAs([]);
    }
  };
  

  const handleTAClick = (ta) => {
    setSelectedTA(ta.email);
  };

  const createTAItem = (firstName, lastName, email, onClick, isSelected) => (
    <TAItem
      key={email}
      ta={{ firstName, lastName, email }}
      onClick={() => onClick({ firstName, lastName, email })}
      isSelected={isSelected}
    />
  );

  const createExamListItems = () =>
    createdExams.map((item) => {
      const dto = item.classProctoringTARelationDTO.classProctoringDTO;
      const key = `${dto.courseName}-${dto.startDate}`;
      return (
        <AdminDatabaseItem
          key={key}
          type="exam"
          data={{
            id: dto.id,
            course: dto.courseName,
            date: dto.startDate,
            time: dto.timeInterval,
            location: dto.classrooms,
          }}
          onSelect={() => setSelectedExamKey(key)}
          isSelected={selectedExamKey === key}
          inLog={true}
        />
      );
    });

  return (
    <div className="do-create-exam-container">
      <NavbarDO />

      <div className="do-create-exam-content">
        {/* Top Row */}
        <div className="top-row">
          {/* Your Exams */}
          <div className="your-exams box">
            <h3>Your Exams</h3>
            <div className="exam-list">{createExamListItems()}</div>
          </div>

          {/* TAs Assigned */}
          <div className="assigned-tas box">
            <h3>TAs Assigned for This Task</h3>
            <div className="ta-assigned-list">
              {(() => {
                const selectedExam = createdExams.find((item) => {
                  const dto = item.classProctoringTARelationDTO.classProctoringDTO;
                  const key = `${dto.courseName}-${dto.startDate}`;
                  return key === selectedExamKey;
                });

                if (!selectedExam || selectedExam.taProfileDTOList.length === 0) {
                  return <div>No TAs assigned</div>;
                }

                return selectedExam.taProfileDTOList.map((ta) =>
                  createTAItem(
                    ta.name,
                    ta.surname,
                    ta.email,
                    handleTAClick,
                    selectedTA === ta.email
                  )
                );
              })()}
            </div>
            <button className="dismissTA-button">
              Dismiss
            </button>
          </div>
        </div>

        {/* Bottom Row */}
        <div className="bottom-row">
          {/* Create Exam Form */}
          <div className="create-task box">
            <h3>Create Exam</h3>
            <div className="form-grid">
              <div className="form-group">
                <label>Exam Title</label>
                <input
                  type="text"
                  value={examTitle}
                  onChange={(e) => setExamTitle(e.target.value)}
                  placeholder="Enter Exam Title"
                />
              </div>

              <div className="form-group">
                <label>Date</label>
                <input
                  type="date"
                  value={examDate}
                  onChange={(e) => setExamDate(e.target.value)}
                />
              </div>

              <div className="form-group">
                <label>Classroom</label>
                <input
                  type="text"
                  value={classroom}
                  onChange={(e) => setClassroom(e.target.value)}
                  placeholder="Enter Classroom"
                />
              </div>

              <div className="form-group">
                <label>Department</label>
                <select
                  value={department}
                  onChange={(e) => setDepartment(e.target.value)}
                >
                  <option value="">Select Department</option>
                  {departments.map((dep) => (
                    <option key={dep} value={dep}>
                      {dep}
                    </option>
                  ))}
                </select>
              </div>

              <div className="form-group">
                <label>Course Name</label>
                <input
                  type="text"
                  value={courseName}
                  onChange={(e) => setCourseName(e.target.value)}
                  placeholder="e.g., CS 202"
                />
              </div>

              <div className="form-group">
                <label>Start Time</label>
                <input
                  type="time"
                  value={startTime}
                  onChange={(e) => setStartTime(e.target.value)}
                />
              </div>

              <div className="form-group">
                <label>End Time</label>
                <input
                  type="time"
                  value={endTime}
                  onChange={(e) => setEndTime(e.target.value)}
                />
              </div>

              <div className="form-group">
                <label>TA Count</label>
                <input
                  type="number"
                  value={taCount}
                  min="0"
                  onChange={(e) => setTaCount(Number(e.target.value))}
                />
              </div>

              <div className="form-group full-width">
                <button className="create-btn" onClick={handleCreateExam}>
                  Create Exam
                </button>
              </div>
            </div>
          </div>

          {/* Choose TAs */}
          <div className="choose-tas box">
            <h3>Choose TAs</h3>
            <div className="choose-header">
              <select
                value={chooseDepartmentFilter}
                onChange={(e) => {
                  const dep = e.target.value;
                  setChooseDepartmentFilter(dep);
                  fetchAvailableTAs(dep);
                }}
              >
                <option value="">Select Department</option>
                {departments.map((dep) => (
                  <option key={dep} value={dep}>
                    {dep}
                  </option>
                ))}
              </select>
            </div>

            <div className="ta-list">
              {availableTAs.length ? (
                availableTAs.map((ta) =>
                  createTAItem(
                    ta.name,
                    ta.surname,
                    ta.email,
                    handleTAClick,
                    selectedTA === ta.email
                  )
                )
              ) : (
                <div>No TAs available</div>
              )}
            </div>

            <div className="assign-buttons">
              <button className="assign-btn" onClick={() => alert("Automatic Assign")}>
                Automatically Assign
              </button>
              <button className="assign-btn" onClick={() => alert("Manual Assign")}>
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
