import React from "react";
import "./ReceivedRequestItem.css";

function parseSentDate(sentDateString) {
  const [datePart, timePart] = sentDateString.split(' ');
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

const ReceivedRequestItem = ({requestType, sentDateTime, isApproved, responseDateTime, description, senderName, receiverName, receiverEmail, senderEmail, status, classProctoringEventName, proctoringStartDate, proctoringEndDate, taCountNeeded, isComplete, isUrgent, leaveStartDate, leaveEndDate, taskTypeName, timeSpent, courseCode, onAccept, onReject}) => {
  const { date, time } = parseSentDate(sentDateTime);

  const renderDetails = () => {
    switch (requestType) {
      case 'authStaffProctoringRequest':
        return (
          <>
            <div className="received-request-item-info-row">Proctoring Event: {classProctoringEventName}</div>
            <div className="received-request-item-time-row">Start: {proctoringStartDate}</div>
            <div className="received-request-item-time-row">End: {proctoringEndDate}</div>
            <div className="received-request-item-info-row">Sender: {senderName}</div>
            <div className="received-request-item-info-row received-request-item-email">{senderEmail}</div>
            <div className="received-request-item-info-row">Comment: {description}</div>
          </>
        );
  
      case 'instructorAdditionalTARequest':
        return (
          <>
            <div className="received-request-item-info-row">Proctoring Event: {classProctoringEventName}</div>
            <div className="received-request-item-info-row">TA Count Needed: {taCountNeeded}</div>
            <div className="received-request-item-info-row">Completed: {isComplete ? "Yes" : "No"}</div>
            <div className="received-request-item-info-row">Sender: {senderName}</div>
            <div className="received-request-item-info-row received-request-item-email">{senderEmail}</div>
            <div className="received-request-item-info-row">Comment: {description}</div>
          </>
        );
  
      case 'taLeaveRequest':
        return (
          <>
            <div className="received-request-item-info-row">Leave Period: {leaveStartDate} - {leaveEndDate}</div>
            <div className="received-request-item-info-row">Urgent: {isUrgent ? "Yes" : "No"}</div>
            <div className="received-request-item-info-row">Sender: {senderName}</div>
            <div className="received-request-item-info-row received-request-item-email">{senderEmail}</div>
            <div className="received-request-item-info-row">Comment: {description}</div>
          </>
        );
  
      case 'taSwapRequest':
        return (
          <>
            <div className="received-request-item-info-row">Proctoring Event: {classProctoringEventName}</div>
            <div className="received-request-item-time-row">Start: {proctoringStartDate}</div>
            <div className="received-request-item-time-row">End: {proctoringEndDate}</div>
            <div className="received-request-item-info-row">Sender: {senderName}</div>
            <div className="received-request-item-info-row received-request-item-email">{senderEmail}</div>
            <div className="received-request-item-info-row">Comment: {description}</div>
          </>
        );
  
      case 'taWorkloadRequest':
        return (
          <>
            <div className="received-request-item-time-row">{time.start}</div>
            <div className="received-request-item-info-row">{taskTypeName} • {Math.floor(parseInt(timeSpent, 10) / 60)} Hours {parseInt(timeSpent,10)%60} Min</div>
            <div className="received-request-item-info-row">Course: {courseCode}</div>
            <div className="received-request-item-info-row">Sender: {senderName}</div>
            <div className="received-request-item-info-row received-request-item-email">{senderEmail}</div>
            <div className="received-request-item-info-row">Comment: {description}</div>
          </>
        );
  
      default:
        return <div className="received-request-item-time-row">Unknown request type</div>;
    }
  };
  
  
  
  
  return (
    <div className="received-request-item-received-card">
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
