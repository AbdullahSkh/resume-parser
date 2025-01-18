import React, { useState } from "react";
import Navbar from "../../components/Navbar/index.jsx";
import axios from "axios";
import "./style.css"; // Import the new CSS file

const SearchFile = () => {
  const [searchQuery, setSearchQuery] = useState("");
  const [searchType, setSearchType] = useState("id"); // 'id' or 'text'
  const [resumes, setResumes] = useState([]);
  const [error, setError] = useState("");

  const handleSearch = async () => {
    if (!searchQuery.trim()) {
      setError("Search query cannot be empty.");
      return;
    }
    try {
      setError("");
      const response = await axios.get(`/api/files/search`, {
        params: {
          query: searchQuery,
          type: searchType,
        },
      });
      setResumes(response.data);
    } catch (error) {
      console.error("Error fetching resumes:", error);
      setError("Failed to fetch resumes. Please try again.");
    }
  };

  return (
    <div>
      <Navbar />
      <div className="container">
        <h1 className="title">Resume Insights</h1>

        <div className="search-bar">
          <input
            type="text"
            placeholder={`Search by ${searchType === "id" ? "ID" : "Text"}`}
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="search-input"
          />
          <select
            value={searchType}
            onChange={(e) => setSearchType(e.target.value)}
            className="search-select"
          >
            <option value="id">Search by ID</option>
            <option value="text">Search by Text</option>
          </select>
          <button onClick={handleSearch} className="search-button">
            🔍
          </button>
        </div>

        {error && <p className="error-message">{error}</p>}

        {resumes.length > 0 && (
          <div>
            <h2 className="resume-title">Fetched Resumes:</h2>
            <ul className="resume-list">
              {resumes.map((resume, index) => (
                <li key={index} className="resume-item">
                  <strong>ID:</strong> {resume.id} <br />
                  <strong>Name:</strong> {resume.name} <br />
                  <strong>Experience:</strong> {resume.experience} years <br />
                  <strong>Skills:</strong> {resume.skills.join(", ")} <br />
                </li>
              ))}
            </ul>
          </div>
        )}
      </div>
    </div>
  );
};

export default SearchFile;
