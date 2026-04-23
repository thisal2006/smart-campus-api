# Smart Campus API - JAX-RS RESTful Service

## 📋 Project Overview

A robust RESTful API for managing university campus rooms and IoT sensors, built with JAX-RS (Jersey) and deployed on Apache Tomcat. This system enables facilities managers to track room occupancy, monitor environmental sensors, and maintain historical sensor readings.

**Base URL:** `http://localhost:8080/smart-campus-api/api/v1`

---

## Architecture & Technology Stack

| Component | Technology |
|-----------|------------|
| Framework | JAX-RS (Jakarta RESTful Web Services) |
| Implementation | Jersey 3.1.3 |
| Servlet Container | Apache Tomcat 10.1.54 |
| JSON Processing | Jackson |
| Language | Java 11 |
| Build Tool | Maven |
| Data Storage | In-memory (ConcurrentHashMap) |

---

## Project Structure

```text
smart-campus-api/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── smartcampus/
│       │           ├── api/
│       │           │   ├── ApplicationConfig.java
│       │           │   ├── DiscoveryResource.java
│       │           │   ├── RoomResource.java
│       │           │   ├── SensorResource.java
│       │           │   ├── SensorReadingResource.java
│       │           │   ├── RoomNotEmptyException.java
│       │           │   ├── RoomNotEmptyExceptionMapper.java
│       │           │   ├── LinkedResourceNotFoundException.java
│       │           │   ├── LinkedResourceNotFoundExceptionMapper.java
│       │           │   ├── SensorUnavailableException.java
│       │           │   ├── SensorUnavailableExceptionMapper.java
│       │           │   ├── GlobalExceptionMapper.java
│       │           │   └── LoggingFilter.java
│       │           └── model/
│       │               ├── Room.java
│       │               ├── Sensor.java
│       │               ├── Reading.java
│       │               └── ErrorResponse.java
│       └── webapp/
│           └── WEB-INF/
│               └── web.xml
├── pom.xml
└── README.md
```

---

## Build & Run Instructions

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher
- Apache Tomcat 10

### Step 1: Clone the Repository
```bash
git clone https://github.com/yourusername/smart-campus-api.git
cd smart-campus-api
```

### Step 2: Build the WAR File
```bash
mvn clean package
```
The WAR file will be created at: target/smart-campus-api.war

---

### Step 3: Deploy to Tomcat
Copy the WAR file to Tomcat's webapps folder:

```bash
copy target\smart-campus-api.war C:\apache-tomcat-10.1.54\webapps\
```

---

### Step 4: Start Tomcat
```bash
cd C:\apache-tomcat-10.1.54\bin
startup.bat
```

---

### Step 5: Verify Server is Running
Open browser and navigate to:

```
http://localhost:8080/smart-campus-api/api/v1/
```

Expected response:

```json
{
    "version": "1.0.0",
    "apiName": "Smart Campus API",
    "adminContact": "facilities@smartcampus.edu",
    "description": "RESTful API for managing campus rooms and sensors",
    "resources": {
        "rooms": "/api/v1/rooms",
        "sensors": "/api/v1/sensors"
    }
}
```

---

##  API Endpoints Reference

### Discovery
| Method | Endpoint | Description |
|--------|---------|------------|
| GET | /api/v1/ | API metadata and HATEOAS links |

---

### Room Management
| Method | Endpoint | Description | Status Codes |
|--------|---------|------------|-------------|
| GET | /api/v1/rooms | List all rooms | 200 OK |
| POST | /api/v1/rooms | Create new room | 201 Created, 400 Bad Request |
| GET | /api/v1/rooms/{id} | Get room by ID | 200 OK, 404 Not Found |
| DELETE | /api/v1/rooms/{id} | Delete room | 204 No Content, 404 Not Found, 409 Conflict |

---

### Sensor Management
| Method | Endpoint | Description | Status Codes |
|--------|---------|------------|-------------|
| GET | /api/v1/sensors | List all sensors | 200 OK |
| GET | /api/v1/sensors?type={type} | Filter sensors by type | 200 OK |
| POST | /api/v1/sensors | Register new sensor | 201 Created, 422 Unprocessable Entity |
| GET | /api/v1/sensors/{id} | Get sensor by ID | 200 OK, 404 Not Found |

---

### Sensor Readings (Sub-Resource)
| Method | Endpoint | Description | Status Codes |
|--------|---------|------------|-------------|
| GET | /api/v1/sensors/{id}/readings | Get reading history | 200 OK, 404 Not Found |
| POST | /api/v1/sensors/{id}/readings | Add new reading | 201 Created, 403 Forbidden, 404 Not Found |

---

##  Sample cURL Commands

### 1. Discovery Endpoint
```bash
curl -X GET http://localhost:8080/smart-campus-api/api/v1/
```

### 2. Create a Room
```bash
curl -X POST http://localhost:8080/smart-campus-api/api/v1/rooms \
  -H "Content-Type: application/json" \
  -d '{"name":"Lab 101","building":"Engineering","floor":1}'
```

### 3. Get All Rooms
```bash
curl -X GET http://localhost:8080/smart-campus-api/api/v1/rooms
```

