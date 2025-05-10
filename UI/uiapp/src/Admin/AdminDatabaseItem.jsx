// AdminDatabaseItem.jsx
import React from 'react';
import './AdminDatabaseItem.css';


/**
 * AdminDatabaseItem component
 * Renders a single database item for admin view based on type (TA, instructor, course, etc.).
 *
 * Props:
 * - type: string indicating the type of item
 * - data: object containing the item’s data
 * - onDelete: function to call when delete is triggered
 * - isSelected: whether the item is currently selected
 * - onSelect: function to handle selection
 * - inLog: whether the item is being shown in a log context (disables delete)
 */


const AdminDatabaseItem = ({ type, data, onDelete, isSelected, onSelect, inLog }) => {
  console.log("Course data passed to AdminDatabaseItem:", data);
  // render item specific details
  const renderDetails = () => {
    // TA (v2): Shows name, email, and department
    switch (type) {
      case 'ta2':
        return (
          <>
            <div>{data.name}</div>
            <div>{data.email}</div>
            <div>{data.department}</div>
          </>
        );
      // Instructor (v2): Shows name, email, and academic title
      case 'instructor2':
        return (
          <>
            <div>{data.name}</div>
            <div>{data.email}</div>
            <div>{data.title}</div>
          </>
        );
      // Exam: Shows course, type, date/time, room, and TA count
      case 'exam':
        return (
          <>
            <div><strong>{data.course}</strong> {data.examType && <em>({data.examType})</em>}</div>
            <div>{data.date} | {data.time} - {data.endTime}</div>
            <div>Classroom: {data.location}</div>
            <div>TA Count: {data.tacount ?? '—'}</div>
          </>
        );
      // Course (v2): Shows course code, name, and instructor
      case 'course2':
        return (
          <>
            <div>{data.code}</div>
            <div>{data.name}</div>
            <div>{data.instructor}</div>
          </>
        );
      // Class: Shows room and student capacity
      case 'class':
        return (
          <>
            <div>{data.room}</div>
            <div>{data.capacity} students</div>
          </>
        );
      // Student: Shows name, email, and student ID
      case 'student':
        return (
          <>
            <div>{data.name}</div>
            <div>{data.email}</div>
            <div>{data.studentId}</div>
          </>
        );
      // Instructor (detailed): Shows full profile including department, contact, and courses
      case 'instructor':
        return (
          <>
            <div><strong>{data.name} {data.surname}</strong></div>
            <div>{data.email}</div>
            <div><em>{data.role}</em></div>
            <div>Dept: {data.departmentName}</div>
            <div>Phone: {data.phoneNumber}</div>
            <div>Courses: {data.courses?.join(', ') || 'None'}</div>
          </>
        );

      // Course (detailed): Shows course code, name, description, and department
      case 'course':
        return (
          <>
            <div className="course-title">
              <strong>{data.courseCode}</strong> — {data.name || "Unnamed Course"}
            </div>
            <div className="course-description">
              {data.description?.trim() ? data.description : "No description available."}
            </div>
            <div className="course-meta">
              <small>
                <strong>Department:</strong>{" "}
                {data.departmentCode || `ID ${data.departmentId}` || "Unknown"}
              </small>
            </div>
          </>
        );

      // TA (detailed): Shows full TA profile including ID, phone, department, and workload
      case 'ta':
        return (
          <>
            <div className="course-title">
              <strong>{data.name} {data.surname}</strong> — {data.email}
            </div>
            <div className="course-meta">
              <div style={{ display: 'flex', gap: '1rem' }}>
                <small><strong>Bilkent ID:</strong> {data.bilkentId}</small>
                <small><strong>Phone:</strong> {data.phoneNumber}</small>
              </div>
              <small><strong>Department:</strong> {data.departmentName}</small><br />
              <small><strong>Course:</strong> {data.courseName} ({data.courseCode})</small><br />
              <small><strong>Workload:</strong> {data.workload}</small><br />
            </div>

          </>
        );




      default:
        return <div>Unknown type</div>;
    }
  };

  return (
    <div className={`admin-db-item ${isSelected ? 'admin-db-item-selected' : ''}`} onClick={() => onSelect(data)}>
      <div className="admin-db-details">
        {renderDetails()}
      </div>
      {!inLog && (<div className="admin-db-delete" onClick={() => onDelete(data.id)}>

      </div>)}

    </div>
  );
};

export default AdminDatabaseItem;
