import React from 'react';
import './NotificationItem.css';

function getIcon(notificationType) {
  switch (notificationType) {
    case 'REQUEST':
      return '📥';
    case 'APPROVAL':
      return '✅';
    case 'ASSIGNMENT':
      return '📝';
    default:
      return '🔔';
  }
}

function NotificationItem({ requestType, message, date, notificationType }) {
  return (
    <div className="notification-item">
      <div className="icon">{getIcon(notificationType)}</div>
      <div className="content">
        <div className="title">{requestType}</div>
        <div className="message">{message}</div>
        <div className="time">{date}</div>
      </div>
    </div>
  );
}

export default NotificationItem;
