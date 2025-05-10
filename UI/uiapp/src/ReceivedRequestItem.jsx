import React from "react";
import "./ReceivedRequestItem.css";
/**
 * ReceivedRequestItem Component
 * Renders a single received request card for various request types like proctoring, workload, swap, etc.
 * Displays metadata such as sender, type, and status with action buttons for accept/reject.
 */

/**
 * Parses a date string in ISO format to extract formatted components for display.
 */
function parseSentDate(sentDateString) {
  if (!sentDateString || typeof sentDateString !== 'string' || !sentDateString.includes('T')) {
    return { date: "—", time: "—" };
  }


  const [datePart, timePart] = sentDateString.split('T');
  const [year, monthNum, day] = datePart.split('-'); 
  const [hour, minute, second] = timePart.split(':');

  const months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", 
                  "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

  const weekdays = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
  
  // Create a date object ONLY to find the weekday (you can't guess weekday without date)
  const tempDate = new Date(`${year}-${monthNum}-${day}`);
  const weekday = weekdays[tempDate.getDay()];

  const month = months[parseInt(monthNum, 10) - 1];

  // Format time as AM/PM
  const hourInt = parseInt(hour, 10);
  const ampm = hourInt >= 12 ? 'PM' : 'AM';
  const adjustedHour = hourInt % 12 || 12;

  const start = `${adjustedHour}:${minute}${ampm}`;

  return {
    date: {
      month,
      day,
      weekday,
    },
    time: {
      start,
    }
  };
}

/**
 * Component to render details and actions for a received request.
 */
const ReceivedRequestItem = ({requestType, sentDateTime, isApproved, responseDateTime, description, senderName, receiverName, receiverEmail, senderEmail, status, classProctoringEventName, classProctoringStartDate, classProctoringEndDate, taCountNeeded, isComplete, isUrgent, leaveStartDate, leaveEndDate, taskTypeName, timeSpent, courseCode, onAccept, onReject, isSelected}) => {
  const { date, time } = parseSentDate(sentDateTime);


    /**
   * Renders context-specific information depending on request type.
   */
  const renderDetails = () => {
    switch (requestType) {
      case 'AuthStaffProctoringRequest':
        return (
           <>
            <div className="pending-request-item-time-row">Proctoring Request:</div>
            <div className="pending-request-item-info-row">Proctoring Event: {classProctoringEventName}</div>
            <div>Start: {classProctoringStartDate ? classProctoringStartDate.split("T")[0] : "—"}</div>
            <div>End: {classProctoringEndDate ? classProctoringEndDate.split("T")[0] : "—"}</div>
            <div className="pending-request-item-info-row">Sender: {senderName}</div>
            <div className="pending-request-item-info-row pending-request-item-email">{senderEmail}</div>
          </>
        );
  
      case 'InstructorAdditionalTARequest':
        return (
          <>
            <div className="pending-request-item-time-row">Additional TA Request:</div>
            <div className="pending-request-item-info-row">Course: {courseCode}</div>
            <div className="pending-request-item-info-row">Proctoring Event: {classProctoringEventName}</div>
            <div className="pending-request-item-info-row">TA Count Needed: {taCountNeeded}</div>
            <div className="pending-request-item-info-row">Sender: {senderName}</div>
            <div className="pending-request-item-info-row pending-request-item-email">{senderEmail}</div>
          </>
        );
  
      case 'TALeaveRequest':
        return (
          <>
            <div className="pending-request-item-time-row">TA Leave Request:</div>
            <div className="pending-request-item-info-row">Leave Period: {leaveStartDate ? leaveStartDate.split("T")[0] + " " + leaveStartDate.split("T")[1].substring(0,5) : null} - {leaveEndDate ? leaveEndDate.split("T")[0] + " " + leaveEndDate.split("T")[1].substring(0,5) : null}</div>
            <div className="pending-request-item-info-row">Urgent: {isUrgent ? "Yes" : "No"}</div>
            <div className="pending-request-item-info-row">Sender: {senderName}</div>
            <div className="pending-request-item-info-row pending-request-item-email">{senderEmail}</div>
          </>
        );
  
      case 'TASwapRequest':
        return (
           <>
            <div className="pending-request-item-time-row">TA Swap Request:</div>
            <div className="pending-request-item-info-row">Proctoring Event: {classProctoringEventName}</div>
            <div className="pending-request-item-info-row">Start: {classProctoringStartDate ? classProctoringStartDate.split("T")[0] : "—"}</div>
            <div className="pending-request-item-info-row">End: {classProctoringEndDate ? classProctoringEndDate.split("T")[0] : "—"}</div>
            <div className="pending-request-item-info-row">Sender: {senderName}</div>
            <div className="pending-request-item-info-row pending-request-item-email">{senderEmail}</div>
          </>
        );
  
      case 'TAWorkloadRequest':
        return (
          <>
            <div className="pending-request-item-time-row">TA Workload Request:</div>
            <div className="pending-request-item-info-row">Start Time: {time.start}</div>
            <div className="pending-request-item-info-row">{taskTypeName} • {Math.floor(parseInt(timeSpent, 10) / 60)} Hours {parseInt(timeSpent,10)%60} Min</div>
            <div className="pending-request-item-info-row">Course: {courseCode}</div>
            <div className="pending-request-item-info-row">Sender: {senderName}</div>
            <div className="pending-request-item-info-row pending-request-item-email">{senderEmail}</div>
          </>
        );
  
      default:
        return <div className="received-request-item-time-row">Unknown request type</div>;
    }
  };
  
  
  
  
  return (
    <div className="received-request-item-received-card" style={{ backgroundColor: isSelected ? "#e2d9fe" : "#ececec" }}>
      <div className="received-request-item-date-box">
        <div className="received-request-item-month">{date.month}</div>
        <div className="received-request-item-day">{date.day}</div>
        <div className="received-request-item-weekday">{date.weekday}</div>
      </div>

      <div className="received-request-item-details">
        {renderDetails()}
      </div>

      <div className="received-request-item-status-actions">
        <div className="received-request-item-status-text">Request Status:</div>
        <div className="received-request-item-status-label">Waiting For Response</div>
        <div className="received-request-item-button-group">
          <button className="received-request-item-accept-button" onClick={onAccept}>Accept Request</button>
          <button className="received-request-item-reject-button" onClick={onReject}>Reject Request</button>
        </div>
      </div>
    </div>
  );
};

export default ReceivedRequestItem;
