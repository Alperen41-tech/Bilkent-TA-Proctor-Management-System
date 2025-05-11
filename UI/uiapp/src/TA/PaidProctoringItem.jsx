import React from "react";
import "./PaidProctoringItem.css";
/**
 * PaidProctoringItem component
 * Renders a clickable card for a paid proctoring duty, showing date, time, course, and enrollment status.
 * Allows TAs to select or apply for proctoring events.
 */


/**
 * parseSentDate
 * Converts start and end date strings into formatted date and time objects.
 */
function parseSentDate(startDateString, endDateString) {
  if (!startDateString || typeof startDateString !== 'string' || !startDateString.includes('T')) {
    return { date: "—", time: "—" };
  }


  const [datePart, timePart] = startDateString.split('T');
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

  // Format end time
  const endHourInt = parseInt(endDateString.split("T")[1].split(":")[0], 10);
  const endMinute = endDateString.split("T")[1].split(":")[1];
  const endHour = endHourInt % 12 || 12;
  const endAMPM = endHourInt >= 12 ? 'PM' : 'AM';
  const end = `${endHour}:${endMinute}${endAMPM}`;

  return {
    date: {
      month,
      day,
      weekday,
    },
    time: {
      start,
      end,
    }
  };
}
/**
 * PaidProctoringItem
 * Displays an individual proctoring task card with enrollment details and interaction options.
 */
const PaidProctoringItem = ({ task, isSelected, isEnrolled, onSelect, onEnroll }) => {
  const { date, time } = parseSentDate(task.classProctoringDTO.startDate, task.classProctoringDTO.endDate);
  
  return (
    <div
      className={`proctoring-card ${isSelected ? "selected" : ""}`}
      onClick={onSelect}
    >
      <div className="left-block">
        <div className="date">
          <div>{date.month} {date.day}</div>
          <div className="weekday">{date.weekday}</div>
        </div>
        <div className="time">{time.start} - {time.end}</div>
        <div className="title">{task.title}</div>
        <div className="course">{task.classProctoringDTO.courseName}</div>
        
        <div className="duration">{task.finishDate ? "Application will be open until: " +task.finishDate.split(" ")[0] + " " +task.finishDate.split(" ")[1].substring(0,5): null}</div>
      </div>

      <div className="right-block">
        <div className="department-label">{task.departmentName}</div>
        <div className="department-label">{task.courseFullName}</div>
        <div>{`${task.applicantCount} / ${task.applicantCountLimit} TAs`}</div>
        <div>
          {isEnrolled ? (
            <span className="status enrolled" >Enrolled</span>
          ) : (
            <button className="status apply " onClick={onEnroll}>Apply Proctoring</button>
          )}
        </div>
      </div>
    </div>
  );
};

export default PaidProctoringItem;
