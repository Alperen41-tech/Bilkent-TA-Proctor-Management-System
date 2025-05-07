import React, { useState, useRef, useEffect } from "react";
import NavbarAdmin from "./NavbarAdmin";
import "./AdminDatabasePage.css";
import AdminDatabaseItem from "./AdminDatabaseItem";
import axios from "axios";
import { type } from "@testing-library/user-event/dist/type";

const AdminDatabasePage = () => {
  const [selectedType, setSelectedType] = useState("");
  const [selectedFile, setSelectedFile] = useState(null);
  const [selectedData, setSelectedData] = useState({});
  const [departmentSelection, setDepartmentSelection] = useState("");
  const [selectTaWorktime, setSelectTaWorktime] = useState("");

  // Refs for new TA creation
  const newTaNameRef = useRef();
  const newTaSurnameRef = useRef();
  const newTaEmailRef = useRef();
  const newTaIdRef = useRef();
  const newTaCourseNameRef = useRef();
  const newTaPhoneNumRef = useRef();
  const newTaClassYearRef = useRef();
  const newTaPasswordRef = useRef();
  const [taRole, setTaRole] = useState("");
  const [departmentCode, setDepartmentCode] = useState("");
  const [courseCode, setCourseCode] = useState("");
  const [taWorkload, setTaWorkload] = useState(20);

  //-----------------------------

  // Refs for new Proctoring creation
  const newProctoringNameRef = useRef();
  const newProctoringCourseNameRef = useRef();
  const newProctoringSectionNumberRef = useRef();
  const newProctoringDateRef = useRef();
  const newProctoringStartTimeRef = useRef();
  const newProctoringEndTimeRef = useRef();
  const newProctoringClassroomsRef = useRef();
  const newProctoringTaCountRef = useRef();
  //-----------------------------

  const [departments, setDepartments] = useState([]);
  const [courses, setCourses] = useState([]);
  const [selectedDepartmentId, setSelectedDepartmentId] = useState(null);
  const [selectedCourseId, setSelectedCourseId] = useState(null);
  const [examDate, setExamDate] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [classrooms, setClassrooms] = useState("");

  // Refs for new Course creation
  const newCourseNameRef = useRef();
  const newCourseDescRef = useRef();
  const newCourseCodeRef = useRef();
  const newCourseCoordinatorIdRef = useRef();

  //instructor
  const newInstructorNameRef = useRef();
  const newInstructorSurnameRef = useRef();
  const newInstructorEmailRef = useRef();
  const newInstructorBilkentIdRef = useRef();
  const newInstructorRoleRef = useRef();
  const newInstructorPhoneRef = useRef();
  const newInstructorCoursesRef = useRef();
  const newInstructorPasswordRef = useRef();

  const [selectedDepartmentName, setSelectedDepartmentName] = useState("");
  const [selectedInstructorCourses, setSelectedInstructorCourses] = useState([]);
  const [selectedInstructorCourse, setSelectedInstructorCourse] = useState("");

  const [coursesData, setCoursesData] = useState([]);
  const [instructorsData, setInstructorsData] = useState([]);





  const handleImportClick = async () => {
    if (!selectedFile) {
      alert("Please select a file.");
      return;
    }

    const formData = new FormData();
    formData.append("file", selectedFile);

    try {
      const response = await axios.post("http://localhost:8080/excel/processTAAssignmentExcel", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });

      if (response.status === 200) {
        alert("Excel file successfully. Imported.");
      } else {
        alert("Upload failed.");
      }
    } catch (error) {
      console.error("File dump error:", error);
      alert("A problem occured.");
    }
  };

  const handleTypeChange = async (e) => {
    const type = e.target.value;
    setSelectedType(type);
  
    if (type === "Course") {
      try {
        const { data } = await axios.get("http://localhost:8080/course/getAllCourses");
        setCoursesData(data || []);
      } catch (error) {
        console.error("Error fetching courses:", error);
      }
    }
  
    if (type === "Instructor") {
      try {
        const { data } = await axios.get("http://localhost:8080/instructor/getAllInstructors");
        setInstructorsData(data || []);
      } catch (error) {
        console.error("Error fetching instructors:", error);
      }
    }
  };
  







  const handleFileChange = (e) => {
    if (e.target.files && e.target.files[0]) {
      setSelectedFile(e.target.files[0]);
    }
  };


  useEffect(() => {
    fetchDepartments();
  }, []);

  const fetchDepartments = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/department/getAllDepartmentsInFaculty",
        { params: { facultyId: 1 } } // Assuming Admin has access to the same faculty
      );
      setDepartments(response.data || []);
    } catch (error) {
      console.error("Error fetching departments:", error);
    }
  };

  const createDatabaseItems = () => {
    if (selectedType === "Course" && coursesData.length > 0) {
      return coursesData.map((course) => (
        <AdminDatabaseItem
          key={`course-${course.courseCode}`}
          type="course"
          data={course}
          onDelete={(id) => console.log(`Deleted course with ID: ${id}`)}
          onSelect={(data) => setSelectedData(data)}
          isSelected={selectedData.courseCode === course.courseCode}
          inLog={false}
        />
      ));
    }
  
    if (selectedType === "Instructor" && instructorsData.length > 0) {
      return instructorsData.map((instructor, index) => (
        <AdminDatabaseItem
          key={`instructor-${instructor.bilkentId || index}`}
          type="instructor"
          data={instructor}
          onDelete={(id) => console.log(`Deleted instructor with ID: ${id}`)}
          onSelect={(data) => setSelectedData(data)}
          isSelected={selectedData.bilkentId === instructor.bilkentId}
          inLog={false}
        />
      ));
    }
  
    return <p style={{ padding: "1rem" }}>No data to display.</p>;
  };
  


  const createNewTa = async () => {
    console.log("Calling createNewTa...");

    const selectedDept = departments.find((d) => d.departmentId === selectedDepartmentId);
    const selectedCourse = courses.find((c) => c.id === selectedCourseId);

    if (!selectedDept || !selectedCourse) {
      alert("Please select a department and a course.");
      return;
    }

    const name = newTaNameRef.current.value;
    const surname = newTaSurnameRef.current.value;
    const email = newTaEmailRef.current.value;
    const bilkentId = newTaIdRef.current.value;
    const phoneNumber = newTaPhoneNumRef.current.value;
    const classYear = parseInt(newTaClassYearRef.current.value);
    const password = newTaPasswordRef.current.value;

    try {
      const response = await axios.post("http://localhost:8080/ta/createTA", {
        profile: {
          name,
          surname,
          email,
          bilkentId,
          role: "TA",
          departmentName: selectedDept.departmentName,
          departmentCode: selectedDept.departmentCode, // ✅ correct name
          courseName: selectedCourse.name,
          courseCode: selectedCourse.courseCode?.toString(), // ✅ fix field
          phoneNumber,
          active: true,
          classYear,
          workload: taWorkload,
        },
        login: {
          email,
          password,
          userTypeName: "ta",
        },
      });

      console.log("Response:", response.data);

      if (response.data === true) {
        alert("TA created successfully!");
      } else {
        alert("Failed to create TA. Check values.");
      }
    } catch (error) {
      console.error("Error creating TA:", error.response?.data || error.message);
      alert("An error occurred.");
    }
  };











  const createNewProctoring = async (
    eventName,
    courseId,
    sectionNo,
    date,
    startTime,
    endTime,
    classrooms,
    taCount
  ) => {
    try {
      const response = await axios.post("http://localhost:8080/classProctoring/createClassProctoring", {
        eventName,
        courseId,
        sectionNo: parseInt(sectionNo),
        startDate: `${date} ${startTime}:00`,
        endDate: `${date} ${endTime}:00`,
        classrooms: classrooms.split(",").map((c) => c.trim()),
        taCount: parseInt(taCount),
        creatorId: 1, // Replace 1 with actual Admin userId if needed
      });

      if (response.data === true) {
        alert("Exam created successfully!");
        // Optionally reset fields here
      } else {
        alert("Failed to create the exam. Try again.");
      }
    } catch (error) {
      console.error("Error creating exam:", error);
      alert("An error occurred. Please try again.");
    }
  };


  const createNewCourse = async (name, description, courseCode, departmentId, coordinatorId) => {
    try {
      const response = await axios.post("http://localhost:8080/course/createCourse", {
        name,
        description,
        courseCode: parseInt(courseCode),
        departmentId,
        coordinatorId: 4,
        departmentCode: departments.find((d) => d.departmentId === departmentId)?.departmentName || ""
      });

      if (response.data === true) {
        alert("Course created successfully!");
      } else {
        alert("Failed to create course.");
      }
    } catch (error) {
      console.error("Error creating course:", error.response?.data || error.message);
      alert("An error occurred while creating the course.");
    }
  };

  const createInstructor = async (
    name,
    surname,
    email,
    bilkentId,
    role,
    departmentName,
    departmentId,
    phoneNumber,
    password,
    courses = []
  ) => {
    try {
      const payload = {
        instructorDTO: {
          name,
          surname,
          email,
          bilkentId,
          role,
          departmentName,
          departmentId,
          active: true,
          courses,
          phoneNumber
        },
        loginDTO: {
          email,
          password,
          userTypeName: "instructor"
        }
      };

      console.log("POST payload:", payload); // <== DEBUG LOG

      const response = await axios.post("http://localhost:8080/instructor/createInstructor", payload);

      if (response.data === true) {
        alert("Instructor created successfully!");
      } else {
        alert("Failed to create instructor.");
      }
    } catch (error) {
      console.error("Error creating instructor:", error.response?.data || error.message);
      alert("An error occurred while creating the instructor.");
    }
  };









  return (
    <div className="admin-database-database-container">
      <NavbarAdmin />

      {/* Top Section: Left side for search and data table, right side for view data details */}
      <div className="admin-database-database-top">
        <div className="admin-database-search-data-section">
          <div className="admin-database-search-bar">
            <input type="text" placeholder="Name: ex. CS 202, Ahmet" />
            <select value={selectedType} onChange={handleTypeChange}>
              <option value="Choose">choose</option>

              <option value="Course">Course</option>
              <option value="Instructor">Instructor</option>
            </select>

            <button className="admin-database-search-button">Search</button>
          </div>
          <div className="admin-database-data-table">
            <div className="admin-database-table-body">
              <div className="admin-database-table-row">
                {createDatabaseItems()}
              </div>
            </div>
          </div>
        </div>
        <div className="admin-database-view-data-details">
          <h3>View Data Details</h3>
          <p>Select an entry from the table to see details.</p>
        </div>
      </div>

      {/* Bottom Section: Two panels side by side */}
      <div className="admin-database-database-bottom">
        {/* Left Panel: Create New Type */}
        <div className="admin-database-create-new-type">
          <h3>Create New Data</h3>
          <div className="admin-database-type-selector">
            <label>Select Type:</label>
            <select value={selectedType} onChange={handleTypeChange}>
              <option value="">--Select Type--</option>
              <option value="Proctoring">Proctoring</option>
              <option value="TA">TA</option>
              <option value="Course">Course</option>
              <option value="Instructor">Instructor</option>
            </select>
          </div>
          {selectedType === "Proctoring" && (
            <form
              className="admin-database-type-form"
              onSubmit={async (e) => {
                e.preventDefault();

                if (
                  !newProctoringNameRef.current.value ||
                  !examDate ||
                  !startTime ||
                  !endTime ||
                  !classrooms ||
                  !selectedDepartmentId ||
                  !selectedCourseId ||
                  !newProctoringSectionNumberRef.current.value ||
                  !newProctoringTaCountRef.current.value
                ) {
                  alert("Please fill all required fields.");
                  return;
                }

                await createNewProctoring(
                  newProctoringNameRef.current.value,
                  selectedCourseId,
                  newProctoringSectionNumberRef.current.value,
                  examDate,
                  startTime,
                  endTime,
                  classrooms,
                  newProctoringTaCountRef.current.value
                );
              }}
            >
              <label>Event Name</label>
              <input
                ref={newProctoringNameRef}
                type="text"
                placeholder="e.g., Midterm Exam"
                required
              />

              <label>Date</label>
              <input
                type="date"
                value={examDate}
                onChange={(e) => setExamDate(e.target.value)}
                required
              />

              <label>Start Time</label>
              <input
                type="time"
                value={startTime}
                onChange={(e) => setStartTime(e.target.value)}
                required
              />

              <label>End Time</label>
              <input
                type="time"
                value={endTime}
                onChange={(e) => setEndTime(e.target.value)}
                required
              />

              <label>Classrooms</label>
              <input
                type="text"
                value={classrooms}
                onChange={(e) => setClassrooms(e.target.value)}
                placeholder="e.g., EE-01,EA-312"
                required
              />

              <label>Department</label>
              <select
                value={selectedDepartmentId || ""}
                onChange={async (e) => {
                  const deptId = parseInt(e.target.value);
                  setSelectedDepartmentId(deptId);

                  try {
                    const { data } = await axios.get(
                      "http://localhost:8080/course/getCoursesInDepartment",
                      { params: { departmentId: deptId } }
                    );
                    setCourses(data || []);
                  } catch (error) {
                    console.error("Error fetching courses:", error);
                  }
                }}
                required
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
                required
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
                ref={newProctoringSectionNumberRef}
                type="number"
                placeholder="e.g., 1"
                required
                min={1}
              />

              <label>TA Count</label>
              <input
                ref={newProctoringTaCountRef}
                type="number"
                placeholder="e.g., 2"
                min={1}
                required
              />

              <input
                type="submit"
                value="Create"
                className="admin-database-create-type-button"
              />
            </form>
          )}


          {selectedType === "Course" && (
            <form
              className="admin-database-type-form"
              onSubmit={(e) => {
                e.preventDefault();

                if (
                  !newCourseNameRef.current.value ||
                  !newCourseDescRef.current.value ||
                  !newCourseCodeRef.current.value ||
                  !selectedDepartmentId ||
                  !newCourseCoordinatorIdRef.current.value
                ) {
                  alert("Please fill in all required fields.");
                  return;
                }

                createNewCourse(
                  newCourseNameRef.current.value,
                  newCourseDescRef.current.value,
                  newCourseCodeRef.current.value,
                  selectedDepartmentId,
                  newCourseCoordinatorIdRef.current.value
                );
              }}
            >
              <label>Course Name</label>
              <input ref={newCourseNameRef} type="text" placeholder="e.g., Object-Oriented Programming" required />

              <label>Description</label>
              <input ref={newCourseDescRef} type="text" placeholder="e.g., Covers Java OOP concepts" required />

              <label>Course Code</label>
              <input ref={newCourseCodeRef} type="number" placeholder="e.g., 319" required />

              <label>Department</label>
              <select
                value={selectedDepartmentId || ""}
                onChange={(e) => setSelectedDepartmentId(parseInt(e.target.value))}
                required
              >
                <option value="">Select Department</option>
                {departments.map((dept) => (
                  <option key={dept.departmentId} value={dept.departmentId}>
                    {dept.departmentName}
                  </option>
                ))}
              </select>

              <label>Coordinator ID</label>
              <input ref={newCourseCoordinatorIdRef} type="number" placeholder="e.g., 1023" required />

              <input
                type="submit"
                value="Create"
                className="admin-database-create-type-button"
              />
            </form>
          )}



          {selectedType === "TA" && (
            <form className="admin-database-type-form" onSubmit={async (e) => {
              e.preventDefault();
              await createNewTa(); // no args, handled inside
            }}
            >

              <label>TA Name</label>
              <input ref={newTaNameRef} type="text" required />

              <label>Surname</label>
              <input ref={newTaSurnameRef} type="text" required />

              <label>Email</label>
              <input ref={newTaEmailRef} type="email" required />

              <label>Bilkent ID</label>
              <input ref={newTaIdRef} type="text" required />

              <label>Department</label>
              <select
                value={selectedDepartmentId || ""}
                onChange={async (e) => {
                  const deptId = parseInt(e.target.value);
                  setSelectedDepartmentId(deptId);
                  try {
                    const { data } = await axios.get("http://localhost:8080/course/getCoursesInDepartment", {
                      params: { departmentId: deptId },
                    });
                    setCourses(data || []);
                  } catch (error) {
                    console.error("Error fetching courses:", error);
                  }
                }}
                required
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
                required
              >
                <option value="">Select Course</option>
                {courses.map((course) => (
                  <option key={course.id} value={course.id}>
                    {course.name}
                  </option>
                ))}
              </select>

              <label>Phone Number</label>
              <input ref={newTaPhoneNumRef} type="text" required />

              <label>Class Year</label>
              <input ref={newTaClassYearRef} type="number" min={1} max={6} required />

              <label>Password</label>
              <input ref={newTaPasswordRef} type="password" required />

              <input type="submit" value="Create TA" className="admin-database-create-type-button" />
            </form>
          )}



          {selectedType === "Instructor" && (
            <form className="admin-database-type-form" onSubmit={(e) => {
              e.preventDefault();

              if (
                !newInstructorNameRef.current.value ||
                !newInstructorSurnameRef.current.value ||
                !newInstructorEmailRef.current.value ||
                !newInstructorBilkentIdRef.current.value ||
                !newInstructorRoleRef.current.value ||
                !selectedDepartmentId ||
                !selectedInstructorCourse ||
                !newInstructorPhoneRef.current.value ||
                !newInstructorPasswordRef.current.value
              ) {
                alert("Please fill in all required fields.");
                return;
              }

              const name = newInstructorNameRef.current.value;
              const surname = newInstructorSurnameRef.current.value;
              const email = newInstructorEmailRef.current.value;
              const bilkentId = newInstructorBilkentIdRef.current.value;
              const role = newInstructorRoleRef.current.value;
              const phoneNumber = newInstructorPhoneRef.current.value;
              const password = newInstructorPasswordRef.current.value;
              const departmentName = selectedDepartmentName;
              const departmentId = selectedDepartmentId;
              const courses = [selectedInstructorCourse]; // ensure this is valid

              console.log("Sending instructor data:", {
                name,
                surname,
                email,
                bilkentId,
                role,
                departmentName,
                departmentId,
                phoneNumber,
                password,
                courses
              });

              createInstructor(
                name,
                surname,
                email,
                bilkentId,
                role,
                departmentName,
                departmentId,
                phoneNumber,
                password,
                courses
              );


            }}
            >
              <label>Name</label>
              <input ref={newInstructorNameRef} type="text" required placeholder="e.g., Zeynep" />

              <label>Surname</label>
              <input ref={newInstructorSurnameRef} type="text" required placeholder="e.g., Aslan" />

              <label>Email</label>
              <input ref={newInstructorEmailRef} type="email" required placeholder="e.g., zeynep@bilkent.edu.tr" />

              <label>Bilkent ID</label>
              <input ref={newInstructorBilkentIdRef} type="text" required placeholder="e.g., 10001" />

              <label>Role</label>
              <input ref={newInstructorRoleRef} type="text" required placeholder="e.g., Associate Professor" />

              <label>Department</label>
              <select
                value={selectedDepartmentId || ""}
                onChange={async (e) => {
                  const deptId = parseInt(e.target.value);
                  setSelectedDepartmentId(deptId);

                  const dept = departments.find((d) => d.departmentId === deptId);
                  setSelectedDepartmentName(dept?.departmentName || "");

                  try {
                    const { data } = await axios.get(
                      "http://localhost:8080/course/getCoursesInDepartment",
                      { params: { departmentId: deptId } }
                    );
                    setCourses(data || []);
                  } catch (error) {
                    console.error("Error fetching courses:", error);
                  }
                }}
                required
              >
                <option value="">Select Department</option>
                {departments.map((dept) => (
                  <option key={dept.departmentId} value={dept.departmentId}>
                    {dept.departmentName}
                  </option>
                ))}
              </select>

              <label>Phone Number</label>
              <input ref={newInstructorPhoneRef} type="text" required placeholder="e.g., +90-555-123-45-67" />

              <label>Course</label>
              <select
                value={selectedInstructorCourse || ""}
                onChange={(e) => setSelectedInstructorCourse(e.target.value)}
                required
              >
                <option value="">Select Course</option>
                {courses.map((course) => (
                  <option key={course.id} value={course.name}>
                    {course.name}
                  </option>
                ))}
              </select>

              <label>Password</label>
              <input ref={newInstructorPasswordRef} type="password" required placeholder="Set login password" />

              <input type="submit" value="Create Instructor" className="admin-database-create-type-button" />
            </form>
          )}


        </div>

        {/* Right Panel: Dump New Data */}
        <div className="admin-database-dump-new-data">
          <h3>Dump New Data</h3>
          <div className="admin-database-upload-container">
            <div className="admin-database-drag-drop-area" style={selectedFile ? { backgroundColor: "#f0f0f0" } : {}}>
              <p>Drag &amp; Drop file here</p>
              <p>or</p>
              <label htmlFor="file-upload" className="admin-database-choose-file-label">
                Choose File
              </label>
              <input
                id="file-upload"
                type="file"
                onChange={handleFileChange}
                style={{ display: "none" }}
              />
              <p>Supported formats: Excel</p>
            </div>
            <div className="admin-database-upload-buttons">
              <button onClick={handleImportClick} className="admin-database-import-button">Import</button>
              <button className="admin-database-cancel-button" onClick={() => setSelectedFile(null)}>Cancel</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdminDatabasePage;
