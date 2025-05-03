import React, { useState, useEffect } from "react";
import NavbarDO from "./NavbarDO";
import "./DOExamsPage.css";
import AdminDatabaseItem from "../Admin/AdminDatabaseItem";
import TAItem from "../TAItem";
import axios from "axios";






const DOExamsPage = () => {
  const [taDepartmentFilter, setTaDepartmentFilter] = useState("");

  // Example exam data
  const [selectedTA, setSelectedTA] = useState(null);
  const [selectedTAObj, setSelectedTAObj] = useState(null);
  const [selectedExamKey, setSelectedExamKey] = useState(null);
  // For the search bar
  const [searchQuery, setSearchQuery] = useState("");
  const [departmentFilter, setDepartmentFilter] = useState("");
  // Dummy logic for "Automatic Assign"
  const handleAutomaticAssign = () => {
    alert("Automatic assignment logic goes here.");
  };
  // Dummy logic for "Manually Assign"
  const handleManualAssign = () => {
    alert("Manual assignment logic goes here.");
  };
  const handleTAClick = (ta) => {
    const key = `${ta.firstName || ta.name}-${ta.lastName || ta.surname}-${ta.email}`;
    setSelectedTA(key);
    setSelectedTAObj(ta); // Store full TA object
  };

  const [examItems, setExamItems] = useState([]);
  const facultyId = 1; // Hardcoded for now, you can pass dynamically later
  const [departments, setDepartments] = useState([]); // New state for departments
  const [selectedExamItem, setSelectedExamItem] = useState(null);

  const [tas, setTAs] = useState([]);
  const [allTAs, setAllTAs] = useState([]); //all fetched TAs




  const createLogsDatabaseItems = () => {
    return examItems
      .filter((item) => item.classProctoringTARelationDTO?.classProctoringDTO || item.classProctoringDTO)
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
              course: proctoring.courseName,
              date: proctoring.startDate,
              time: proctoring.timeInterval,
              location: proctoring.classrooms,
            }}
            onDelete={(id) => console.log(`Deleted exam with ID: ${id}`)}
            onSelect={() => {
              setSelectedExamKey(key);
              setSelectedExamItem(item);
              setTaDepartmentFilter("");
              fetchTAs("", proctoring.id);
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
      console.log("Fetched Exams: ", response.data);
      setExamItems(response.data || []);
    } catch (error) {
      console.error('Error fetching exams:', error);
    }
  };




  useEffect(() => {
    fetchExams();
    fetchTAs();
    fetchDepartments();
  }, []);





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
        { params: { taId, classProctoringId, removerId: 9 } }
      );

      if (success) {
        alert("TA dismissed successfully.");

        fetchTAs();
        fetchExams();
        // remove that TA from the assigned-list in state
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








  const createTAItem = (ta, onClickHandler, selectedTAKey) => {
    const key = `${ta.firstName || ta.name}-${ta.lastName || ta.surname}-${ta.email}`;
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




  return (
    <div className="do-exams-container">
      <NavbarDO />

      <div className="do-exams-content">
        {/* LEFT SECTION */}
        <div className="left-section">
          <div className="search-filter">
            <input
              type="text"
              placeholder="Name: ex. CS 202, E 400"
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



            <button className="search-button">Search</button>
          </div>

          <div className="exams-table">

            {createLogsDatabaseItems()}

          </div>
        </div>

        {/* RIGHT SECTION */}
        <div className="right-section">
          {/* Assigned TAs */}
          <div className="assigned-tas">
            <h3>Assigned TAs</h3>
            <div className="assigned-list">
              {selectedExamItem?.taProfileDTOList?.length > 0 ? (
                selectedExamItem.taProfileDTOList.map((ta) => (
                  <div key={ta.email} className="ta-item">
                    {createTAItem(ta, handleTAClick, selectedTA)}
                  </div>
                ))
              ) : (
                <div>No TAs assigned</div>
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
                tas.map((ta) => createTAItem(ta, handleTAClick, selectedTA))
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
            </div>
          </div>


        </div>
      </div>
    </div>
  );
};

export default DOExamsPage;
