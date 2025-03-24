import React from "react";
import"./ConfigForm.css"

function ConfigForm({ config, handleInputChange, resetConfiguration }) {

  const handleSaveConfiguration = async () => {
    try {
      // Construct query string from config data
      const queryString = new URLSearchParams(config).toString();
  
      // Send the data as query parameters to the backend
      const response = await fetch(`http://localhost:8080/api/saveconfig?${queryString}`, {
        method: "POST",
      });
  
      if (response.ok) {
        alert("Configuration saved successfully!");
      } else {
        const errorText = await response.text();
        console.error("Failed to save configuration:", errorText);
        alert("Failed to save configuration: " + errorText);
      }
    } catch (error) {
      console.error("Error saving configuration:", error);
      alert("An error occurred while saving the configuration.");
    }
  };

  return (
    <div className="config-form">
      <h2>🎟️Configuration</h2>
      {Object.keys(config).map((key) => (
        <div key={key} className="form-group">
          <label>{key.replace(/([A-Z])/g, " $1").toUpperCase()}</label>
          <input
            type="number"
            name={key}
            value={config[key]}
            onChange={handleInputChange}
            min="0"
          />
        </div>
      ))}
      <div className="buttons">
        <button onClick={handleSaveConfiguration}>Save Configuration</button>
        <button onClick={resetConfiguration}>Reset Configuration</button>
      </div>
    </div>
  );
}

export default ConfigForm;
