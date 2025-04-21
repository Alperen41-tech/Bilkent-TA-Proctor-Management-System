// AdminDatabaseItem.jsx
import React from 'react';
import './AdminDatabaseItem.css';

const AdminDatabaseItem = ({ type, data, onDelete, isSelected, onSelect, inLog }) => {
  const renderDetails = () => {
    switch (type) {
      case 'ta':
        return (
          <>
            <div>{data.name}</div>
            <div>{data.email}</div>
            <div>{data.department}</div>
          </>
        );
      case 'instructor':
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
            <div>{data.course}</div>
            <div>{data.date} {data.time}</div>
            <div>{data.location}</div>
          </>
        );
      case 'course':
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
        🗑️
      </div>)}
      
    </div>
  );
};

export default AdminDatabaseItem;
