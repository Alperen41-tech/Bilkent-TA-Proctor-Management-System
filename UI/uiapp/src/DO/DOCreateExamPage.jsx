// TOP IMPORTS
import React, { useState, useEffect } from "react";
import NavbarDO from "./NavbarDO";
import "./DOCreateExamPage.css";
import AdminDatabaseItem from "../Admin/AdminDatabaseItem";
import TAItem from "../TAItem";
import ManualAssignmentModal from "../ManualAssignmentModal";
import axios from "axios";
import AutomaticAssignmentModal from "../AutomaticAssignmentModal";

/**
 * DOCreateExamPage component
 * Allows Dean’s Office to create exams, view assigned TAs, and manage TA assignments manually or automatically.
 */
// COMPONENT
const DOCreateExamPage = () => {
  const facultyId = 1;

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
  const [showAutoModal, setShowAutoModal] = useState(false);
  const [autoSuggestedTAs, setAutoSuggestedTAs] = useState([]);
  const [selectedDepartment, setSelectedDepartment] = useState("");
  const [eligibilityRestriction, setEligibilityRestriction] = useState(false);
  const [oneDayRestriction, setOneDayRestriction] = useState(false);




  // --- FETCHERS ---

  /**
 * Fetches exams created by the logged-in DO from the backend.
 */

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

  /**
   * Fetches all departments under the DO’s faculty.
   */

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

  /**
   * Fetches available TAs from a specific department or the whole faculty,
   * depending on whether a departmentCode is provided.
   */

  const fetchTAs = async (departmentCode, proctoringId) => {
    try {
      let response;

      if (!departmentCode || departmentCode === "") {
        // Fetch all available TAs in faculty
        const token = localStorage.getItem("token");
        response = await axios.get('http://localhost:8080/ta/getAvailableTAsByFacultyExceptProctoringWithRestriction', {
          params: { facultyId: facultyId, proctoringId: proctoringId, eligibilityRestriction: eligibilityRestriction, oneDayRestriction: oneDayRestriction }, headers: {
            Authorization: `Bearer ${token}`
          }
        });
      } else {
        // Fetch available TAs in selected department
        const token = localStorage.getItem("token");
        response = await axios.get('http://localhost:8080/ta/getAvailableTAsByDepartmentExceptProctoringWithRestriction', {
          params: { departmentCode: departmentCode, proctoringId: proctoringId, eligibilityRestriction: eligibilityRestriction, oneDayRestriction: oneDayRestriction }, headers: {
            Authorization: `Bearer ${token}`
          }
        });
      }
      setAllTAs(response.data || []);
      setTAs(response.data || []);
    } catch (error) {
      console.error('Error fetching available TAs:', error);
    }
  };

  // --- EFFECTS ---

  // On mount: Fetch exams and departments
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

  /**
 * Handles selection of a TA from the UI.
 * Sets selected TA key and object for further actions.
 */

  const handleTAClick = (ta) => {
    const key = `${ta.name}-${ta.surname}-${ta.email}`;
    setSelectedTA(key);
    setSelectedTAObj(ta);
  };

  /**
   * Refreshes exam list and selected exam state after an assignment action is completed.
   */
  const refreshAfterAssignment = async () => {
    await fetchCreatedExams();

    try {
      const token = localStorage.getItem("token");
      const { data: updatedExams } = await axios.get(
        "http://localhost:8080/classProctoringTARelation/getClassProctoringOfCreator",
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      setCreatedExams(updatedExams);
      const updatedExam = updatedExams.find(
        (exam) =>
          exam.classProctoringTARelationDTO?.classProctoringDTO?.id ===
          selectedExamItem?.classProctoringTARelationDTO?.classProctoringDTO?.id
      );
      if (updatedExam) {
        setSelectedExamItem(updatedExam);
        fetchTAs();
        fetchDepartments();
        fetchCreatedExams();
        fetchTAs();
        setSelectedExamKey(null);
        setSelectedExamItem(null);
        setTAs([]);
        setAllTAs([]);
        setTaDepartmentFilter("");
        setSelectedTAObj(null);
      }
    } catch (error) {
      console.error("Error refreshing assigned TAs:", error);
    }
  };

  /**
   * Removes an assigned TA from a selected exam.
   */
  const dismissTA = async () => {
    const taId = selectedTAObj?.userId ?? selectedTAObj?.id;
    const classProctoringId = selectedExamItem?.classProctoringTARelationDTO?.classProctoringDTO?.id;

    if (!taId || !classProctoringId) return alert("Invalid TA or exam selection.");

    try {
      const token = localStorage.getItem("token");
      const { data: success } = await axios.delete(
        "http://localhost:8080/classProctoringTARelation/removeTAFromClassProctoring",
        {
          params: { taId, classProctoringId }, headers: {
            Authorization: `Bearer ${token}`
          }
        }
      );

      if (success) {
        alert("TA dismissed.");
        fetchDepartments();
        fetchCreatedExams();
        fetchTAs();
        setSelectedExamKey(null);
        setSelectedExamItem(null);
        setTAs([]);
        setAllTAs([]);
        setTaDepartmentFilter("");
        setSelectedTAObj(null);
      }
    } catch (error) {
      console.error("Error dismissing TA:", error);
      alert("An error occurred.");
    }
  };

  /**
   * Handles automatic TA assignment for a selected exam by requesting backend suggestions.
   */

  const handleAutomaticAssign = async () => {
    if (!selectedExamItem) {
      alert("Please select an exam first.");
      return;
    }

    const classProctoringId =
      selectedExamItem?.classProctoringTARelationDTO?.classProctoringDTO?.id;

    const courseCode =
      selectedExamItem?.classProctoringTARelationDTO?.classProctoringDTO?.courseCode || "";

    const departmentCode = courseCode.split(" ")[0];

    if (!classProctoringId || !departmentCode) {
      alert("Invalid exam data.");
      return;
    }

    if (!isAutoAssignmentWithinLimit()) {
      alert(`No available TA slots for this exam. All positions are filled or pending. Please reduce the TA count or wait for pending requests.`);
      return;
    }

    try {
      const token = localStorage.getItem("token");
      if (!token) {
        alert("No authentication token found.");
        return;
      }

      const response = await axios.get(
        "http://localhost:8080/authStaffProctoringRequestController/selectAuthStaffProctoringRequestAutomaticallyInDepartment",
        {
          params: {
            classProctoringId,
            departmentCode,
            count: taCount,
            eligibilityRestriction,
            oneDayRestriction,
          },
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      setAutoSuggestedTAs(response.data || []);
      setShowAutoModal(true);
    } catch (error) {
      console.error("Error during automatic assignment:", error);
      alert("Failed to get suggested TAs.");
    }
  };



  /**
   * Triggers manual assignment modal after checking availability and duplication.
   */

  const handleManualAssign = () => {
    if (!selectedExamItem || !selectedTAObj) return alert("Select an exam and TA first.");
    const alreadyAssigned = selectedExamItem.taProfileDTOList?.some((ta) => ta.email === selectedTAObj.email);
    if (alreadyAssigned) return alert("This TA is already assigned.");

    if (!hasAvailableTASlots()) {
      alert("No available TA slots for this exam. All positions are filled or pending. Please reduce the TA count or wait for pending requests.");
      return;
    }
    setShowManualModal(true);
  };
  /**
   * Forcefully assigns the selected TA to the selected exam without request confirmation.
   */

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
      const token = localStorage.getItem("token");
      const { data } = await axios.post("http://localhost:8080/authStaffProctoringRequestController/forceAuthStaffProctoringRequest", null, {
        params: { classProctoringId: cpId, taId },
        headers: {
          Authorization: `Bearer ${token}`
        }
      });

      if (data === true) {
        alert("TA forcefully assigned.");
        fetchDepartments();
        fetchCreatedExams();
        fetchTAs();
        setSelectedExamKey(null);
        setSelectedExamItem(null);
        setTAs([]);
        setAllTAs([]);
        setTaDepartmentFilter("");
        setSelectedTAObj(null);
        setShowManualModal(false);
      } else {
        alert("Force assignment failed.");
      }
    } catch (error) {
      console.error("Force assign error:", error);
      alert("Error in force assignment.");
    }
  };
  /**
   * Sends an assignment request to a TA for a selected exam.
   */

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
      const token = localStorage.getItem("token");
      if (!token) {
        alert("No authentication token found.");
        return;
      }

      const { data } = await axios.post(
        "http://localhost:8080/authStaffProctoringRequestController/sendAuthStaffProctoringRequest",
        null,
        {
          params: {
            classProctoringId: cpId,
            taId,
          },
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (data === true) {
        alert("Request sent.");
        fetchDepartments();
        fetchCreatedExams();
        fetchTAs();
        setSelectedExamKey(null);
        setSelectedExamItem(null);
        setTAs([]);
        setAllTAs([]);
        setTaDepartmentFilter("");
        setSelectedTAObj(null);
        setShowManualModal(false);
      } else {
        alert("Failed to send request.");
      }
    } catch (error) {
      console.error("Send request error:", error);
      alert("Error sending request.");
    }
  };


  /**
   * Sets selected exam and loads available TAs for assignment.
   */

  const handleExamSelect = (examItem, key) => {
    setSelectedExamItem(examItem);
    setSelectedExamKey(key);
    setSelectedTA(null);
    setSelectedTAObj(null);

    const proctoringId = examItem?.classProctoringTARelationDTO?.classProctoringDTO?.id;
    const courseCode = examItem?.classProctoringTARelationDTO?.classProctoringDTO?.courseCode;
    const deptCode = courseCode?.split(" ")[0] || "";
    setSelectedDepartment(deptCode);

    fetchTAs("", proctoringId);
  };

  /**
   * Submits a new exam creation form to the backend.
   */

  const handleCreateExam = async () => {
    try {
      const token = localStorage.getItem("token");
      if (!token) {
        alert("Authentication token not found.");
        return;
      }

      const payload = {
        courseId: selectedCourseId,
        startDate: `${examDate} ${startTime}:00`,
        endDate: `${examDate} ${endTime}:00`,
        classrooms: classrooms.split(",").map((c) => c.trim()),
        taCount,
        sectionNo,
        eventName,
      };

      const { data } = await axios.post(
        "http://localhost:8080/classProctoring/createClassProctoring",
        payload,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

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

  /**
   * Creates a TAItem component for display in TA selection lists.
   */

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
  /**
   * Checks if the selected exam still has unfilled TA slots.
   */

  const hasAvailableTASlots = () => {
    const examInfo = selectedExamItem?.classProctoringTARelationDTO?.classProctoringDTO;

    if (!examInfo) return false;

    const assigned = examInfo.numberOfAssignedTAs || 0;
    const pending = examInfo.numberOfPendingRequests || 0;
    const max = examInfo.tacount || 0;

    return assigned + pending < max;
  };
  /**
   * Checks if additional TAs can be assigned automatically without exceeding the required count.
   */

  const isAutoAssignmentWithinLimit = () => {
    const examInfo = selectedExamItem?.classProctoringTARelationDTO?.classProctoringDTO;

    if (!examInfo) return false;

    const assigned = examInfo.numberOfAssignedTAs || 0;
    const pending = examInfo.numberOfPendingRequests || 0;
    const max = examInfo.tacount || 0;

    return assigned + pending + taCount <= max;
  };

  /**
   * Renders the list of created exams using AdminDatabaseItem.
   */

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
            examType: cp.proctoringName,
            tacount: cp.numberOfAssignedTAs + " / " + cp.tacount

          }}

          isSelected={selectedExamKey === key}
          onSelect={() => handleExamSelect(exam, key)}
          onDelete={() => { }}
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

      <AutomaticAssignmentModal
        isOpen={showAutoModal}
        onClose={() => setShowAutoModal(false)}
        suggestedTAs={autoSuggestedTAs}
        selectedExamId={selectedExamItem?.classProctoringTARelationDTO?.classProctoringDTO?.id}
        refreshAfterAssignment={refreshAfterAssignment}
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
            <label>Section No:</label>
            <input type="number" value={sectionNo} min={1} onChange={(e) => setSectionNo(Number(e.target.value))} />
            <label>Start and End Time:</label>
            <input type="time" value={startTime} onChange={(e) => setStartTime(e.target.value)} />
            <input type="time" value={endTime} onChange={(e) => setEndTime(e.target.value)} />
            <label>TA Count:</label>
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

              <div className="checkbox-options" style={{ marginTop: "1rem" }}>
                <label>
                  <input
                    type="checkbox"
                    checked={eligibilityRestriction}
                    onChange={(e) => setEligibilityRestriction(e.target.checked)}
                  />
                  Eligibility Restriction
                </label>
                <br />
                <label>
                  <input
                    type="checkbox"
                    checked={oneDayRestriction}
                    onChange={(e) => setOneDayRestriction(e.target.checked)}
                  />
                  One Day Restriction
                </label>
              </div>
              <label>
                Enter TA count:
                <input
                  type="number"
                  min="1"
                  value={taCount}
                  onChange={(e) => setTaCount(parseInt(e.target.value))}
                  style={{ marginLeft: "0.5rem", width: "60px" }}
                />
              </label>


            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DOCreateExamPage;
