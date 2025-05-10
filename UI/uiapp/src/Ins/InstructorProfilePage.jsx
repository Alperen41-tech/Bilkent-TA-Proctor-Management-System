// src/InstructorProfilePage.jsx
import React, { use } from "react";
import "./InstructorProfilePage.css"; // Reusing the same CSS
import Navbar from "./NavbarINS";
import axios from "axios";
import { useState, useEffect, useRef } from "react";
import { min } from "date-fns";
/**
 * InstructorProfilePage component
 * Displays instructor profile details and enables password changes and TA request form submission.
 */

const InstructorProfilePage = () => {
  const [showChangePasswordModal, setShowChangePasswordModal] = useState(false);
  const [insProfileInfo, setInsProfileInfo] = useState({ courses: [] });
  const [showFormModal, setShowFormModal] = useState(false);
  const [instructorCourses, setInstructorCourses] = useState([]);

  //Refs for password inputs
  const oldPasswordRef = useRef();
  const newPasswordRef = useRef();
  const confirmNewPasswordRef = useRef();
  const minTALoadRef = useRef();
  const maxTALoadRef = useRef();
  const numberOfGraderRef = useRef();
  const descriptionRef = useRef();
  const mustHaveTAsRef = useRef();
  const preferredTAsRef = useRef();
  const preferredGradersRef = useRef();
  const unwantedTAsRef = useRef();
  const instructorIdRef = useRef();
  const courseIdRef = useRef();
  //-----------------------------------------------------------
  /**
 * handleCreateForm
 * Sends a TA request form to the backend using instructor's preferences.
 */

  const handleCreateForm = async () => {
    try {
      console.log("Course ID:", courseIdRef.current.value);
      console.log("Min TA Load:", minTALoadRef.current.value);
      console.log("Max TA Load:", maxTALoadRef.current.value);
      console.log("Number of Graders:", numberOfGraderRef.current.value);
      console.log("Description:", descriptionRef.current.value);
      console.log("Must-Have TAs:", mustHaveTAsRef.current.value);
      console.log("Preferred TAs:", preferredTAsRef.current.value);
      console.log("Preferred Graders:", preferredGradersRef.current.value);
      console.log("Unwanted TAs:", unwantedTAsRef.current.value);

      const response = await axios.post("http://localhost:8080/courseTAInstructorForm/create", {
        instructorId: 4,
        courseId: courseIdRef.current.value,
        minTALoad: minTALoadRef.current.value,
        maxTALoad: maxTALoadRef.current.value,
        numberOfGrader: numberOfGraderRef.current.value,
        description: descriptionRef.current.value,
        mustHaveTAs: mustHaveTAsRef.current.value,
        preferredTAs: preferredTAsRef.current.value,
        preferredGraders: preferredGradersRef.current.value,
        avoidedTAs: unwantedTAsRef.current.value
      });
      if (response.data) {
        alert("TA request form created successfully.");
        setShowFormModal(false);
      } else {
        alert("Failed to create TA request form. Please try again.");
      }
    } catch (error) {
      console.error("Error creating TA request form:", error);
    }
  };
  /**
 * handleChangePassword
 * Allows instructor to change their password by providing old and new values.
 */

  const handleChangePassword = async () => {
    try {
      console.log("Email:", insProfileInfo.email);
      console.log("Old Password:", oldPasswordRef.current.value);
      console.log("New Password:", newPasswordRef.current.value);
      console.log("Confirm New Password:", confirmNewPasswordRef.current.value);
      const response = await axios.put("http://localhost:8080/auth/changePassword", {
        email: insProfileInfo.email,
        oldPassword: oldPasswordRef.current.value,
        newPassword: newPasswordRef.current.value,
        userTypeName: "instructor"
      });
      if (response.data) {
        alert("Password changed successfully.");
        setShowChangePasswordModal(false);
      } else {
        alert("Failed to change password. Please try again.");
      }
    } catch (error) {
      console.error("Error changing password:", error);
    }

  };
  /**
   * fetchProfileInformation
   * Fetches the profile data of the currently logged-in instructor.
   */

  const fetchProfileInformation = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.get("http://localhost:8080/instructor/profile", {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      setInsProfileInfo(response.data);
      console.log(insProfileInfo);
    } catch (error) {
      console.error("Error fetching tasks:", error);
    }
  };

  /**
 * fetchInstructorCourses
 * Retrieves the list of courses assigned to the instructor for populating form options.
 */

  const fetchInstructorCourses = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.get("http://localhost:8080/course/getCoursesOfInstructor", {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      if (response.data) {
        setInstructorCourses(response.data);
        console.log("Instructor Courses:", response.data);
      }
      else {
        console.log("No courses found for this instructor.");
      }
    } catch (error) {
      console.error("Error fetching instructor courses:", error);
    }
  }

  useEffect(() => {
    fetchInstructorCourses();
    fetchProfileInformation();
  }, []);

  useEffect(() => {
    console.log(insProfileInfo);
    console.log(instructorCourses);
  }
    , [insProfileInfo, instructorCourses]);


  return (
    <div className="profile-container">
      <Navbar />

      <div className="profile-content">
        {/* Info Section */}
        <div className="info-card">
          <h3>Personal Information</h3>
          <p><strong>Name</strong><br />{insProfileInfo.name}</p>
          <p><strong>Surname</strong><br />{insProfileInfo.surname}</p>
          <p><strong>Email</strong><br />{insProfileInfo.email}</p>
          <p><strong>ID</strong><br />{insProfileInfo.bilkentId}</p>
          <p><strong>Role</strong><br />{insProfileInfo.role}</p>
          <p><strong>Department</strong><br />{insProfileInfo.departmentName}</p>
          <p><strong>Course(s)</strong><br />{insProfileInfo.courses.map((courseName, index) => {
            console.log(courseName);
            return (
              <span key={index}>
                {courseName}{index < insProfileInfo.courses.length - 1 ? ", " : ""}
              </span>
            );
          })}</p>
        </div>

        {/* Manage Account Section */}
        <div className="right-section">
          <div className="manage-card">
            <h3>Manage Account</h3>
            <button className="purple-button" onClick={() => setShowChangePasswordModal(true)}>Change Password</button>
            <button className="purple-button" onClick={() => setShowFormModal(true)}>Create TA Request Form for TA Assignments</button>
          </div>
        </div>
      </div>

      {/* Change Password Modal */}
      {showChangePasswordModal && (
        <div className="modal-overlay">
          <div className="modal">
            <h3>Change Password</h3>
            <label>Old Password</label>
            <input ref={oldPasswordRef} type="password" placeholder="Enter your old password" />
            <label>New Password</label>
            <input ref={newPasswordRef} type="password" placeholder="At least 8 characters long" />
            <label>Confirm New Password</label>
            <input ref={confirmNewPasswordRef} type="password" placeholder="Confirm new password" />
            <div className="modal-buttons">
              <button className="cancel-button" onClick={() => setShowChangePasswordModal(false)}>Cancel</button>
              <button className="apply-button" onClick={() => {
                if (!oldPasswordRef.current.value || !newPasswordRef.current.value || !confirmNewPasswordRef.current.value) {
                  alert("Please fill in all fields.");
                  return;
                }
                if (newPasswordRef.current.value !== confirmNewPasswordRef.current.value) {
                  alert("New password and confirmation do not match.");
                  return;
                }
                if (newPasswordRef.current.value.length < 8) {
                  alert("New password must be at least 8 characters long.");
                  return;
                }
                handleChangePassword();
              }}>Apply</button>
            </div>
          </div>
        </div>
      )}

      {showFormModal && (
        <div className="ins-profile-form-modal-overlay">
          <form className="ins-profile-form-modal" onSubmit={(e) => {
            e.preventDefault();
            handleCreateForm();
          }}>
            <h3>Create TA Assignment Form</h3>

            <label>Course ID</label>
            <select ref={courseIdRef} required defaultValue="">
              <option value="" disabled>Select a course</option>
              {instructorCourses.map((course) => (
                <option key={course.course.id} value={course.course.id}>
                  {course.course.name} - {course.course.courseCode}
                </option>
              ))}
            </select>
            {/* Integer Fields */}
            <label>Min TA Load</label>
            <input
              type="number"
              placeholder="Minimum TA load"
              ref={minTALoadRef}
              min={0}
              required
            />

            <label>Max TA Load</label>
            <input
              type="number"
              placeholder="Maximum TA load"
              ref={maxTALoadRef}
              min={0}
              required
            />

            <label>Number of Graders</label>
            <input
              type="number"
              placeholder="Number of graders"
              ref={numberOfGraderRef}
              min={0}
              required
            />

            {/* String Fields */}
            <label>Must-Have TAs</label>
            <input
              type="text"
              placeholder="Comma-separated TA names"
              ref={mustHaveTAsRef}
              pattern="^([^,]+)(,[^,]+)*$"
              title="Separate TA names with a single comma (,), e.g., Ali Vural,Demir Kara"
            />

            <label>Description</label>
            <input
              type="text"
              placeholder="Reason for must-have TAs"
              ref={descriptionRef}
            />

            <label>Preferred TAs</label>
            <input
              type="text"
              placeholder="Comma-separated preferred TA names (in order)"
              ref={preferredTAsRef}
              pattern="^([^,]+)(,[^,]+)*$"
              title="Separate TA names with a single comma (,), e.g., Ali Vural,Demir Kara"
            />

            <label>Preferred Graders</label>
            <input
              type="text"
              placeholder="Comma-separated grader names (in order)"
              ref={preferredGradersRef}
              pattern="^([^,]+)(,[^,]+)*$"
              title="Separate grader names with a single comma (,), e.g., Ali Vural,Demir Kara"
            />

            <label>Unwanted TAs</label>
            <input
              type="text"
              placeholder="Comma-separated unwanted TA names"
              ref={unwantedTAsRef}
              pattern="^([^,]+)(,[^,]+)*$"
              title="Separate TA names with a single comma (,), e.g., Ali Vural,Demir Kara"
            />
            <div className="modal-buttons">
              <button className="cancel-button" onClick={() => setShowFormModal(false)}>Cancel</button>
              <button className="apply-button" type="submit">Apply</button>
            </div>
          </form>
        </div>
      )}

    </div>
  );
};

export default InstructorProfilePage;
