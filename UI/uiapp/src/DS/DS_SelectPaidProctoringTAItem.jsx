import React from "react";
import "./DS_SelectPaidProctoringTAItem.css";


/**
 * Parses and formats the given start and end datetime strings into display-friendly date and time.
 * Returns an object with:
 * - date: { month, day, weekday }
 * - time: { start, end }
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
  //const end = endDateString.split("T")[1].substring(0,5) + "PM"; // Assuming end time is the same as start time for now

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
 * DS_SelectPaidProctoringTAItem component
 * Renders a paid proctoring request card allowing the DS to choose
 * between manual or application-based TA assignment workflows.
 *
 */

const DS_SelectPaidProctoringTAItem = ({paidProctoring ,isSelected, onSelect, isForcedAssignment, isAppliedAssignment, onForcedAssignment, onAppliedAssignment}) => {
  const { date, time } = parseSentDate(paidProctoring.classProctoringDTO.startDate, paidProctoring.classProctoringDTO.endDate);
  const finishDate = new Date(paidProctoring.finishDate);
  const now = new Date();
  const showAppliedAssignmentButton = finishDate.getTime() - now.getTime() > 24 * 60 * 60 * 1000;

  return (
    <div className={`ds-select-paid-proctoring-ta-item-request-card ${isSelected ? 'ds-select-paid-proctoring-ta-item-selected':''}`} onClick={() => onSelect(paidProctoring.applicationId)}>
      <div className="ds-select-paid-proctoring-ta-item-date-box">
        <div className="ds-select-paid-proctoring-ta-item-month">{date.month}</div>
        <div className="ds-select-paid-proctoring-ta-item-day">{date.day}</div>
        <div className="ds-select-paid-proctoring-ta-item-weekday">{date.weekday}</div>
      </div>
      <div className="ds-select-paid-proctoring-ta-item-details">
        <div className="ds-select-paid-proctoring-ta-item-time-row">{time.start} - {time.end}</div>
        <div className="ds-select-paid-proctoring-ta-item-info-row">{paidProctoring.classProctoringDTO.courseName}</div>
        {isAppliedAssignment ? <div className="ds-select-paid-proctoring-ta-item-info-row">{paidProctoring.applicantCount}/{paidProctoring.applicantCountLimit} TAs Applied </div> : <div className="ds-select-paid-proctoring-ta-item-info-row">{paidProctoring.applicantCountLimit} TAs needed</div>}

      </div>
      <div className="ds-select-paid-proctoring-ta-item-buttons">
        {(!isForcedAssignment && !isAppliedAssignment) ? (
          <div className="ds-select-paid-proctoring-ta-item-button">
            {showAppliedAssignmentButton && (
              <button
                className="ds-select-paid-proctoring-ta-item-applied-button"
                onClick={() => onAppliedAssignment()}
              >
                Applied Assignment
              </button>
            )}
            <button
              className="ds-select-paid-proctoring-ta-item-manual-select-button"
              onClick={() => onForcedAssignment()}
            >
              Manual Assignment
            </button>
          </div>
        ) : isForcedAssignment ? (
          <div className="ds-select-paid-proctoring-ta-item-info-row">Manual Assignment Selected</div>
        ) : (
          <div className="ds-select-paid-proctoring-ta-item-info-row">
            Applied Assignment Selected
            <div>Last application date: {paidProctoring.finishDate.substring(0, 16)}</div>
          </div>
        )}
      </div>

    </div>
  );
};

export default DS_SelectPaidProctoringTAItem;
