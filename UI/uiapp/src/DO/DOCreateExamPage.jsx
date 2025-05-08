// TOP IMPORTS
import React, { useState, useEffect } from "react";
import NavbarDO from "./NavbarDO";
import "./DOCreateExamPage.css";
import AdminDatabaseItem from "../Admin/AdminDatabaseItem";
import TAItem from "../TAItem";
import ManualAssignmentModal from "../ManualAssignmentModal";
import axios from "axios";

// COMPONENT
const DOCreateExamPage = () => {
  const facultyId = 1;
  const creatorId = 9;

  // --- STATE DECLARATIONS ---
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
  const [showManualModal, setShowManualModal] = useState(false);

  const [eventName, setEventName] = useState("");
  const [examDate, setExamDate] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [classrooms, setClassrooms] = useState("");
  const [taCount, setTaCount] = useState(2);
  const [sectionNo, setSectionNo] = useState(1);
  const [selectedDepartmentId, setSelectedDepartmentId] = useState(null);
  const [selectedCourseId, setSelectedCourseId] = useState(null);

  // --- FETCHERS ---
  const fetchCreatedExams = async () => {
    try {
      const token = localStorage.getItem("token");
      const { data } = await axios.get("http://localhost:8080/classProctoringTARelation/getClassProctoringOfCreator", {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      setCreatedExams(data || []);
    } catch (error) {
      console.error("Failed to fetch created exams:", error);
    }
  };

  const fetchDepartments = async () => {
    try {
      const { data } = await axios.get("http://localhost:8080/department/getAllDepartmentsInFaculty", {
        params: { facultyId },
      });
      setDepartments(data || []);
    } catch (error) {
      console.error("Error fetching departments:", error);
    }
  };

  const fetchTAs = async (departmentCode, proctoringId) => {
    try {
      const url = departmentCode
        ? "http://localhost:8080/ta/getAvailableTAsByDepartmentExceptProctoring"
        : "http://localhost:8080/ta/getAvailableTAsByFacultyExceptProctoring";

      const { data } = await axios.get(url, {
        params: { facultyId, departmentCode, proctoringId, userId: creatorId },
      });

      setAllTAs(data || []);
      setTAs(data || []);
    } catch (error) {
      console.error("Error fetching available TAs:", error);
    }
  };

  // --- EFFECTS ---
  useEffect(() => {
    fetchCreatedExams();
    fetchDepartments();
  }, []);

  useEffect(() => {
    if (selectedDepartmentId) {
      axios
        .get("http://localhost:8080/course/getCoursesInDepartment", {
          params: { departmentId: selectedDepartmentId },
        })
        .then(({ data }) => setCourses(data || []))
        .catch(console.error);
    }
  }, [selectedDepartmentId]);

  // --- TA ACTIONS ---
  const handleTAClick = (ta) => {
    const key = `${ta.name}-${ta.surname}-${ta.email}`;
    setSelectedTA(key);
    setSelectedTAObj(ta);
  };

  const dismissTA = async () => {
    const taId = selectedTAObj?.userId ?? selectedTAObj?.id;
    const classProctoringId = selectedExamItem?.classProctoringTARelationDTO?.classProctoringDTO?.id;

    if (!taId || !classProctoringId) return alert("Invalid TA or exam selection.");

    try {
      const token = localStorage.getItem("token");
      const { data: success } = await axios.delete(
        "http://localhost:8080/classProctoringTARelation/removeTAFromClassProctoring",
        { params: { taId, classProctoringId}, headers: {
            Authorization: `Bearer ${token}`
          } }
      );

      if (success) {
        alert("TA dismissed.");
        fetchCreatedExams();
        setSelectedTA(null);
        setSelectedTAObj(null);
      }
    } catch (error) {
      console.error("Error dismissing TA:", error);
      alert("An error occurred.");
    }
  };


  const handleAutomaticAssign = () => {
    alert("Automatic assignment logic goes here.");
  };

  const handleManualAssign = () => {
    if (!selectedExamItem || !selectedTAObj) return alert("Select an exam and TA first.");
    const alreadyAssigned = selectedExamItem.taProfileDTOList?.some((ta) => ta.email === selectedTAObj.email);
    if (alreadyAssigned) return alert("This TA is already assigned.");
    setShowManualModal(true);
  };

  const handleForceAssign = async () => {
    const cpId =
      selectedExamItem?.classProctoringTARelationDTO?.classProctoringDTO?.id ||
      selectedExamItem?.classProctoringDTO?.id ||
      selectedExamItem?.id;
  
    const taId = selectedTAObj?.userId || selectedTAObj?.id;
  
    console.log("🔍 Force Assign Debug:", { cpId, taId, selectedExamItem });
  
    if (!cpId || !taId) {
      alert("Missing required IDs for force assignment.");
      return;
    }
  
    try {
      const { data } = await axios.post(
        "http://localhost:8080/authStaffProctoringRequestController/forceAuthStaffProctoringRequest",
        null,
        {
          params: { classProctoringId: cpId, taId, senderId: creatorId },
        }
      );
  
      if (data === true) {
        alert("TA forcefully assigned.");
        fetchCreatedExams();
        fetchTAs(taDepartmentFilter, cpId);
        setShowManualModal(false);
      } else {
        alert("Force assignment failed.");
      }
    } catch (error) {
      console.error("Force assign error:", error);
      alert("Error in force assignment.");
    }
  };
  
  const handleSendRequest = async () => {
    const cpId =
      selectedExamItem?.classProctoringTARelationDTO?.classProctoringDTO?.id ||
      selectedExamItem?.classProctoringDTO?.id ||
      selectedExamItem?.id;
  
    const taId = selectedTAObj?.userId || selectedTAObj?.id;
  
    console.log("📨 Send Request Debug:", { cpId, taId, selectedExamItem });
  
    if (!cpId || !taId) {
      alert("Missing required IDs for request.");
      return;
    }
  
    try {
      const { data } = await axios.post(
        "http://localhost:8080/authStaffProctoringRequestController/sendAuthStaffProctoringRequest",
        null,
        {
          params: { classProctoringId: cpId, taId, senderId: creatorId },
        }
      );
  
      if (data === true) {
        alert("Request sent.");
        setShowManualModal(false);
      } else {
        alert("Failed to send request.");
      }
    } catch (error) {
      console.error("Send request error:", error);
      alert("Error sending request.");
    }
  };
  

  const handleExamSelect = (examItem, key) => {
    setSelectedExamItem(examItem);
    setSelectedExamKey(key);
    setSelectedTA(null);
    setSelectedTAObj(null);
    const proctoringId = examItem?.classProctoringTARelationDTO?.classProctoringDTO?.id;
    fetchTAs("", proctoringId);
  };

  const handleCreateExam = async () => {
    try {
      const payload = {
        courseId: selectedCourseId,
        startDate: `${examDate} ${startTime}:00`,
        endDate: `${examDate} ${endTime}:00`,
        classrooms: classrooms.split(",").map((c) => c.trim()),
        taCount,
        sectionNo,
        eventName,
        creatorId,
      };

      const { data } = await axios.post("http://localhost:8080/classProctoring/createClassProctoring", payload);
      if (data === true) {
        alert("Exam created.");
        fetchCreatedExams();
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
        alert("Failed to create exam.");
      }
    } catch (error) {
      console.error("Exam creation error:", error);
      alert("Error occurred.");
    }
  };

  const createTAItem = (ta, onClick, selectedKey) => {
    const key = `${ta.firstName || ta.name}-${ta.lastName || ta.surname}-${ta.email}`;
    return (
      <TAItem
        key={key}
        ta={ta}
        onClick={() => onClick(ta)}
        isSelected={selectedKey === key}
        inInstructor={true}
      />
    );
  };
  

  const renderExamItems = () =>
    createdExams.map((exam) => {
      const cp = exam.classProctoringTARelationDTO.classProctoringDTO;
      const key = `${cp.courseName}-${cp.startDate}`;
      return (
        <AdminDatabaseItem
          key={key}
          type="exam"
          data={{
            id: cp.id,
            course: cp.courseName,
            date: new Date(cp.startDate).toLocaleDateString(),
            time: new Date(cp.startDate).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
            endTime: new Date(cp.endDate).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
            location: cp.classrooms,
            Section: cp.section,
            examType: cp.proctoringName
          }}
          
          isSelected={selectedExamKey === key}
          onSelect={() => handleExamSelect(exam, key)}
          onDelete={() => {}}
          inLog={true}
        />
      );
    });

  return (
    <div className="do-create-exam-container">
      <NavbarDO />

      <ManualAssignmentModal
        isOpen={showManualModal}
        onForceAssign={handleForceAssign}
        onSendRequest={handleSendRequest}
        onCancel={() => setShowManualModal(false)}
      />

      {/* CONTENT LAYOUT */}
      <div className="do-create-exam-content">
        {/* TOP: Exams and Assigned TAs */}
        <div className="top-row">
          <div className="your-exams box">
            <h3>Your Exams</h3>
            <div className="exam-list">{renderExamItems()}</div>
          </div>
          <div className="assigned-tas box">
            <h3>TAs Assigned</h3>
            <div className="ta-assigned-list">
              {selectedExamItem?.taProfileDTOList?.length > 0 ? (
                selectedExamItem.taProfileDTOList.map((ta) =>
                  createTAItem(ta, handleTAClick, selectedTA)
                )
              ) : (
                <div>No TAs assigned</div>
              )}
            </div>
            <button className="dismissTA-button" onClick={dismissTA}>Dismiss</button>
          </div>
        </div>

        {/* BOTTOM: Create Exam and TA Selection */}
        <div className="bottom-row">
          <div className="create-task box">
            {/* Exam Form */}
            <label>Create Exam</label>
            <input type="text" value={eventName} onChange={(e) => setEventName(e.target.value)} placeholder="Exam Name" />
            <input type="date" value={examDate} onChange={(e) => setExamDate(e.target.value)} />
            <input type="text" value={classrooms} onChange={(e) => setClassrooms(e.target.value)} placeholder="EE-01, EA-312" />
            <select value={selectedDepartmentId || ""} onChange={(e) => setSelectedDepartmentId(Number(e.target.value))}>
              <option value="">Select Department</option>
              {departments.map((d) => <option key={d.departmentId} value={d.departmentId}>{d.departmentName}</option>)}
            </select>
            <select value={selectedCourseId || ""} onChange={(e) => setSelectedCourseId(Number(e.target.value))}>
              <option value="">Select Course</option>
              {courses.map((c) => <option key={c.id} value={c.id}>{c.name}</option>)}
            </select>
            <input type="number" value={sectionNo} min={1} onChange={(e) => setSectionNo(Number(e.target.value))} />
            <input type="time" value={startTime} onChange={(e) => setStartTime(e.target.value)} />
            <input type="time" value={endTime} onChange={(e) => setEndTime(e.target.value)} />
            <input type="number" value={taCount} min={1} onChange={(e) => setTaCount(Number(e.target.value))} />
            <button onClick={handleCreateExam}>Create</button>
          </div>

          <div className="choose-tas box">
            <h3>Choose TAs</h3>
            <select
              value={taDepartmentFilter}
              onChange={(e) => {
                const dept = e.target.value;
                setTaDepartmentFilter(dept);
                const pid = selectedExamItem?.classProctoringTARelationDTO?.classProctoringDTO?.id;
                fetchTAs(dept, pid);
              }}
            >
              <option value="">Select Department</option>
              {departments.map((d) => (
                <option key={d.departmentCode} value={d.departmentCode}>{d.departmentName}</option>
              ))}
            </select>
            <div className="choose-list">
              {tas.length > 0 ? (
                tas.map((ta) => createTAItem(ta, handleTAClick, selectedTA))
              ) : (
                <div>No TAs available</div>
              )}
            </div>
            <div className="choose-actions">
              <button onClick={handleAutomaticAssign}>Automatically Assign</button>
              <button onClick={handleManualAssign}>Manually Assign</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DOCreateExamPage;
