# Todo Web App

A full-stack Todo application with a **Java Spring Boot** backend and a **React** frontend. The project is containerized using Docker and can be run locally using `docker-compose`.

## � Technology Stack

- **Backend:** Java 17+, Spring Boot, Spring Data JPA
- **Frontend:** React, Create React App, Axios for API calls
- **Database:** PostgreSQL, H2 (for testing)
- **Containerization:** Docker, docker-compose
- **Build/Test:** Maven (backend), npm (frontend), JUnit, Jest

## ✨ Functionalities

- Create, read, update, and delete (CRUD) Todo tasks
- Task listing and detail views
- Form validation and error handling
- REST API backend with JSON responses
- Frontend consumes API and renders responsive UI

## �🧩 Project Structure

- `todowebapp_backend/` - Spring Boot backend service
- `todowebapp_frontend/` - React frontend
- `docker-compose.yml` - docker-compose stack to run backend + frontend together

## 🚀 Getting Started

### Prerequisites

- Docker & Docker Compose installed
- (Optional) Java 17+ and Maven (for local backend runs)
- (Optional) Node.js 16+ and npm/yarn (for local frontend runs)

### Run with Docker Compose (Recommended)

From the project root:

```bash
docker-compose up --build
```

This will build and start both services:

- Backend: `http://localhost:8080`
- Frontend: `http://localhost:3000`

To stop and remove containers:

```bash
docker-compose down
```

## 🧑‍💻 Backend (Spring Boot)

### Run Locally (without Docker)

From `todowebapp_backend/`:

```bash
./mvnw spring-boot:run
```

### Build JAR

```bash
./mvnw clean package
```

### Configurations

The backend uses Spring profiles. Available property files:

- `application.properties` (default)
- `application-dev.properties`
- `application-test.properties`
- `application-docker.properties`

### API Endpoints

This project contains a Todo controller under `com.todo.todowebapp.controller`.

Common endpoints (typical):

- `GET /api/tasks` — list all tasks
- `GET /api/tasks/uncompleted` — list uncompleted tasks
- `POST /api/tasks` — create task
- `POST /api/tasks/complete/{id}` — complete task
- `DELETE /api/tasks/{id}` — delete task

(Use `curl` or Postman against `http://localhost:8080`)

## 🌐 Frontend (React)

### Run Locally (without Docker)

From `todowebapp_frontend/`:

```bash
npm install
npm start
```

The app will start at `http://localhost:3000` and proxy API requests to the backend.

### Build for Production

```bash
npm run build
```

The output will be placed in `todowebapp_frontend/build/`.

## 🧪 Tests

### Backend Tests (JUnit)

From `todowebapp_backend/`:

```bash
./mvnw test
```

### Frontend Tests (Jest)

From `todowebapp_frontend/`:

```bash
npm test
```

## 📦 Deployment

This project is designed to run in containers. Use the existing `docker-compose.yml` for local deployment.

For production, build images and deploy them to your preferred container platform (e.g., Kubernetes, Azure App Service, AWS ECS).

## 🛠️ Notes & Helpful Links

- Backend source: `todowebapp_backend/src/main/java/com/todo/todowebapp`
- Frontend source: `todowebapp_frontend/src`

If you want to change the API base path or ports, update `docker-compose.yml` and the frontend proxy config in `todowebapp_frontend/package.json`.

---

If you want me to add more details (e.g., endpoint documentation, environment variables, or CI/CD guidance), just ask!