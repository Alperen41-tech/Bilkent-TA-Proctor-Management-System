// AdminDatabaseItem.jsx
import React from 'react';
import './AdminDatabaseItem.css';

const AdminDatabaseItem = ({ type, data, onDelete, isSelected, onSelect, inLog }) => {
  console.log("Course data passed to AdminDatabaseItem:", data);

  const renderDetails = () => {
    switch (type) {
      case 'ta2':
        return (
          <>
            <div>{data.name}</div>
            <div>{data.email}</div>
            <div>{data.department}</div>
          </>
        );
      case 'instructor2':
        return (
          <>
            <div>{data.name}</div>
            <div>{data.email}</div>
            <div>{data.title}</div>
          </>
        );
      case 'exam':
        return (
          <>
            <div><strong>{data.course}</strong> {data.examType && <em>({data.examType})</em>}</div>
            <div>{data.date} | {data.time} - {data.endTime}</div>
            <div>Classroom: {data.location}</div>
            <div>Section: {data.Section ?? '—'}</div>
          </>
        );

      case 'course2':
        return (
          <>
            <div>{data.code}</div>
            <div>{data.name}</div>
            <div>{data.instructor}</div>
          </>
        );
      case 'class':
        return (
          <>
            <div>{data.room}</div>
            <div>{data.capacity} students</div>
          </>
        );
      case 'student':
        return (
          <>
            <div>{data.name}</div>
            <div>{data.email}</div>
            <div>{data.studentId}</div>
          </>
        );

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
