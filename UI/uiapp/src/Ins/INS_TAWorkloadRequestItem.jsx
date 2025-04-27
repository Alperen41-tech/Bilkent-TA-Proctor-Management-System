import React from "react";
import "./INS_TAWorkloadRequestItem.css";

const INS_TAWorkloadRequestItem = ({taskTypeName, timeSpent, courseCode, description, sentDate, status, taName, taMail, onAccept, onReject }) => {
    function parseSentDate(sentDateString) {
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
    const { date, time } = parseSentDate(sentDate);
    
    return (



    <div className="ins-ta-workload-item-received-card">
      <div className="ins-ta-workload-item-date-box">
        <div className="ins-ta-workload-item-month">{date.month}</div>
        <div className="ins-ta-workload-item-day">{date.day}</div>
        <div className="ins-ta-workload-item-weekday">{date.weekday}</div>
      </div>

      <div className="ins-ta-workload-item-details">
        <div className="ins-ta-workload-item-time-row">{time.start}</div>
        <div className="ins-ta-workload-item-info-row">{taskTypeName} • {Math.floor(parseInt(timeSpent, 10) / 60)} Hours {parseInt(timeSpent,10)%60} Min</div>
        <div className="ins-ta-workload-item-info-row">Sender: {taName}</div>
        <div className="ins-ta-workload-item-info-row ins-ta-workload-item-email">{taMail}</div>
      </div>

      <div className="ins-ta-workload-item-status-actions">
        <div className="ins-ta-workload-item-status-text">Request Status:</div>
        <div className="ins-ta-workload-item-status-label">Waiting for Response</div>
        <div className="ins-ta-workload-item-button-group">
          <button className="ins-ta-workload-item-accept-button" onClick={onAccept}>Accept Request</button>
          <button className="ins-ta-workload-item-reject-button" onClick={onReject}>Reject Request</button>
        </div>
      </div>
    </div>
  );
};

export default INS_TAWorkloadRequestItem;
