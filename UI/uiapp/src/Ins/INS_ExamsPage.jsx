import React, { useState, useEffect } from "react";
import NavbarINS from "./NavbarINS";
import "./NavbarINS.css";
import "./INS_ExamsPage.css";
import TAItem from "../TAItem";
import TaskItem from "../TaskItem";
import axios from "axios";
import ManualAssignmentModal from "../ManualAssignmentModal";

const INS_ExamsPage = () => {
  const instructorId = 4;

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
  const [eventName, setEventName] = useState("");
  const [examDate, setExamDate] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [classrooms, setClassrooms] = useState("");
  const [sectionNo, setSectionNo] = useState(1);
  const [instructorCourses, setInstructorCourses] = useState([]);
  const [selectedOfferedCourse, setSelectedOfferedCourse] = useState(null);

  useEffect(() => {
    fetchProctoringTasks();
    fetchAvailableTAs();
    fetchInstructorCourses();
  }, [selectedTask]);

  const fetchProctoringTasks = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.get("http://localhost:8080/classProctoringTARelation/getClassProctoringOfInstructor", {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      setProctoringTasks(response.data || []);
    } catch (error) {
      console.error("Error fetching proctoring tasks:", error);
    }
  };

  const fetchInstructorCourses = async () => {
    try {
      const token = localStorage.getItem("token")
      const response = await axios.get("http://localhost:8080/course/getCoursesOfInstructor", {
        params: { instructorId },
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      setInstructorCourses(response.data || []);
    } catch (error) {
      console.error("Error fetching instructor courses:", error);
    }
  };

  const fetchAvailableTAs = async () => {
    const proctoringId = selectedTask?.classProctoringTARelationDTO?.classProctoringDTO?.id;
    if (!proctoringId) return;
    try {
      const response = await axios.get("http://localhost:8080/ta/getAvailableTAsByDepartmentExceptProctoring", {
        params: {
          departmentCode: "CS",
          proctoringId,
          userId: instructorId,
        },
      });
      const assignedEmails = new Set(selectedTask.taProfileDTOList.map((ta) => ta.email));
      const filtered = (response.data || []).filter((ta) => !assignedEmails.has(ta.email));
      setAvailableTAs(filtered);
    } catch (error) {
      console.error("Error fetching available TAs:", error);
    }
  };

  const handleCreateExam = async () => {
    if (!eventName || !examDate || !startTime || !endTime || !classrooms || !selectedOfferedCourse || !sectionNo) {
      alert("Please fill all required fields.");
      return;
    }
    try {
      const response = await axios.post("http://localhost:8080/classProctoring/createClassProctoring", {
        courseId: selectedOfferedCourse.course.id, // ✅ Fix this line
        startDate: `${examDate} ${startTime}:00`,
        endDate: `${examDate} ${endTime}:00`,
        classrooms: classrooms.split(",").map((c) => c.trim()),
        taCount,
        sectionNo,
        eventName,
        creatorId: instructorId,
      });
      
      if (response.data === true) {
        alert("Exam created successfully!");
        setEventName("");
        setExamDate("");
        setStartTime("");
        setEndTime("");
        setClassrooms("");
        setSelectedOfferedCourse(null);
        setSectionNo(1);
        setTaCount(2);
        fetchProctoringTasks();
      } else {
        alert("Failed to create the exam.");
      }
    } catch (error) {
      console.error("Error creating exam:", error);
      alert("An error occurred: " + (error?.response?.data?.message || error.message));
    }
  };

  const handleDiscardTA = async () => {
    if (!selectedTA) return alert("No TA selected to discard.");
    try {
      const token = localStorage.getItem("token");
      const response = await axios.delete("http://localhost:8080/classProctoringTARelation/removeTAFromClassProctoring", {
        params: {
          classProctoringId: selectedTask.classProctoringTARelationDTO.classProctoringDTO.id,
          taId: selectedTA.userId,
        },
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      if (response.data) {
        alert("TA discarded successfully.");
        setSelectedTA(null);
        fetchProctoringTasks();
      }
    } catch (error) {
      console.error("Failed to discard TA:", error);
    }
  };

  const confirmManualAssign = async () => {
    const classProctoringId = selectedTask.classProctoringTARelationDTO.classProctoringDTO.id;
    const taId = selectedTA.userId;
    try {
      const response = await axios.post("http://localhost:8080/authStaffProctoringRequestController/forceAuthStaffProctoringRequest", null, {
        params: {
          classProctoringId,
          taId,
          senderId: instructorId,
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

  return (
    <div className="ins-exam-exams-page">
      <NavbarINS />
      <div className="ins-exam-grid-container">
        <div className="ins-exam-card ins-exam-assignments">
          <h3>Your Assignments with Proctors</h3>
          <div className="ins-exam-page-task-row">
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

        <div className="ins-exam-card ins-exam-create-task">
          <h3>Create a New Exam</h3>
          <label>Exam Name</label>
          <input value={eventName} onChange={(e) => setEventName(e.target.value)} type="text" />
          <label>Date</label>
          <input value={examDate} onChange={(e) => setExamDate(e.target.value)} type="date" />
          <label>Classrooms (comma-separated)</label>
          <input value={classrooms} onChange={(e) => setClassrooms(e.target.value)} type="text" />
          <label>Course</label>
          <select
            value={selectedOfferedCourse?.offeredCourseId || ""}
            onChange={(e) => {
              const course = instructorCourses.find(c => c.offeredCourseId === parseInt(e.target.value));
              setSelectedOfferedCourse(course);
            }}>
            <option value="">Select Course</option>
            {instructorCourses.map((course) => (
              <option key={course.offeredCourseId} value={course.offeredCourseId}>
                {course.course.name}
              </option>
            ))}
          </select>
          <label>Section No</label>
          <input type="number" value={sectionNo} onChange={(e) => setSectionNo(Number(e.target.value))} min={1} />
          <label>Start Time</label>
          <input value={startTime} onChange={(e) => setStartTime(e.target.value)} type="time" />
          <label>End Time</label>
          <input value={endTime} onChange={(e) => setEndTime(e.target.value)} type="time" />
          <label>TA Count</label>
          <input type="number" value={taCount} onChange={(e) => setTaCount(Number(e.target.value))} />
          <button onClick={handleCreateExam}>Create Exam</button>
        </div>

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
            <button onClick={() => {}}>Automatic Assign</button>
            <button onClick={() => setShowManualModal(true)}>Manual Assign</button>
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