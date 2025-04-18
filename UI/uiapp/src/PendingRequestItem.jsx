import React from "react";
import "./PendingRequestItem.css";

const PendingRequestItem = ({ date, time, role, duration, name, email, status, onCancel }) => {
  return (
    <div className="pending-request-item-request-card">
      <div className="pending-request-item-date-box">
        <div className="pending-request-item-month">{date.month}</div>
        <div className="pending-request-item-day">{date.day}</div>
        <div className="pending-request-item-weekday">{date.weekday}</div>
      </div>
      <div className="pending-request-item-details">
        <div className="pending-request-item-time-row">{time.start} - {time.end}</div>
        <div className="pending-request-item-info-row">{role} • {duration} hours</div>
        <div className="pending-request-item-info-row">Sent to: {name}</div>
        <div className="pending-request-item-info-row pending-request-item-email">{email}</div>
      </div>
      <div className="pending-request-item-status-box">
        <div className="pending-request-item-status-text">{status}</div>
        <button className="pending-request-item-cancel-button" onClick={onCancel}>Cancel Request</button>
      </div>
    </div>
  );
};

export default PendingRequestItem;
