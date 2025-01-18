import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./style.css";

const Register = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [error, setError] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);
  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();

    if (password !== confirmPassword) {
      setError("Passwords do not match");
      return;
    }

    setIsSubmitting(true);

    try {
      const response = await axios.post(
        "http://localhost:8080/api/auth/register",
        {
          email,
          password,
        },
        {
          headers: { "Content-Type": "application/json" },
          withCredentials: true,
        }
      );

      if (response.data.success) {
        localStorage.setItem("isLoggedIn", "true");
        navigate("/");
      } else {
        setError(
          response.data.message || "Registration failed. Please try again."
        );
      }
    } catch (err) {
      setError("Error registering. Please try again later.");
    } finally {
      setIsSubmitting(false);
    }
  };

  const handleLogin = () => {
    navigate("/login");
  };

  return (
    <div className="register-container">
      <div className="register-form">
        <h2 className="register-title">Register</h2>
        {error && <div className="error-message">{error}</div>}

        <form onSubmit={handleRegister}>
          <div className="input-field">
            <label className="input-label" htmlFor="email">
              Email
            </label>
            <input
              type="email"
              id="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
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
          <div className="input-field">
            <label className="input-label" htmlFor="confirmPassword">
              Confirm Password
            </label>
            <input
              type="password"
              id="confirmPassword"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              className="input"
              required
            />
          </div>

          <div className="log-container">
            <p className="log-text">
              <span>Already registered? </span>
              <a onClick={handleLogin} className="log-link">
                Click here to Login
              </a>
            </p>
          </div>

          <button
            type="submit"
            className="submit-button"
            disabled={isSubmitting}
          >
            Register
          </button>
        </form>
      </div>
    </div>
  );
};

export default Register;
