import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./style.css";
import axios from "axios";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post(
        "http://localhost:8080/api/auth/login",
        {
          email: username,
          password,
        }
      );

      if (response.data.success) {
        localStorage.setItem("isLoggedIn", "true");
        navigate("/");
      } else {
        setError(response.data.message || "Login failed. Please try again.");
      }
    } catch (err) {
      console.log(err);
      setError("Error logging in. Please try again later.");
    }
  };

  const handleRegister = () => {
    navigate("/register");
  };

  return (
    <div className="login-container">
      <div className="login-form">
        <h2 className="login-title">Login</h2>
        {error && <div className="error-message">{error}</div>}

        <form onSubmit={handleLogin}>
          <div className="input-field">
            <label className="input-label" htmlFor="username">
              Username
            </label>
            <input
              type="text"
              id="username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              className="input"
              required
            />
          </div>
          <div className="input-field">
            <label className="input-label" htmlFor="password">
              Password
            </label>
            <input
              type="password"
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="input"
              required
            />
          </div>
          <div className="reg-container">
            <p className="reg-text">
              <span>New user? </span>
              <a onClick={handleRegister} className="reg-link">
                Click here to register
              </a>
            </p>
          </div>
          <button type="submit" className="submit-button">
            Login
          </button>
        </form>
      </div>
    </div>
  );
};

export default Login;
