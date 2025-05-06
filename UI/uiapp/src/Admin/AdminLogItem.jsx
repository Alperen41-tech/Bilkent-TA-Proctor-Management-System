import "./AdminLogItem.css";
import React from "react";


  // AdminLogItem component to display individual log items
  // This component receives logType, message, and logDate as props
  // and displays them in a structured format.

const AdminLogItem = ({ logType, message, logDate }) => {
    return (
      <div className="log-item">
        <p><strong>{logType}</strong></p>
        <p>{message}</p>
        <p className="log-date">{logDate ? logDate.split("T")[0] + " " + logDate.split("T")[1] : ""}</p>
      </div>
    );
  };
export default AdminLogItem;