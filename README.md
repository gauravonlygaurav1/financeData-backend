
# Finance Dashboard Backend

This project is a backend system for a Finance Dashboard that manages financial records, user roles, and provides analytics.

It is designed to demonstrate backend engineering skills including API design, data modeling, role-based access control, dashboard analytics, spring security and jwt.

The system allows different users (Admin, Analyst, Viewer) to interact with financial data based on their permissions.

## API Documentation
We can explore and test all APIs using my published Postman documentation:
  https://documenter.getpostman.com/view/46277604/2sBXirhTU1

OR

Import the Postman collection from:
 `/postman/finance-data.postman_collection.json`

## Tech Stack

**Client:** PostMan (API testing)

**Server:** Java, Spring Boot (REST APIs)

**Database:** MySQL

**Authentication:** JWT(JSON Web Token)

**ORM:** Spring Data JPA/ Hibernate


## Features

### User Management
- User creation and Management
- Role based access(ADMIN, ANALYST, VIEWER)
- Active/Inactive user status
- Secure authentication using JWT

### Authentication & Authorization
- Login API with JWT token generation
- Secured endpoints using Spring Security
- Role-based access control enforced at backend
- Stateless authentication using JWT

### Financial Records Management
- Create, Read, Update, Delete (CRUD) operations
- Each record is linked to a specific user
- Fields include:
    - Amount
    - Type(INCOME/ EXPENSE)
    - Category
    - Date
    - Description

### Dashboard APIs
Provide aggregated data for frontend dashboards:
- Total Income
- Total Expense
- Net Balance
- Category-wise Summary
- Monthly Trends
- Recent Transactions

### Filtering & Search
- Filter records by:
    - Type
    - Category
    - Data range

### Validation & Error Handling
- Input validation using annotations
- Global exception handling
- Proper HTTP status codes and error responses
## Project Structure
    controller → Handles HTTP requests
    service → Business logic
    repository → Database interaction
    entity → Database models
    dto → Request/Response objects 
    exception → Custom exceptions & handlers
    security → JWT & security configuration
## Role-Based Access Control
| Role     | Permissions                          |
|----------|--------------------------------------|
ADMIN    | Full access (users + records)        
ANALYST  | View records + dashboard             
VIEWER   | View dashboard only                  
## Authentication Flow
    1. User logs in using credentials
    2. Server generates JWT token
    3. Token is sent in Authorization header:
    4. Authorization: Bearer <token>
    5. Backend validates token for each request
    6. Access is granted based on user role
## API Endpoints

### Users
- POST `/users/create` -> Create user(Admin only)
- GET `/users/list` -> Get all users

#### Login
- POST `/users/login` -> Login & get JWT

### Financial Records
- POST `/records/add` → Create record
- GET `/records/all` → Get all records
- PUT `/records/{id}` → Update record
- DELETE `/records/{id}` → Delete record
- GET `/records/filter` → Filter records

### Dashboard
- GET `/dashboard/summary` → Total income/expense
- GET `/dashboard/category` → Category-wise data
- GET `/dashboard/trends` → Monthly trends



## Setup & Run

Clone the repository

```bash
  git clone 
  cd finance-data
```

Configure Database

```bash
Create MySQL database:
 CREATE DATABASE finance_db;

Update application.yaml:
    server:
  port: 8082

spring:
  application:
    name: financeData

  datasource:
    url: jdbc:mysql://localhost:3306/finance_data
    username: ${DB_USERNAME:your_username}
    password: ${DB_PASSWORD:your_password}

  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQLDialect
        format_sql: true
    show-sql: true
```

Run Application

```bash
  mvn spring-boot:run
```

Test APIs

```bash
  Use Postman or any API client
```


## Assumptions
- JWT authentication is used instead of session-based authentication

- Role-based access is enforced at backend level

- Each financial record belongs to a user

- Simplified authentication is used for demonstration purposes