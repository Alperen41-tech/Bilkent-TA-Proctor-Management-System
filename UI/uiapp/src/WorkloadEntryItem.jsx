import React from "react";
import './WorkloadEntryItem.css'; // Make sure to import your CSS file


/**
 * WorkloadEntryItem Component
 * Displays a summarized view of a TA workload request including task type, course, date, duration, comment, and status.
 */

/**
 * Returns a color code depending on the workload request status.
 */
const getStatusColor = (status) => {
  switch (status) {
    case "REJECTED":
      return "#f8d7da"; // red-ish
    case null:
      return "#fff3cd"; // yellow-ish
    case "APPROVED":
      return "#c7f5d5"; // green-ish
    default:
      return "#e2e3e5"; // neutral gray
  }
};

const WorkloadEntryItem = ({ taskTitle, courseCode, date, duration, comment, status}) => {
  const backgroundColor = getStatusColor(status);

  return (
    <div className="workload-entry-item-workload-entry" style={{ backgroundColor }}>
      
      <span className="workload-entry-item-entry-item">{taskTitle}</span>
      <span className="workload-entry-item-entry-item">{courseCode}</span>
      <span className="workload-entry-item-entry-item">{date ? date.split("T")[0] + " " + date.split("T")[1].substring(0,5) : null}</span>
      <span className="workload-entry-item-entry-item">{Math.floor(parseInt(duration, 10) / 60)} Hours {parseInt(duration,10)%60} Min</span>
      <span className="workload-entry-item-entry-item">{comment.length != 0 ? comment : "-"}</span>
    </div>
  );
};

export default WorkloadEntryItem;
