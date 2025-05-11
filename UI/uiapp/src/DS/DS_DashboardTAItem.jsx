// DS_DashboardTAItem.jsx
import React from 'react';
import './DS_DashboardTAItem.css';


/**
 * DS_DashboardTAItem component
 * Represents a TA in the Dean’s Secretary dashboard for selection and overview.
 */

const DS_DashboardTAItem = ({ name, surname, id, bgColor, onSelect, isSelected, email, paidProctoringCount, taType }) => {
  return (
    <div className={`ds-dashboard-ta-item ${isSelected ? "ds-dashboard-ta-item-selected" : ""} `}  style={{ backgroundColor: bgColor }} onClick={() => onSelect(id)} >
        <div className="ds-dashboard-ta-item-content">
            <div className="ds-dashboard-ta-item-name">Name: {name} {surname}</div>
            <div className="ds-dashboard-ta-item-email">{email}</div>
            <div className="ds-dashboard-ta-item-id">Student ID: {id}</div>
            <div className="ds-dashboard-ta-item-paid-proctoring-count">PP Count: {paidProctoringCount}</div>
            <div className='ds-dashboard-ta-item-id'>TA Type: {taType} </div>
        </div>
    </div>
  );
};

export default DS_DashboardTAItem;
