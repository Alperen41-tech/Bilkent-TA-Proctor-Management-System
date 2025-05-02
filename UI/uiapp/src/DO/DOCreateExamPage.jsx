import React, { useState, useEffect } from "react";
import NavbarDO from "./NavbarDO";
import "./DOCreateExamPage.css";
import AdminDatabaseItem from "../Admin/AdminDatabaseItem";
import TAItem from "../TAItem";
import axios from "axios";

const DOCreateExamPage = () => {
  const facultyId = 1;
  const creatorId = 9;

  const [createdExams, setCreatedExams] = useState([]);
  const [departments, setDepartments] = useState([]);
  const [courses, setCourses] = useState([]);
  const [tas, setTAs] = useState([]);
  const [allTAs, setAllTAs] = useState([]);

  const [selectedExamItem, setSelectedExamItem] = useState(null);
  const [selectedExamKey, setSelectedExamKey] = useState(null);

  const [selectedTA, setSelectedTA] = useState(null);
  const [selectedTAObj, setSelectedTAObj] = useState(null);
  const [taDepartmentFilter, setTaDepartmentFilter] = useState("");

  // Create exam form state
  const [eventName, setEventName] = useState("");
  const [examDate, setExamDate] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [classrooms, setClassrooms] = useState("");
  const [taCount, setTaCount] = useState(2);
  const [sectionNo, setSectionNo] = useState(1);
  const [selectedDepartmentId, setSelectedDepartmentId] = useState(null);
  const [selectedCourseId, setSelectedCourseId] = useState(null);

  useEffect(() => {
    fetchCreatedExams();
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
      const response = await axios.get(
        "http://localhost:8080/department/getAllDepartmentsInFaculty",
        { params: { facultyId } }
      );
      setDepartments(response.data || []);
    } catch (error) {
      console.error("Error fetching departments:", error);
    }
  };

  const fetchTAs = async (departmentCode, proctoringId) => {
    try {
      let response;
      if (!departmentCode) {
        response = await axios.get(
          "http://localhost:8080/ta/getAvailableTAsByFacultyExceptProctoring",
          { params: { facultyId, proctoringId } }
        );
      } else {
        response = await axios.get(
          "http://localhost:8080/ta/getAvailableTAsByDepartmentExceptProctoring",
          { params: { departmentCode, proctoringId } }
        );
      }
      setAllTAs(response.data || []);
      setTAs(response.data || []);
    } catch (error) {
      console.error("Error fetching available TAs:", error);
    }
  };

  const handleTAClick = (ta) => {
    const key = `${ta.name}-${ta.surname}-${ta.email}`;
    setSelectedTA(key);
    setSelectedTAObj(ta);
  };

  const handleAutomaticAssign = () => {
    alert("Automatically assigning TAs...");
  };
  
  const handleManualAssign = () => {
    alert("Manually assigning TAs...");
  };
  

  const dismissTA = async () => {
    const taId = selectedTAObj?.userId ?? selectedTAObj?.id;
    const classProctoringId =
      selectedExamItem?.classProctoringTARelationDTO?.classProctoringDTO?.id;

    if (!taId || !classProctoringId) {
      alert("Invalid TA or exam selection.");
      return;
    }

    try {
      const { data: success } = await axios.delete(
        "http://localhost:8080/classProctoringTARelation/removeTAFromClassProctoring",
        {
          params: { taId, classProctoringId },
        }
      );

      if (success) {
        alert("TA dismissed successfully.");
        setSelectedExamItem((item) => ({
          ...item,
          taProfileDTOList: item.taProfileDTOList.filter((t) => t.id !== taId),
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

  const createLogsDatabaseItems = () =>
    createdExams
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
              <input
                type="text"
                placeholder="Enter Exam Name"
                value={eventName}
                onChange={(e) => setEventName(e.target.value)}
              />

              <label>Date</label>
              <input
                type="date"
                value={examDate}
                onChange={(e) => setExamDate(e.target.value)}
              />

              <label>Classrooms</label>
              <input
                type="text"
                placeholder="e.g., EE-01,EA-312"
                value={classrooms}
                onChange={(e) => setClassrooms(e.target.value)}
              />

              <label>Department</label>
              <select
                value={selectedDepartmentId || ""}
                onChange={async (e) => {
                  const departmentId = parseInt(e.target.value);
                  setSelectedDepartmentId(departmentId);

                  try {
                    const { data } = await axios.get(
                      "http://localhost:8080/course/getCoursesInDepartment",
                      { params: { departmentId } }
                    );
                    setCourses(data || []);
                  } catch (error) {
                    console.error("Error fetching courses:", error);
                  }
                }}
              >
                <option value="">Select Department</option>
                {departments.map((dept) => (
                  <option key={dept.departmentId} value={dept.departmentId}>
                    {dept.departmentName}
                  </option>
                ))}
              </select>

              <label>Course</label>
              <select
                value={selectedCourseId || ""}
                onChange={(e) => setSelectedCourseId(parseInt(e.target.value))}
              >
                <option value="">Select Course</option>
                {courses.map((course) => (
                  <option key={course.id} value={course.id}>
                    {course.name}
                  </option>
                ))}
              </select>

              <label>Section No</label>
              <input
                type="number"
                value={sectionNo}
                onChange={(e) => setSectionNo(Number(e.target.value))}
                min={1}
              />

              <label>Start Time</label>
              <input
                type="time"
                value={startTime}
                onChange={(e) => setStartTime(e.target.value)}
              />

              <label>End Time</label>
              <input
                type="time"
                value={endTime}
                onChange={(e) => setEndTime(e.target.value)}
              />

              <label>TA Count</label>
              <input
                type="number"
                value={taCount}
                onChange={(e) => setTaCount(Number(e.target.value))}
              />

              <button
                className="create-type-button"
                onClick={async () => {
                  if (
                    !eventName ||
                    !examDate ||
                    !startTime ||
                    !endTime ||
                    !classrooms ||
                    !selectedDepartmentId ||
                    !selectedCourseId ||
                    !sectionNo
                  ) {
                    alert("Please fill all required fields.");
                    return;
                  }

                  const startDateTime = `${examDate} ${startTime}:00`;
                  const endDateTime = `${examDate} ${endTime}:00`;

                  try {
                    const response = await axios.post(
                      "http://localhost:8080/classProctoring/createClassProctoring",
                      {
                        courseId: selectedCourseId,
                        startDate: startDateTime,
                        endDate: endDateTime,
                        classrooms: classrooms.split(",").map((c) => c.trim()),
                        taCount,
                        sectionNo,
                        eventName,
                        creatorId: 9
                      }
                    );

                    if (response.data === true) {
                      alert("Exam created successfully!");
                      fetchCreatedExams();
                      // reset form
                      setEventName("");
                      setExamDate("");
                      setStartTime("");
                      setEndTime("");
                      setClassrooms("");
                      setSelectedDepartmentId(null);
                      setSelectedCourseId(null);
                      setSectionNo(1);
                      setTaCount(2);
                    } else {
                      alert("Failed to create the exam. Try again.");
                    }
                  } catch (error) {
                    console.error("Error creating exam:", error);
                    alert("An error occurred. Please try again.");
                  }
                }}
              >
                Create
              </button>
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
                {departments.map((department) => (
                  <option
                    key={department.departmentCode}
                    value={department.departmentCode}
                  >
                    {department.departmentName}
                  </option>
                ))}
              </select>
            </div>

            <div className="choose-list">
              {tas.length > 0 ? (
                tas.map((ta) => createTAItem(ta, handleTAClick, selectedTA))
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
