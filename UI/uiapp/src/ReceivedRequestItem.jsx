import React from "react";
import "./ReceivedRequestItem.css";

const ReceivedRequestItem = ({ date, time, role, duration, name, email, status, onAccept, onReject }) => {
  return (
    <div className="received-request-item-received-card">
      <div className="received-request-item-date-box">
        <div className="received-request-item-month">{date.month}</div>
        <div className="received-request-item-day">{date.day}</div>
        <div className="received-request-item-weekday">{date.weekday}</div>
      </div>

      <div className="received-request-item-details">
        <div className="received-request-item-time-row">{time.start} - {time.end}</div>
        <div className="received-request-item-info-row">{role} • {duration} hours</div>
        <div className="received-request-item-info-row">Sender: {name}</div>
        <div className="received-request-item-info-row received-request-item-email">{email}</div>
      </div>

      <div className="received-request-item-status-actions">
        <div className="received-request-item-status-text">Request Status:</div>
        <div className="received-request-item-status-label">{status}</div>
        <div className="received-request-item-button-group">
          <button className="received-request-item-accept-button" onClick={onAccept}>Accept Request</button>
          <button className="received-request-item-reject-button" onClick={onReject}>Reject Request</button>
        </div>
      </div>
    </div>
  );
};

export default ReceivedRequestItem;
