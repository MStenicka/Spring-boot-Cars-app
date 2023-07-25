# Cars Application

## Overview
The Cars Application is a Java-based REST API built using the Spring framework and Gradle build tool. 
It allows users to perform basic CRUD operations on automobile data, manufacturers, and models. 
The application utilizes an SQL database for data persistence and provides comprehensive documentation through Swagger. 
Unit tests ensure the reliability of critical methods, and error logging is implemented to handle exceptions gracefully.

## Features
- REST API with endpoints for managing automobiles, manufacturers, and models.
- Integration with an SQL database for data storage and retrieval.
- Documentation of the API endpoints using the Swagger framework.
- Unit tests for critical methods to ensure application reliability.
- Error logging with multiple log levels (INFO, ERROR, SUCCESS, NOT_VALID).
- Import functionality to allow batch creation of automobile entities from a JSON file. (in progress)
- Friendly user-facing error messages for enhanced user experience. (in progress)

## Installation and Setup
1. Clone the repository to your local machine.
2. Install Java Development Kit (JDK) version 11 or higher.
3. Set up an SQL database (e.g., MySQL) and configure the application.properties file with the appropriate database connection settings.
4. Build the application using Gradle: `gradle build`.
5. Run the application: `gradle bootRun`.

## API Documentation
Swagger is integrated into the application to provide interactive API documentation. Once the application is running, you can access the Swagger UI at `http://localhost:8080/swagger-ui.html`.

## Unit Tests
The project includes unit tests for complex methods, such as input validation for user inputs, to ensure robust functionality and code reliability.

## Error Logging (in progress)
The application employs comprehensive error logging with different log levels (INFO, ERROR, SUCCESS, NOT_VALID). Logs are stored in a separate database table, and an endpoint is available for convenient log filtering and retrieval.

## Importing Data (in progress)
The application supports bulk creation of automobile entities through JSON file import. The file should adhere to the specified JSON format.
