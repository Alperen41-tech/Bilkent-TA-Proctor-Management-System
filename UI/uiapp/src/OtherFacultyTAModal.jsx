// OtherFacultyTAModal.js
import React, { useState } from "react";
import "./OtherFacultyTAModal.css"; // optional for styling
import axios from "axios";

const OtherFacultyTAModal = ({ isOpen, onClose, classProctoringId, onSuccess }) => {
const [form, setForm] = useState({
  name: "",
  surname: "",
  email: "",
  bilkentId: "",
});


  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
  };

  const handleAssign = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.post(
        "http://localhost:8080/classProctoringTARelation/assignTAFromOtherFaculty",
        form,
        {
          params: { classProctoringId },
          headers: {
            Authorization: `Bearer ${token}`
          }
        }
      );

      if (response.data === true) {
        alert("TA successfully assigned from other faculty.");
        onSuccess(); // refresh exams, etc.
        onClose();
      } else {
        alert("Failed to assign TA.");
      }
    } catch (error) {
      console.error("Error assigning TA:", error);
      alert("An error occurred while assigning TA.");
    }
  };

  if (!isOpen) return null;

  return (
    <div className="modal-overlay">
      <div className="modal">
        <h3>Assign TA From Outside Faculty</h3>
        <div className="modal-content">
          {["name", "surname", "email", "bilkentId"].map((field) => (
            <div key={field}>
              <label>{field}</label>
              <input
                type="text"
                name={field}
                value={form[field]}
                onChange={handleChange}
              />
            </div>
          ))}
          <div className="modal-actions">
            <button onClick={handleAssign}>Assign TA</button>
            <button onClick={onClose}>Cancel</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default OtherFacultyTAModal;
