import React, { useState } from "react";
import ConfigForm from "./components/ConfigForm";
import SystemControl from "./components/SystemControl";
import LogsDisplay from "./components/LogsDisplay";
import "./App.css";

function App() {
  const [config, setConfig] = useState({
    totalTickets: 0,
    ticketReleaseRate: 0,
    customerRetrievalRate: 0,
    maxTicketCapacity: 0,
    numberOfVendors: 0,
    numberOfCustomers: 0,
    buyLimit: 0,
  });

  const [logs, setLogs] = useState([]);
  const [isRunning, setIsRunning] = useState(false);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setConfig({ ...config, [name]: parseInt(value, 10) });
  };

  const saveConfiguration = () => {
    // Placeholder for save configuration logic
    setLogs((prevLogs) => [...prevLogs, "Configuration saved successfully."]);
  };

  const resetConfiguration = () => {
    setConfig({
      totalTickets: 0,
      ticketReleaseRate: 0,
      customerRetrievalRate: 0,
      maxTicketCapacity: 0,
      numberOfVendors: 0,
      numberOfCustomers: 0,
      buyLimit: 0,
    });
    setLogs((prevLogs) => [...prevLogs, "Configuration reset successfully."]);
  };

  const startSystem = () => {
    setIsRunning(true);
    setLogs((prevLogs) => [...prevLogs, "System started."]);
  };

  const stopSystem = () => {
    setIsRunning(false);
    setLogs((prevLogs) => [...prevLogs, "System stopped."]);
  };

  return (
    <div className="App">
      <h1>🎟️ Ticket System</h1>
      <ConfigForm
        config={config}
        handleInputChange={handleInputChange}
        saveConfiguration={saveConfiguration}
        resetConfiguration={resetConfiguration}
      />
      <SystemControl
        startSystem={startSystem}
        stopSystem={stopSystem}
        isRunning={isRunning}
      />
      <LogsDisplay logs={logs} />
    </div>
  );
}

export default App;
