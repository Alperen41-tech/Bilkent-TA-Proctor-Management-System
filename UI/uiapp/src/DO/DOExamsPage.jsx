import React, { useState, useEffect } from "react";
import NavbarDO from "./NavbarDO";
import "./DOExamsPage.css";
import AdminDatabaseItem from "../Admin/AdminDatabaseItem";
import TAItem from "../TAItem";
import axios from "axios";
import ManualAssignmentModal from "../ManualAssignmentModal";
import AutomaticAssignmentModal from "../AutomaticAssignmentModal";
import OtherFacultyTAModal from "../OtherFacultyTAModal";

/**
 * DOExamsPage component
 * Allows Dean’s Office to view all exams, assign TAs (manually or automatically),
 * filter by department, and manage TA visibility and restrictions.
 */
const DOExamsPage = () => {
  const [taDepartmentFilter, setTaDepartmentFilter] = useState("");

  // Example exam data
  // Currently selected TA's unique key (used for visual selection)
  const [selectedTA, setSelectedTA] = useState(null);
  const [selectedTAObj, setSelectedTAObj] = useState(null);
  const [selectedExamKey, setSelectedExamKey] = useState(null);
  // For the search bar
  // Search filter for course name, section, or location
  const [searchQuery, setSearchQuery] = useState("");
  const [departmentFilter, setDepartmentFilter] = useState("");

  const [showAutoModal, setShowAutoModal] = useState(false);
  const [taCount, setTaCount] = useState(1);
  const [eligibilityRestriction, setEligibilityRestriction] = useState(false);
  const [oneDayRestriction, setOneDayRestriction] = useState(false);
  const [autoSuggestedTAs, setAutoSuggestedTAs] = useState([]);
  const [selectedDepartment, setSelectedDepartment] = useState("");
  const [showOtherFacultyModal, setShowOtherFacultyModal] = useState(false);




  /**
   * Requests the backend for automatic TA assignment suggestions based on department and restrictions.
   */

  const handleAutomaticAssign = async () => {
    if (!selectedExamItem) {
      alert("Please select an exam first.");
      return;
    }

    const classProctoringId = selectedExamItem?.classProctoringTARelationDTO?.classProctoringDTO?.id;
    if (!classProctoringId) {
      alert("Invalid exam data.");
      return;
    }

    const departmentCode = selectedDepartment;
    if (!departmentCode) {
      alert("No department code available. Please reselect the exam.");
      return;
    }

    if (!isAutoAssignmentWithinLimit()) {

      alert(`No available TA slots for this exam. All positions are filled or pending. Please reduce the TA count or wait for pending requests.`);
      return;
    }

    try {
      const response = await axios.get(
        "http://localhost:8080/authStaffProctoringRequestController/selectAuthStaffProctoringRequestAutomaticallyInDepartment",
        {
          params: {
            classProctoringId,
            departmentCode,
            senderId: 9,
            count: taCount,
            eligibilityRestriction,
            oneDayRestriction,
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
   * Validates selection and opens modal for manual assignment to an exam.
   */

  const hasAvailableTASlots = () => {
    const examInfo = selectedExamItem?.classProctoringTARelationDTO?.classProctoringDTO;

    if (!examInfo) return false;

    const assigned = examInfo.numberOfAssignedTAs || 0;
    const pending = examInfo.numberOfPendingRequests || 0;
    const max = examInfo.tacount || 0;

    return assigned + pending < max;
  };

  const isAutoAssignmentWithinLimit = () => {
    const examInfo = selectedExamItem?.classProctoringTARelationDTO?.classProctoringDTO;

    if (!examInfo) return false;

    const assigned = examInfo.numberOfAssignedTAs || 0;
    const pending = examInfo.numberOfPendingRequests || 0;
    const max = examInfo.tacount || 0;

    return assigned + pending + taCount <= max;
  };



  const handleManualAssign = () => {
    if (!selectedTAObj || !selectedExamItem) {
      alert("Please select both an exam and a TA before assigning.");
      return;
    }

    const assignedTAs = selectedExamItem?.taProfileDTOList || [];
    const isAssigned = assignedTAs.some(
      (ta) => ta.email === selectedTAObj.email
    );

    if (isAssigned) {
      alert("This TA is already assigned. Please choose from the available TAs below.");
      return;
    }

    if (!hasAvailableTASlots()) {
      alert("No available TA slots for this exam. All positions are filled or pending. Please reduce the TA count or wait for pending requests.");
      return;
    }

    setShowManualModal(true);
  };

  /**
   * Forcefully assigns a TA to the selected exam, bypassing confirmation.
   */

  const handleForceAssign = async () => {
    try {
      const classProctoringId = selectedExamItem?.classProctoringTARelationDTO?.classProctoringDTO?.id;
      const taId = selectedTAObj?.id || selectedTAObj?.userId;
      const departmentCode = selectedExamItem?.classProctoringTARelationDTO?.classProctoringDTO?.departmentCode;
      const token = localStorage.getItem("token");
      const response = await axios.post("http://localhost:8080/authStaffProctoringRequestController/forceAuthStaffProctoringRequest", null, {
        params: { classProctoringId, taId },
        headers: {
          Authorization: `Bearer ${token}`
        }
      });

      if (response.data === true) {
        alert("TA forcefully assigned.");
        fetchDepartments();
        fetchExams();
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
      console.error("Error in force assignment:", error);
      alert("An error occurred during force assignment.");
    }
  };

  /**
   * Sends a TA assignment request to the selected TA for the selected exam.
   */

  const handleSendRequest = async () => {
    try {
      const classProctoringId = selectedExamItem?.classProctoringTARelationDTO?.classProctoringDTO?.id;
      const taId = selectedTAObj?.id || selectedTAObj?.userId;
      const senderId = 9;

      const token = localStorage.getItem("token");
      const response = await axios.post("http://localhost:8080/authStaffProctoringRequestController/sendAuthStaffProctoringRequest", null, {
        params: { classProctoringId, taId, senderId },
        headers: {
          Authorization: `Bearer ${token}`
        }
      });

      if (response.data === true) {
        alert("Request sent to TA.");

        fetchDepartments();
        fetchExams();
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
      console.error("Error in sending request:", error);
      alert("An error occurred while sending request.");
    }
  };


  // Selects a TA from the assigned list

  const handleTAClick = (ta) => {
    const key = `${ta.firstName || ta.name}-${ta.lastName || ta.surname}-${ta.email}`;
    setSelectedTA(key);
    setSelectedTAObj(ta); // Store full TA object
  };

  // Selects a TA from the available list for assignment

  const handleAvailableTAClick = (ta) => {
    const key = `${ta.firstName || ta.name}-${ta.lastName || ta.surname}-${ta.email}`;
    setSelectedTA(key);
    setSelectedTAObj(ta);
  };


  const [examItems, setExamItems] = useState([]);
  const facultyId = 1; // Hardcoded for now, you can pass dynamically later
  const [departments, setDepartments] = useState([]); // New state for departments
  const [selectedExamItem, setSelectedExamItem] = useState(null);

  const [tas, setTAs] = useState([]);
  const [allTAs, setAllTAs] = useState([]); //all fetched TAs

  const [showManualModal, setShowManualModal] = useState(false);





  const createLogsDatabaseItems = () => {
    const query = searchQuery.toLowerCase().trim();

    return examItems
      .filter((item) => {
        const proctoring = item.classProctoringTARelationDTO?.classProctoringDTO || item.classProctoringDTO;
        if (!proctoring) return false;

        const course = proctoring.courseName?.toLowerCase() || "";
        const location = proctoring.classrooms?.toLowerCase() || "";
        const section = proctoring.section?.toString() || "";

        return (
          course.includes(query) ||
          location.includes(query) ||
          section.includes(query)
        );
      })
      .map((item) => {
        const proctoring = item.classProctoringTARelationDTO?.classProctoringDTO || item.classProctoringDTO;
        const key = `${proctoring.courseName}-${proctoring.startDate}`;
        const isSelected = selectedExamKey === key;

        return (
          <AdminDatabaseItem
            key={key}
            type="exam"
            data={{
              id: proctoring.id,
              course: proctoring.courseCode + " " + proctoring.courseName,
              date: new Date(proctoring.startDate).toLocaleDateString(),
              time: new Date(proctoring.startDate).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
              endTime: new Date(proctoring.endDate).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
              location: proctoring.classrooms,
              Section: proctoring.section,
              examType: proctoring.proctoringName,
              tacount: proctoring.numberOfAssignedTAs + " / " + proctoring.tacount
            }}
            onSelect={() => {
              setSelectedExamKey(key);
              setSelectedExamItem(item);
              setTaDepartmentFilter("");

              const departmentCode = proctoring.courseCode?.split(" ")[0] || "";
              setSelectedDepartment(departmentCode);

              fetchTAs("", proctoring.id);
            }}

            isSelected={isSelected}
            inLog={true}
          />
        );
      });
  };

  /**
   * Fetches available TAs, filtered by department and proctoring ID.
   * Applies eligibility and one-day restrictions.
   */
  const fetchTAs = async (departmentCode, proctoringId) => {
    console.log(facultyId, proctoringId, eligibilityRestriction, oneDayRestriction);
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

  /**
   * Fetches exams for the DO’s faculty or a specific department.
   * If no departmentCode is given, all faculty exams are returned.
   */

  const fetchExams = async (departmentCode) => {
    try {
      let response;
      if (!departmentCode || departmentCode === "") {
        // Empty = fetch all faculty exams
        response = await axios.get('http://localhost:8080/classProctoringTARelation/getFacultyClassProctoringsById', {
          params: { facultyId: facultyId },
        });
      } else {
        // Otherwise, fetch department-specific exams
        response = await axios.get('http://localhost:8080/classProctoringTARelation/getDepartmentClassProctoringsByCode', {
          params: { departmentCode: departmentCode },
        });
      }

      console.log("🧪 Exams API Response:", response.data);
      setExamItems(response.data || []);
      fetchTAs();
      fetchDepartments();
      fetchTAs();
      setSelectedExamKey(null);
      setSelectedExamItem(null);
      setTAs([]);
      setAllTAs([]);
      setTaDepartmentFilter("");
      setSelectedTAObj(null);
    } catch (error) {
      console.error('Error fetching exams:', error);
    }
  };




  useEffect(() => {
    fetchExams();
    fetchTAs();
    fetchDepartments();
  }, []);

  /**
   * Fetches all departments under the current faculty.
   */

  const fetchDepartments = async () => {
    try {
      const response = await axios.get('http://localhost:8080/department/getAllDepartmentsInFaculty', {
        params: {
          facultyId: 1, // <-- Hardcoded for now
        },
      });
      console.log("Fetched Departments: ", response.data);
      setDepartments(response.data);
    } catch (error) {
      console.error('Error fetching departments:', error);
    }
  };

  /**
   * Dismisses a TA from the selected exam and updates all related views.
   */

  const dismissTA = async () => {
    console.log("▶️ dismissTA called with:", { selectedTAObj, selectedExamItem });

    const taId = selectedTAObj?.userId ?? selectedTAObj?.id;

    const classProctoring = selectedExamItem
      ?.classProctoringTARelationDTO
      ?.classProctoringDTO;
    const classProctoringId = classProctoring?.id;

    console.log("   → taId =", taId, ", classProctoringId =", classProctoringId);

    if (!taId || !classProctoringId) {
      alert("Invalid TA or exam selection.");
      return;
    }

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
        alert("TA dismissed successfully.");
        fetchDepartments();
        fetchExams();
        fetchTAs();
        setSelectedExamKey(null);
        setSelectedExamItem(null);
        setTAs([]);
        setAllTAs([]);
        setTaDepartmentFilter("");
        setSelectedTAObj(null);
      } else {
        alert("Failed to dismiss the TA. Please try again.");
      }
    } catch (error) {
      console.error("Error dismissing TA:", error);
      alert("An error occurred. Please try again.");
    }
  };

  /**
   * Creates a TAItem component with selection highlighting and click handler.
   */
  const createTAItem = (ta, onClickHandler, selectedTAKey) => {
    const key = `${ta.firstName || ta.name}-${ta.lastName || ta.surname}-${ta.email}`;
    const isSelected = selectedTAKey === key;

    return (
      <TAItem
        key={key}
        ta={ta}
        onClick={() => onClickHandler(ta)}
        isSelected={isSelected}
        inInstructor={true}
      />
    );
  };





  return (
    <div className="do-exams-container">
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
        refreshAfterAssignment={() => fetchExams(departmentFilter)}
      />


      <OtherFacultyTAModal
        isOpen={showOtherFacultyModal}
        onClose={() => setShowOtherFacultyModal(false)}
        classProctoringId={selectedExamItem?.classProctoringTARelationDTO?.classProctoringDTO?.id}
        onSuccess={() => {
          fetchExams();
          fetchTAs();
        }}
      />







      <div className="do-exams-content">
        {/* LEFT SECTION */}
        <div className="left-section">
          <div className="search-filter">
            <input
              type="text"
              placeholder="Name: ex. CS 202, IE 400"
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
            />
            <select
              value={departmentFilter}
              onChange={(e) => {
                const selectedDepartment = e.target.value;
                setDepartmentFilter(selectedDepartment);

                setSelectedExamKey(null);
                setSelectedExamItem(null);
                setTAs([]);
                setAllTAs([]);
                setTaDepartmentFilter("");

                fetchExams(selectedDepartment);
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

          <div className="exams-table">

            {createLogsDatabaseItems()}

          </div>

          {selectedExamItem && (
            <div className="exam-details-panel">
              <h3>Exam Details</h3>
              <p><strong>Course:</strong> {selectedExamItem.classProctoringTARelationDTO?.classProctoringDTO?.courseName}</p>
              <p><strong>Exam Type:</strong> {selectedExamItem.classProctoringTARelationDTO?.classProctoringDTO?.proctoringName}</p>
              <p><strong>Date:</strong> {new Date(selectedExamItem.classProctoringTARelationDTO?.classProctoringDTO?.startDate).toLocaleDateString()}</p>
              <p><strong>Time:</strong> {new Date(selectedExamItem.classProctoringTARelationDTO?.classProctoringDTO?.startDate).toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" })}</p>
              <p><strong>End Time:</strong> {new Date(selectedExamItem.classProctoringTARelationDTO?.classProctoringDTO?.endDate).toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" })}</p>
              <p><strong>Location:</strong> {selectedExamItem.classProctoringTARelationDTO?.classProctoringDTO?.classrooms}</p>
              <p><strong>Section:</strong> {selectedExamItem.classProctoringTARelationDTO?.classProctoringDTO?.section}</p>
              <p><strong>TA Count:</strong> {selectedExamItem.classProctoringTARelationDTO.classProctoringDTO.numberOfAssignedTAs + " / " + selectedExamItem.classProctoringTARelationDTO.classProctoringDTO.tacount}</p>
              <p><strong>Pending TA Requests:</strong> {selectedExamItem.classProctoringTARelationDTO.classProctoringDTO.numberOfPendingRequests}</p>
            </div>
          )}

        </div>

        {/* RIGHT SECTION */}
        <div className="right-section">
          {/* Assigned TAs */}
          <div className="assigned-tas">
            <h3>Assigned TAs</h3>
            <div className="assigned-list">
              {selectedExamItem?.taProfileDTOList?.length > 0 ? (
                selectedExamItem.taProfileDTOList.map((ta) => (
                  <div key={ta.email}>
                    {createTAItem(ta, handleTAClick, selectedTA)}
                  </div>

                ))

              ) : (
                <div>No TAs available</div>
              )}

              <button className="dismissTA-button" onClick={dismissTA}>
                Dismiss
              </button>
            </div>
          </div>



          {/* Choose TAs */}
          <div className="choose-tas">
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
                tas.map((ta) => createTAItem(ta, handleAvailableTAClick, selectedTA))
              ) : (
                <div>No TAs available</div>
              )}

            </div>

            <div className="choose-actions">
              <button className="assign-button" onClick={handleAutomaticAssign}>
                Automatic Assign
              </button>
              <button className="assign-button" onClick={handleManualAssign}>
                Manually Assign
              </button>
              <button className="assign-button" onClick={() => setShowOtherFacultyModal(true)}>
                Outside Faculty TA
              </button>



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
                <br />
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
    </div>
  );
};

export default DOExamsPage;
