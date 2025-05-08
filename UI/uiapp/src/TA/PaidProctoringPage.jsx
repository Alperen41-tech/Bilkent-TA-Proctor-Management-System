// src/PaidProctoringPage.jsx
import React, { use, useEffect, useState } from "react";
import Navbar from "./Navbar";
import PaidProctoringItem from "./PaidProctoringItem";
import "./PaidProctoringPage.css";
import TAItem from "../TAItem";
import axios from "axios";
import { set } from "date-fns";

const PaidProctoringPage = () => {
  const [paidProctorings, setPaidProctorings] = useState([]);
  const [appliedTAs, setAppliedTAs] = useState([]);
  const [selectedPaidProctoring, setSelectedPaidProctoring] = useState(null);

  const fetchPaidProctorings = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.get(`http://localhost:8080/proctoringApplication/getAllApplicationsForTA?applicationType=APPLICATION`, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      if(response.data){
        console.log("Paid Proctorings fetched successfully:", response.data);
        setPaidProctorings(response.data);
      }
      else{
        console.log("No paid proctorings found.");
      }
    } catch (error) {
      console.error("Error fetching paid proctorings:", error);
      if (error.response.data.message) {
        alert(error.response.data.message);
      }
      else{
        alert("An error occurred. Please try again.");
      }
    }
  };

  const fetchAppliedStudents = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/proctoringApplicationTARelation/getApplicantsForApplication?applicationId=${selectedPaidProctoring.applicationId}`); // Adjust the URL as needed
      if (response.data) {
        setAppliedTAs(response.data);
      }
      else{
        console.log("No paid proctoring requests found.");
      }
    } catch (error) {
      console.error("Error fetching paid proctoring requests applicants:", error);
      if (error.response.data.message) {
        alert(error.response.data.message);
      }
      else{
        alert("An error occurred. Please try again.");
      }
    }
  };

  const handleEnroll = async (task) => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.post(`http://localhost:8080/proctoringApplicationTARelation/create?applicationId=${task.applicationId}`,null,{
        headers: {
          Authorization: `Bearer ${token}`
        }});
      if (response.data) {
        console.log("Enrolled in paid proctoring successfully");
        fetchPaidProctorings();
        fetchAppliedStudents();
      } else {
        console.log("Failed to enroll in paid proctoring.");
      }
    } catch (error) {
      console.error("Error enrolling in paid proctoring:", error);
      if (error.response.data.message) {
        alert(error.response.data.message);
      }
      else{
        alert("An error occurred. Please try again.");
      }
    }
  };

  const createTAItem = (ta) => {
    return (
      <TAItem
        key={ta.bilkentId}
        ta={ta}
        onClick={console.log(ta)}
        isSelected={console.log(ta)}        
      />
    );
  };

  useEffect(() => {
    fetchPaidProctorings(); // Fetch paid proctorings when the component mounts
  }, []);

  useEffect(() => {
    if (selectedPaidProctoring) {
      fetchAppliedStudents();
    }
  }, [selectedPaidProctoring]); // Fetch applied TAs when a paid proctoring is selected


  return (
    <div className="paid-proctoring-page">
      <Navbar />

      <div className="proctoring-content">
        {/* LEFT: List of proctoring tasks */}
        <div className="proctoring-left">
          <div className="card">
            <h3>Paid Proctoring List</h3>
            
            {/* Task List Placeholder */}
            <div className="task-list">
              {paidProctorings.map((task) => (
                <PaidProctoringItem
                  key={task.applicationId}
                  task={task}
                  isSelected={selectedPaidProctoring === task}
                  isEnrolled={task.isAppliedByTA}
                  onSelect={()=> setSelectedPaidProctoring(task)}
                  onEnroll={() => handleEnroll(task)}
                />
              ))}
            </div>
          </div>
        </div>

        {/* RIGHT: Selected task details */}
        <div className="proctoring-right">
          <div className="card">
            <h3>TAs Applied for this Task</h3>
            {/* Applied TA Placeholder */}
            <div className="assigned-tas">
              {appliedTAs.length > 0 ? (
                <div className="applied-ta-list">
                  {appliedTAs.map((ta) => createTAItem(ta))}
                </div>
              ) : (
                <p>No TAs have applied for this task yet.</p>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default PaidProctoringPage;
