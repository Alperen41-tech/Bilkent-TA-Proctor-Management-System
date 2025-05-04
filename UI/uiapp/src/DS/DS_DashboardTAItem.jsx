// DS_DashboardTAItem.jsx
import React from 'react';
import './DS_DashboardTAItem.css';

const DS_DashboardTAItem = ({ name, surname, id, bgColor, onSelect, isSelected, email }) => {
  return (
    <div className={`ds-dashboard-ta-item ${isSelected ? "ds-dashboard-ta-item-selected" : ""} `}  style={{ backgroundColor: bgColor }} onClick={() => onSelect(id)} >
        <div className="ds-dashboard-ta-item-content">
            <div className="ds-dashboard-ta-item-name">Name: {name} {surname}</div>
            <div className="ds-dashboard-ta-item-email">{email}</div>
            <div className="ds-dashboard-ta-item-id">Student ID: {id}</div>
        </div>
    </div>
  );
};

export default DS_DashboardTAItem;
