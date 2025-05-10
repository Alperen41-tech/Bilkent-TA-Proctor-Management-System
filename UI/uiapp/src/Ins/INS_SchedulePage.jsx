// src/SchedulePage.jsx
import React, { useState, useRef, useEffect } from "react";
import NavbarINS from "./NavbarINS";
import "./INS_SchedulePage.css";
import { format, addWeeks, subWeeks, startOfWeek, addDays } from "date-fns";
import axios from "axios";
/**
 * INS_SchedulePage component
 * Displays the instructor's weekly schedule in a grid calendar format.
 * Allows navigation between weeks and shows events like lectures, proctoring, exams, or leaves.
 */

const INS_SchedulePage = () => {
  const days = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"];
  const [currentStartDate, setCurrentStartDate] = useState(startOfWeek(new Date(), { weekStartsOn: 1 }));
  const [currentEndDate, setCurrentEndDate] = useState(addDays(addWeeks(currentStartDate, 1), -1));
  const [insScheduleTimes, setInsScheduleTimes] = useState([]);
  const [cellHeight, setCellHeight] = useState(0);
  const cellRef = useRef(null);
  const latestRequest = useRef(0);

  const times = [
    "8:00 am", "9:00 am", "10:00 am", "11:00 am", "12:00 pm",
    "1:00 pm", "2:00 pm", "3:00 pm", "4:00 pm", "5:00 pm",
    "6:00 pm", "7:00 pm", "8:00 pm", "9:00 pm", "10:00 pm"
  ];
  /**
   * Moves the calendar view to the next week and fetches the updated schedule.
   */
  const handleNextWeek = () => {
    setCurrentStartDate(prev => addWeeks(prev, 1));
    setCurrentEndDate(prev => addWeeks(prev, 1));
    fetchScheduleInformation();
  };
/**
 * Moves the calendar view to the previous week and fetches the updated schedule.
 */
  const handlePrevWeek = () => {
    setCurrentStartDate(prev => subWeeks(prev, 1));
    setCurrentEndDate(prev => subWeeks(prev, 1));
    fetchScheduleInformation();
  };
/**
 * Fetches the instructor's schedule from the backend for the current week.
 * Uses a requestId to prevent race conditions when switching weeks quickly.
 */
  const fetchScheduleInformation = async () => {
    const requestId = ++latestRequest.current;
    const token = localStorage.getItem("token");
    try {
      const response = await axios.post("http://localhost:8080/timeInterval/instructorSchedule", {
        startDate: format(currentStartDate, "yyyy-MM-dd"),
        endDate: format(currentEndDate, "yyyy-MM-dd"),
      }, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      console.log("Fetched schedule data:", response.data);

      if (requestId === latestRequest.current) {
        setInsScheduleTimes(response.data);
      }
    } catch (error) {
      if (requestId === latestRequest.current) {
        console.error("Error fetching tasks:", error);
      }
    }
  };
/**
 * Recalculates the height of one time cell after render.
 * This helps to dynamically size events based on their duration.
 */
  useEffect(() => {
    fetchScheduleInformation();
  }, [currentStartDate, currentEndDate]);

  useEffect(() => {
    if (cellRef.current) {
      setCellHeight(cellRef.current.offsetHeight);
    }
  }, [insScheduleTimes]);

  const buildScheduleLookup = () => {
    const lookup = {};
    insScheduleTimes.forEach(event => {
      const dayKey = event.dayOfWeek.slice(0, 3);
      const [startHour, startMinute] = event.startTime.split(":").map(Number);
      const [endHour, endMinute] = event.endTime.split(":").map(Number);
      const duration = (endHour * 60 + endMinute) - (startHour * 60 + startMinute);
      const key = `${dayKey}-${startHour}`;
      if (!lookup[key]) lookup[key] = [];
      lookup[key].push({ ...event, offset: startMinute, duration });
    });
    return lookup;
  };

  const eventLookup = buildScheduleLookup();

  return (
    <div className="schedule-page">
      <NavbarINS />

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
              {days.map((day, dayIdx) => {
                const hour = 8 + timeIdx;
                const key = `${day}-${hour}`;
                const cellEvents = eventLookup[key] || [];
                return (
                  <div
                    key={`${timeIdx}-${dayIdx}`}
                    className="grid-cell"
                    ref={timeIdx === 0 && dayIdx === 0 ? cellRef : null}
                  >
                    {cellEvents.map((event, i) => (
                      <div
                        key={i}
                        className="ta-schedule-inner-event"
                        style={{
                          height: `${(event.duration / 60) * cellHeight}px`,
                          marginTop: `${(event.offset / 60) * cellHeight}px`,
                          backgroundColor: event.eventType == "lecture" ? "#a8d5ff" : event.eventType == "proctoring" ? "#9DC08B" : event.eventType == "leave of absence" ? "#e03e3e" : event.eventType == "exam" ? "#FF8700" : "white",
                        }}
                      >
                        <div>
                          {event.eventName}
                        </div>
                        {event.startTime} to {event.endTime}
                      </div>
                    ))}
                  </div>
                );
              })}
            </React.Fragment>
          ))}
        </div>
      </div>
    </div>
  );
};

export default INS_SchedulePage;