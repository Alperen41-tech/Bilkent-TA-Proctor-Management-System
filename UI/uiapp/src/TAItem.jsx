// src/TAItem.jsx
import React from "react";
import "./TAItem.css";

const TAItem = ({ ta, onClick, isSelected, inInstructor}) => {
  const handleClick = () => {
    if (onClick) {
      onClick(ta);
    }
  };
  return (

        <div className={`ta-item ${isSelected ? "selected" : ""}`} onClick={handleClick}>
            <div className="ta-item-data-area"><strong>{ta.name} {ta.surname}</strong></div>
            <div className="ta-item-data-area" style={{ fontSize: "0.9em", color: "#555" }}>{ta.email}</div>
            <div className="ta-item-data-area" style={{ fontSize: "0.9em", color: "#555" }}>{inInstructor ? ta.departmentCode: ""}</div>
            <div className="ta-item-data-area" style={{ fontSize: "0.9em", color: "#555" }}>{inInstructor ? ta.bilkentId: ""}</div>
        </div>

  );
};

export default TAItem;
