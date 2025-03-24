import React from "react";

function SystemControl({ startSystem, stopSystem, isRunning }) {
  const handleStart = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/start", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
      });

      if (!response.ok) {
        throw new Error("Failed to start the system.");
      }

      startSystem(); // Call the local handler to update the UI
      alert("System started successfully!");
    } catch (error) {
      console.error(error);
      alert("Error starting the system: " + error.message);
    }
  };

  const handleStop = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/stop", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
      });

      if (!response.ok) {
        throw new Error("Failed to stop the system.");
      }

      stopSystem(); // Call the local handler to update the UI
      alert("System stopped successfully!");
    } catch (error) {
      console.error(error);
      alert("Error stopping the system: " + error.message);
    }
  };

  return (
    <div className="system-control">
      <h2>🎟️System Control</h2>
      <button onClick={handleStart} disabled={isRunning}>
        Start System
      </button>
      <button onClick={handleStop} disabled={!isRunning}>
        Stop System
      </button>
    </div>
  );
}

export default SystemControl;

