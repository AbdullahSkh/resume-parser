import React from "react";
import Navbar from "../../components/Navbar/index.jsx";
import "./style.css";

const Home = () => {
  return (
    <div className="home-container">
      <Navbar />
      <div className="home-content">
        <div className="home-card">
          <h1 className="home-title">Resume Parser</h1>
          <p className="home-description">
            Welcome to Resume Parser! Our application helps you to scan and
            analyze resumes efficiently. Upload your resume and get detailed
            insights.
          </p>
        </div>
      </div>
    </div>
  );
};

export default Home;
