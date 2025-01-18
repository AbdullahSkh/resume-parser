import React, { useState } from "react";
import axios from "axios";
import Navbar from "../../components/Navbar/index.jsx";
import "./style.css";

import * as pdfjsLib from "pdfjs-dist";

const UploadFile = () => {
  const [file, setFile] = useState(null);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  pdfjsLib.GlobalWorkerOptions.workerSrc =
    "https://cdnjs.cloudflare.com/ajax/libs/pdf.js/4.10.38/pdf.min.mjs";

  const parsePDF = async (file) => {
    const reader = new FileReader();

    return new Promise((resolve, reject) => {
      reader.onload = async function () {
        try {
          const arrayBuffer = reader.result;
          const pdf = await pdfjsLib.getDocument(arrayBuffer).promise;
          const numPages = pdf.numPages;
          let textContent = "";

          for (let i = 1; i <= numPages; i++) {
            const page = await pdf.getPage(i);
            const content = await page.getTextContent();

            content.items.forEach((item) => {
              textContent += item.str + " ";
            });
          }

          resolve(textContent);
        } catch (err) {
          reject(err);
        }
      };

      reader.onerror = (err) => reject(err);
      reader.readAsArrayBuffer(file);
    });
  };

  const extractDetailsWithAI = async (resumeText) => {
    try {
      const response = await axios.post(
        "https://api.openai.com/v1/chat/completions",
        {
          model: "gpt-3.5-turbo",
          messages: [
            {
              role: "system",
              content: `
                You are an AI bot designed to parse resumes and extract the following details in JSON format:
                - fullName (string)
                - email (string)
                - github (string)
                - linkedIn (string)
                - employmentDetails (string)
                - experience (integer, in years)
                - skills (array of objects, each with 'name' field)
                - softSkills (array of objects, each with 'name' field)
              `,
            },
            { role: "user", content: resumeText },
          ],
        },
        {
          headers: {
            Authorization: `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`, // Replace with your API key
            "Content-Type": "application/json",
          },
        }
      );

      const parsedDetails = response.data.choices[0].message.content;
      return JSON.parse(parsedDetails);
    } catch (err) {
      console.error("Error extracting details with AI:", err);
      setError("Failed to parse resume using AI.");
      return null;
    }
  };

  const handleFileChange = (event) => {
    setFile(event.target.files[0]);
  };

  const uploadJson = async (parsedJson) => {
    setLoading(true);

    try {
      const response = await axios.post(
        "http://localhost:8080/api/files/upload",

        parsedJson,
        {
          headers: {
            "Content-Type": "application/json",
          },
          withCredentials: true,
        }
      );
      console.log("Json uploaded successfully:", response.data);
      setError(null);
    } catch (error) {
      console.error("Error json file:", error);

      if (error.response) {
        setError(`Error: ${error.response.status} - ${error.response.data}`);
      } else if (error.request) {
        setError("No response from the server. Please try again later.");
      } else {
        setError(`Unexpected error: ${error.message}`);
      }
    } finally {
      setLoading(false);
    }
  };

  const handleFileUpload = async () => {
    if (!file) {
      alert("Please select a file first!");
      return;
    }

    setLoading(true);
    setError(null);

    try {
      const resumeText = await parsePDF(file);

      const parsedJson = await extractDetailsWithAI(resumeText);

      if (!parsedJson) {
        throw new Error("AI extraction failed.");
      }

      await uploadJson(parsedJson);
    } catch (err) {
      console.error("Error processing file:", err);
      setError("An error occurred while processing the file.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <Navbar />

      <div className="upload-container">
        <div className="upload-form">
          <h2 className="upload-title">Upload File</h2>
          {error && <div className="error-message">{error}</div>}
          <input
            type="file"
            accept=".pdf"
            onChange={handleFileChange}
            disabled={loading}
          />
          <button
            onClick={handleFileUpload}
            disabled={loading}
            className={`upload-button ${loading ? "loading" : ""}`}
          >
            {loading ? (
              <div className="loading-indicator">
                <div className="spinner"></div>
                <span>Uploading...</span>
              </div>
            ) : (
              <div className="upload-content">Upload</div>
            )}
          </button>
        </div>
      </div>
    </div>
  );
};
export default UploadFile;
