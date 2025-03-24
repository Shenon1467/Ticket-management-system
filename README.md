## Ticket Booking System ##

This project is a ticket booking simulation built with Spring Boot for the backend and React for the frontend. It models real-world scenarios where tickets are simultaneously bought and sold by multiple users. The system allows users to manage configurations, start and stop operations, and monitor activity through a detailed log panel.

## Key Features

- **Simultaneous Ticket Operations**: Vendors release tickets while customers purchase them concurrently, highlighting the system’s multithreading functionality.
- **Configuration Management**:
  - **Save Configurations**: Users can save their setup to a JSON file for future use.
  - **Load Configurations**: Reload previously saved configurations with a single click.
- **System Control**:
  - **Start Simulation**: Launch vendor and customer threads to begin the ticket buying and selling process.
  - **Stop Simulation**: Halt all operations, allowing for reconfiguration or restarting as needed.
- **Intuitive Interface**: A user-friendly React-based interface simplifies interaction, providing smooth and responsive controls.

## Technology Stack

### Backend
- **Programming Language**: Java
- **Framework**: Spring Boot

### Frontend
- **Technologies**: React, JavaScript, CSS

## Prerequisites

### Backend
- Java version 21 or later

### Frontend
- Node.js version 20 or later

## Setup Guide

### Backend
1. Download the Spring Boot project.
2. Build and run the backend application.
3. Verify that the backend is accessible at [http://localhost:8080](http://localhost:8080).

### Frontend
1. Navigate to the frontend project directory.
2. Install dependencies using `npm install`.
3. Start the frontend application using `npm start`.
4. Ensure the frontend is accessible at [http://localhost:3000](http://localhost:3000).

## Running the Application

1. **Start the Backend**: Run the Spring Boot application.
2. **Launch the Frontend**: Start the React application.
3. **Open the Application**: Access the system in your browser.
4. **Manage the System**:
   - Configure and save system settings, or load pre-existing configurations.
   - Start the simulation to allow vendors and customers to operate concurrently.
   - Stop operations whenever needed.
5. **Monitor Logs**: Use the log panel to track system events and thread activities.

---

This system demonstrates the power of multithreading in handling concurrent operations, paired with a modern and responsive user interface for effective ticket booking simulation.

