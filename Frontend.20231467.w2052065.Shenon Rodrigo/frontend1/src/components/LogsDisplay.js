import React, { useState, useEffect } from "react";

function LogsDisplay() {
  const [logs, setLogs] = useState(""); // Use a single string for logs
  const [error, setError] = useState(null);

  const fetchLogs = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/logs");
      if (response.ok) {
        const data = await response.json();
        if (Array.isArray(data)) {
          // Append logs directly as a string
          setLogs((prevLogs) => `${prevLogs}\n${data.join("\n")}`);
        } else {
          setError("Unexpected response format. Logs should be an array.");
        }
      } else {
        const errorText = await response.text();
        setError(`Failed to fetch logs: ${errorText}`);
      }
    } catch (err) {
      setError(`Error fetching logs: ${err.message}`);
    }
  };

  useEffect(() => {
    fetchLogs();
    const interval = setInterval(fetchLogs, 5000);
    return () => clearInterval(interval);
  }, []);

  return (
    <div className="logs-container">
      <h2>🎟️System Logs</h2>
      {error ? (
        <p className="error">{error}</p>
      ) : (
        <pre className="logs">{logs}</pre> // Display logs as preformatted text
      )}
    </div>
  );
}

export default LogsDisplay;
