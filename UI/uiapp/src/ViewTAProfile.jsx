// src/ViewTAProfile.jsx
import React, { useEffect, useState, useRef } from "react";
import "./ViewTAProfile.css";
import { format, addWeeks, subWeeks, startOfWeek, addDays } from "date-fns";
import axios from "axios";

const ViewTAProfile = ({ taId = 2 }) => {
  const [profile, setProfile] = useState({});
  const [currentStartDate, setCurrentStartDate] = useState(startOfWeek(new Date(), { weekStartsOn: 1 }));
  const [currentEndDate, setCurrentEndDate] = useState(addDays(addWeeks(currentStartDate, 1), -1));
  const [insScheduleTimes, setInsScheduleTimes] = useState([]);
  const [cellHeight, setCellHeight] = useState(0);
  const cellRef = useRef(null);

  const fetchScheduleInformation = async () => {
    try {
      const response = await axios.post(`http://localhost:8080/timeInterval/taSchedule?id=${taId}`, {
        startDate: format(currentStartDate, "yyyy-MM-dd"),
        endDate: format(currentEndDate, "yyyy-MM-dd"),
      });
      console.log("Fetched schedule data:", response.data);
      setInsScheduleTimes(response.data);
      
    } catch (error) {
      console.error("Error fetching tasks:", error);
    }
  };

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
      const [startHour, startMinute] = event.startTime.split(":" ).map(Number);
      const [endHour, endMinute] = event.endTime.split(":" ).map(Number);
      const duration = (endHour * 60 + endMinute) - (startHour * 60 + startMinute);
      const key = `${dayKey}-${startHour}`;
      if (!lookup[key]) lookup[key] = [];
      lookup[key].push({ ...event, offset: startMinute, duration });
    });
    return lookup;
  };

  useEffect(() => {
    const fetchTA = async () => {
      try {
        const res = await axios.get(`http://localhost:8080/ta/profile?id=${taId}`);
        setProfile(res.data);
      } catch (err) {
        console.error("Failed to load TA profile", err);
      }
    };
    fetchTA();
  }, [taId]);


  const eventLookup = buildScheduleLookup();
  const days = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"];
  const times = [
    "8:00 am", "9:00 am", "10:00 am", "11:00 am", "12:00 pm",
    "1:00 pm", "2:00 pm", "3:00 pm", "4:00 pm", "5:00 pm",
    "6:00 pm", "7:00 pm", "8:00 pm", "9:00 pm", "10:00 pm"
  ];

  return (
    <div className="view-ta-profile-profile-view">

      <div className="view-ta-profile-profile-content">
        <div className="view-ta-profile-left-info-card">
          <h3>TA Profile</h3>
          <p><strong>Name:</strong> {profile.name}</p>
          <p><strong>Surname:</strong> {profile.surname}</p>
          <p><strong>Email:</strong> {profile.email}</p>
          <p><strong>Bilkent ID:</strong> {profile.bilkentId}</p>
          <p><strong>Role:</strong> {profile.role}</p>
          <p><strong>Department:</strong> {profile.departmentName}</p>
          <p><strong>Course:</strong> {profile.courseName}</p>
          <p><strong>Total Workload:</strong> {profile.workload}</p>
        </div>

        <div className="view-ta-profile-calendar-container">
          <h3>Weekly Schedule</h3>
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
                          className="ins-schedule-inner-event"
                          style={{
                            height: `${(event.duration / 60) * cellHeight}px`,
                            marginTop: `${(event.offset / 60) * cellHeight}px`,
                            backgroundColor: event.eventType == "lecture" ? "#a8d5ff" : event.eventType == "proctoring" ?  "#9DC08B": event.eventType == "leave of absence" ? "#e03e3e" : event.eventType == "exam" ? "#FF8700" : "white",
                          }}
                        >
                          <div>
                            {event.eventName}
                          </div>
                            {event.startTime} to {event.endTime }
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
    </div>
  );
};

export default ViewTAProfile;
