// src/TAItem.jsx
import React from "react";
import "./TAItem.css";

const TAItem = ({ ta, onClick, isSelected }) => {
  const handleClick = () => {
    if (onClick) {
      onClick(ta);
    }
  };
  return (

        <div className={`ta-item ${isSelected ? "selected" : ""}`} onClick={handleClick}>
            <div><strong>{ta.name} {ta.surname}</strong></div>
            <div style={{ fontSize: "0.9em", color: "#555" }}>{ta.email}</div>
        </div>

  );
};

export default TAItem;
