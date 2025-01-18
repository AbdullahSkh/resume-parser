import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import "./style.css";

const Navbar = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const userLoggedIn = localStorage.getItem("isLoggedIn");
    setIsLoggedIn(!!userLoggedIn);
  }, []);

  const handleLoginClick = () => {
    localStorage.setItem("isLoggedIn", "true");
    setIsLoggedIn(true);
    navigate("/login");
  };

  const handleLogoutClick = () => {
    localStorage.removeItem("isLoggedIn");
    setIsLoggedIn(false);
    navigate("/");
  };

  return (
    <nav className="navbar">
      <div className="navbar-brand">Resume Parser</div>
      <div className="navbar-links">
        <Link to="/" className="nav-link">
          Home
        </Link>
        {isLoggedIn ? (
          <>
            <Link to="/upload" className="nav-link">
              Upload
            </Link>
            <Link to="/search" className="nav-link">
              Search
            </Link>
          </>
        ) : (
          <>
            <Link
              to="/upload"
              className="nav-link disabled-link"
              onClick={(e) => e.preventDefault()}
            >
              Upload
            </Link>
            <Link
              to="/search"
              className="nav-link disabled-link"
              onClick={(e) => e.preventDefault()}
            >
              Search
            </Link>
          </>
        )}

        <div className="login-logout">
          {isLoggedIn ? (
            <button className="btn logout-btn" onClick={handleLogoutClick}>
              Logout
            </button>
          ) : (
            <button className="btn login-btn" onClick={handleLoginClick}>
              Login
            </button>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
