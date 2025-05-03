import React from "react";
import "./DS_SelectPaidProctoringTAItem.css";

const DS_SelectPaidProctoringTAItem = ({id, date, time, role, duration, name, numOfTaNeeded, taSApplied, isSelected, onSelect, isForcedAssignment, isAppliedAssignment, onForcedAssignment, onAppliedAssignment}) => {

  return (
    <div className={`ds-select-paid-proctoring-ta-item-request-card ${isSelected ? 'ds-select-paid-proctoring-ta-item-selected':''}`} onClick={() => onSelect(id)}>
      <div className="ds-select-paid-proctoring-ta-item-date-box">
        <div className="ds-select-paid-proctoring-ta-item-month">{date.month}</div>
        <div className="ds-select-paid-proctoring-ta-item-day">{date.day}</div>
        <div className="ds-select-paid-proctoring-ta-item-weekday">{date.weekday}</div>
      </div>
      <div className="ds-select-paid-proctoring-ta-item-details">
        <div className="ds-select-paid-proctoring-ta-item-time-row">{time.start} - {time.end}</div>
        <div className="ds-select-paid-proctoring-ta-item-info-row">{role} • {duration} hours</div>
        <div className="ds-select-paid-proctoring-ta-item-info-row">{name}</div>
        <div className="ds-select-paid-proctoring-ta-item-info-row">{taSApplied}/{numOfTaNeeded} TAs Applied</div>
      </div>
      <div className="ds-select-paid-proctoring-ta-item-buttons">
        {(!isForcedAssignment && !isAppliedAssignment) ? <div className="ds-select-paid-proctoring-ta-item-button"><button className="ds-select-paid-proctoring-ta-item-applied-button" onClick={()=>onAppliedAssignment()}>Applied Assignment</button><button className="ds-select-paid-proctoring-ta-item-manual-select-button" onClick={()=>onForcedAssignment()}>Manual Assignment</button></div> : null}
      </div>
    </div>
  );
};

export default DS_SelectPaidProctoringTAItem;
