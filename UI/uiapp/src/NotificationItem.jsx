import React from 'react';
import './NotificationItem.css';


/**
 * NotificationItem Component
 * Renders a styled notification entry showing the type of request, message, timestamp, and icon.
 */

/**
 * Returns an emoji/icon representing the notification category.
 */
function getIcon(notificationType) {
  switch (notificationType) {
    case 'REQUEST':
      return '📥';
    case 'APPROVAL':
      return '✅';
    case 'REJECTION':
      return '❌';
    case 'ASSIGNMENT':
      return '📝';
    default:
      return '🔔';
  }
}


/**
 * Maps backend request types to user-friendly labels.
 */
function getNotificationType(notificationType) {
  switch (notificationType) {
    case 'TAWorkloadRequest':
      return 'TA Workload Request';
    case 'TASwapRequest':
      return 'TA Swap Request';
    case 'TALeaveRequest':
      return 'TA Leave Request';
    case 'InstructorAdditionalTARequest':
      return 'Additional TA Request';
    case 'AuthStaffProctoringRequest':
      return 'Proctoring Request';
    default:
      return 'General Notification';
  }
}


/**
 * Main component to render a notification item with its icon, label, message, and date.
 */
function NotificationItem({ requestType, message, date, notificationType }) {
  return (
    <div className="notification-item">
      <div className="icon">{getIcon(notificationType)}</div>
      <div className="content">
        <div className="title">{getNotificationType(requestType)}</div>
        <div className="message">{message}</div>
        <div className="time">{date ? date.split("T")[0]+ " " + date.split("T")[1].substring(0,5) : null}</div>
      </div>
    </div>
  );
}

export default NotificationItem;
