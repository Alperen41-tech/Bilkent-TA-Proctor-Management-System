import React from "react";
import "./DS_PaidProctoringRequestItem.css";


/**
 * DS_PaidProctoringRequestItem component
 * Represents a single paid proctoring request card for the Dean's Secretary.
 *
 */

const DS_PaidProctoringRequestItem = ({id, date, time, role, duration, name, numOfTaNeeded, onInform, isSelected, onSelect}) => {

  return (
    <div className={`ds-paid-proctoring-request-item-request-card ${isSelected ? 'ds-paid-proctoring-request-item-selected':''}`} onClick={() => onSelect(id)}>
      <div className="ds-paid-proctoring-request-item-date-box">
        <div className="ds-paid-proctoring-request-item-month">{date.month}</div>
        <div className="ds-paid-proctoring-request-item-day">{date.day}</div>
        <div className="ds-paid-proctoring-request-item-weekday">{date.weekday}</div>
      </div>
      <div className="ds-paid-proctoring-request-item-details">
        <div className="ds-paid-proctoring-request-item-time-row">{time.start} - {time.end}</div>
        <div className="ds-paid-proctoring-request-item-info-row">{role} • {duration} hours</div>
        <div className="ds-paid-proctoring-request-item-info-row">{name}</div>
        <div className="ds-paid-proctoring-request-item-info-row">{numOfTaNeeded} TAs needed</div>
      </div>
      <div className="ds-paid-proctoring-request-item-status-box">
        <button className="ds-paid-proctoring-request-item-cancel-button" onClick={onInform}>Infrom TAs</button>
      </div>
    </div>
  );
};

export default DS_PaidProctoringRequestItem;
