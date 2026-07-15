# GrindHub Architecture

## I. Project structure

```text
HealthApp
│
├── src
    ├── main
        ├── java
            ├── com.HealthApp
                    ├── config: Configuration classes such as CORS and security
                    ├── controller: Receives HTTP requests and returns HTTP responses
                    ├── exception: Format exception messages for the whole application
                    ├── model: Defines entities and relationships
                    ├── repo: Communicates with the database using Spring Data JPA
                    ├── service: Business logic, validation, rules
                    └── HealthAppApplication: Run this to run the backend

├── frontend
    ├── public
    ├── src
        ├── pages: Contains pages such as Login, Signup, etc
        ├── components: Reusable components such as Navbar and Footer
        ├── services: API communication (communicate with backend)
        ├── assets: static resources, CSS, images
    ├── App.jsx
    └── main.jsx

├── docs
    ├── Architecture.md
    ├── API.md
    ├── Decisions.md
    └── Database.md
│
└── README.md
```

## II. Overview

GrindHub is a full-stack fitness platform built using a separated frontend and backend architecture. The front end communicates with the backend through REST APIs using HTTP requests.

### 1. Frontend

- React
- React Router
- CSS

### 2. Backend

- Spring Boot
- Spring Data JPA
- Hibernate

### 3. Database:

- MySQL

## III. How the system works

### 1. Overall

                User
                  |
                  ▼
        React Frontend (5173)
                  │
        HTTP Requests (JSON)
                  │
                  ▼
      Spring Boot REST API (8080)
                  │
         Controller Layer
                  │
                  ▼
          Service Layer
                  │
                  ▼
       Repository Layer (JPA)
                  │
                  ▼
              MySQL Database

### 2. Layer Responsibilities

#### a. React

Responsible for:

- UI
- Forms
- Routing
- Sending API requests
- Displaying data

Example: Signup.jsx -> createClient() / createCoach()

### b. Controller

Responsible for:

- Receiving HTTP requests
- Calling the correct service
- Returning responses

Example: POST /api/clients -> ClientController

### c. Service

Responsible for:

- Business logic
- Validation
- Relationships

Example: Validate Client -> Save Client -> Return Client

### d. Database

Stores Client, Coach, Certificate, MedicalHistory, Exercises, Workout