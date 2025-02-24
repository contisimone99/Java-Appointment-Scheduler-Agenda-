# Java Appointment Scheduler (Agenda)

A Java application for managing appointments and schedules with conflict detection.

## Overview

This project implements an appointment management system that allows users to:

- Create and manage appointments with contact information, location, date/time, and duration
- Check for scheduling conflicts automatically
- Search for appointments by name, date, or time range
- Save and load appointment data to/from files

## Features

- **Appointment Management**: Add, modify, and delete appointments
- **Conflict Detection**: Automatically prevents overlapping appointments
- **Search Functionality**: Find appointments by contact name or date range
- **Persistence**: Save and load appointment data from files
- **Exception Handling**: Custom exceptions for appointment conflicts and validation

## Project Structure

The project is organized into three main packages:

- `codice`: Contains core classes for appointment management
  - `Agenda`: Manages collections of appointments and prevents conflicts
  - `Appuntamento`: Represents individual appointments with validation

- `exception`: Custom exception handling
  - `AgendaException`: Base exception class
  - `AppuntamentoException`: For appointment validation errors
  - `TimeException`: For time conflict errors

- `interfaccia`: User interface components
  - `Interface`: Command-line interface for interacting with the system

## Usage Example

```java
// Create a new agenda
Agenda agenda = new Agenda();

// Add appointments
try {
    agenda.aggiungi(new Appuntamento("Marco", "01/09/2021", "15:00", "Office", 60));
    agenda.aggiungi(new Appuntamento("Anna", "01/09/2021", "16:30", "Conference Room", 30));
} catch (AppuntamentoException | TimeException e) {
    System.out.println("Error: " + e.getMessage());
}

// Search for appointments
LocalDate searchDate = LocalDate.of(2021, 9, 1);
List<Appuntamento> results = agenda.ricercaData(searchDate);

// Save to file
agenda.scriviAgenda("appointments.txt");
```

## Testing

The project includes JUnit tests for both the `Agenda` and `Appuntamento` classes, covering:

- Appointment creation and validation
- Conflict detection
- Search functionality
- File persistence

## Requirements

- Java 14 or higher
- JUnit 5 (for running tests)
