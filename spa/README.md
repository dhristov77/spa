# PoC for a SPA

This project is a Single Page Application built with:

- **Frontend:** React + Vite
- **Backend:** Spring Boot
- **Database:** H2 (in-memory, configured in application.properties)

Creates items and lists them in a table.

---

## Prerequisites

- Node.js v24.14.1
- npm 11.11.0
- Java 21.0.5
- Maven 3.3.9

---

## Backend (Spring Boot)

Navigate to the backend folder:

```bash
cd spa/src
```
 - Default server port: 8080  
 - Default server port can be changed at:

```text 
src/main/resources/application.properties
```
---

## Frontend (React + Vite)

Navigate to the frontend folder:

```bash
cd spa/frontend/interview-react-app
```

 - Frontend calls the REST API on port: 8080  
 - REST API port can be changed at:
 
```text 
frontend/interview-react-app/src/api.jsx
```

---

## Security and Role-Based Access

This SPA implements **JWT token authentication** and **role-based authorization** in the backend.

### Authentication

- The application uses **JWT tokens** for authentication.  
- Tokens expire after **10 minutes**.  

### Access Rules

- Registration and login endpoints are public.
- **USER** and **ADMIN** can perform `GET` requests on items.  
- Only **ADMIN** can perform `POST` and `DELETE` requests on items.


### Preconfigured Users

- Username **user** Role **USER**  

- Username **admin** Role **ADMIN**

Created on startup (DataLoader).

### Frontend Credentials

- By default, the frontend uses **admin** credentials.  
- You can change the credentials in the frontend at:

```text
spa/frontend/interview-react-app/src/useAuth.js
```

---

## REST API Endpoints

### Auth Endpoints

| Method | Endpoint          | Roles Allowed | Description                    |

|--------|-------------------|---------------|--------------------------------|

| POST   | /auth/register    | PUBLIC        | Register a new user            |

| POST   | /auth/login       | PUBLIC        | Login and receive JWT token    |


### Item Endpoints

| Method | Endpoint        | Roles Allowed | Description                     |

|--------|----------------|---------------|---------------------------------|

| GET    | /items         | USER, ADMIN   | Get all items                   |

| POST   | /items         | ADMIN         | Create a new item               |

| DELETE | /items/{id}    | ADMIN         | Delete an item by ID            |

 