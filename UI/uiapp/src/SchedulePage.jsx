// src/SchedulePage.jsx
import React, { useState } from "react";
import Navbar from "./Navbar";
import "./SchedulePage.css";
import { format, addWeeks, subWeeks, startOfWeek } from "date-fns";

const SchedulePage = () => {
  const days = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"];
  const [currentStartDate, setCurrentStartDate] = useState(startOfWeek(new Date(), { weekStartsOn: 1 }));

  const times = [
    "8:30 am", "9:30 am", "10:30 am", "11:30 am", "12:30 pm",
    "1:30 pm", "2:30 pm", "3:30 pm", "4:30 pm", "5:30 pm",
  ];

  const handleNextWeek = () => {
    setCurrentStartDate((prev) => addWeeks(prev, 1));
  };

  const handlePrevWeek = () => {
    setCurrentStartDate((prev) => subWeeks(prev, 1));
  };

  return (
    <div className="schedule-page">
      <Navbar />

      <div className="calendar-container">
        <div className="calendar-header">
          <h2>{format(currentStartDate, "'Week of' MMMM d, yyyy")}</h2>
          <div className="calendar-nav">
            <button onClick={handlePrevWeek}>&lt; Previous</button>
            <button onClick={handleNextWeek}>Next &gt;</button>
          </div>
        </div>

        <div className="calendar-grid">
          <div className="grid-header empty"></div>
          {days.map((day, idx) => (
            <div key={idx} className="grid-header">{day}</div>
          ))}

          {times.map((time, timeIdx) => (
            <React.Fragment key={timeIdx}>
              <div className="time-label">{time}</div>
              {days.map((_, dayIdx) => (
                <div key={`${timeIdx}-${dayIdx}`} className="grid-cell">
                  {/* Future: Display busy cell here */}
                </div>
              ))}
            </React.Fragment>
          ))}
        </div>
      </div>
    </div>
  );
};

export default SchedulePage;