### 4. Create a Sensor
```bash
curl -X POST http://localhost:8080/smart-campus-api/api/v1/sensors \
  -H "Content-Type: application/json" \
  -d '{"name":"CO2 Monitor","type":"CO2","roomId":1}'
```

### 5. Filter Sensors by Type
```bash
curl -X GET "http://localhost:8080/smart-campus-api/api/v1/sensors?type=CO2"
```

### 6. Add Sensor Reading
```bash
curl -X POST http://localhost:8080/smart-campus-api/api/v1/sensors/1/readings \
  -H "Content-Type: application/json" \
  -d '{"value":"420","unit":"ppm"}'
```

### 7. Delete a Room
```bash
curl -X DELETE http://localhost:8080/smart-campus-api/api/v1/rooms/1
```

---

## ⚠ Error Response Format

All error responses follow this JSON structure:

```json
{
    "error": "Error Type",
    "message": "Detailed error message",
    "status": 400,
    "timestamp": 1776704764035
}
```

| Status Code | Meaning | Scenario |
|------------|--------|----------|
| 400 | Bad Request | Invalid JSON or missing required fields |
| 403 | Forbidden | Sensor in MAINTENANCE mode cannot accept readings |
| 404 | Not Found | Resource with specified ID does not exist |
| 409 | Conflict | Cannot delete room with active sensors |
| 415 | Unsupported Media Type | Content-Type is not application/json |
| 422 | Unprocessable Entity | Valid JSON but roomId references non-existent room |
| 500 | Internal Server Error | Unexpected server error (stack trace hidden) |

---

##  Coursework Report Answers

### Part 1.1 - JAX-RS Lifecycle
The default lifecycle of a JAX-RS resource class is request-scoped. A new instance is instantiated for every incoming request. This impacts in-memory data structures because each request gets a fresh instance, so static or singleton-scoped maps/lists must be used with proper synchronization (ConcurrentHashMap) to prevent race conditions and data loss.

### Part 1.2 - HATEOAS Benefits
Hypermedia (HATEOAS) allows clients to navigate the API dynamically through links provided in responses. This benefits client developers by reducing hardcoded URL dependencies, making the API self-documenting and more resilient to URL structure changes.

### Part 2.1 - ID-only vs Full Objects
Returning only IDs reduces network bandwidth significantly when dealing with large collections, but requires clients to make additional requests for details. Returning full objects increases bandwidth usage but reduces client-side processing and round trips.

### Part 2.2 - DELETE Idempotency
DELETE is idempotent in this implementation. The first request removes the resource and returns 204 No Content. Subsequent identical requests return 404 Not Found. The server state after multiple identical DELETE calls remains the same - the resource no longer exists.

### Part 3.1 - @Consumes Mismatch
If a client sends data in a different format to a method annotated with @Consumes(MediaType.APPLICATION_JSON), JAX-RS returns HTTP 415 Unsupported Media Type. The framework cannot deserialize the payload and rejects the request before reaching the resource method.

### Part 3.2 - @QueryParam vs PathParam
Query parameters are superior for filtering because they are optional, order-independent, and can be combined easily. Path parameters suggest a hierarchical resource structure. Using query strings for filters follows REST conventions and supports multiple filters without cluttering the URL path.

### Part 4.1 - Sub-Resource Locator Pattern
The sub-resource locator pattern delegates nested resource logic to dedicated classes, reducing complexity in large APIs. Instead of one massive controller, separate classes manage specific sub-resources, improving maintainability, separation of concerns, and code reusability.

### Part 4.2 - Side Effect Implementation
When a new reading is POSTed to /sensors/{id}/readings, the parent Sensor's currentValue field is updated automatically to maintain data consistency. This ensures the sensor always reflects its latest reading without requiring a separate update operation.

### Part 5.1 - HTTP 422 vs 404
HTTP 422 Unprocessable Entity is more semantically accurate than 404 when a JSON payload references a non-existent resource. 404 indicates the endpoint itself doesn't exist, while 422 indicates the request syntax is correct but semantic validation failed due to invalid referenced data.

### Part 5.2 - Stack Trace Security Risks
Exposing internal Java stack traces reveals sensitive information to attackers: internal package structures, library versions, file paths, line numbers, and business logic flow. This information helps attackers craft targeted exploits and identify vulnerable code paths.

### Part 5.3 - JAX-RS Filters for Logging
Using JAX-RS filters for cross-cutting concerns like logging centralizes logic in one place rather than scattering Logger.info() calls across every resource method. Filters provide automatic invocation for ALL requests/responses, ensure consistency, reduce code duplication, and separate infrastructure concerns from business logic.

---

##  Video Demonstration

A video demonstration showing Postman tests for all endpoints is included with the submission. The video covers:

Starting Tomcat server  
Testing Discovery endpoint  
Creating and retrieving rooms  
Creating and filtering sensors  
Adding sensor readings  
Demonstrating error handling (409, 422, 403)  
Deleting resources

---

##  Author
Student Name: A.G.Thisal Sandayuru  
Student ID: 20231581  
Course: 5COSC022W Client-Server Architectures  
University: University of Westminster  
Submission Date: 24th of April 2026

---
