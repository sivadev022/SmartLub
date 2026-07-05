# SmartLub

SmartLub is a Spring Boot job management project built with MVC, Thymeleaf, PostgreSQL and Bootstrap 5. It is designed as a strong Java full stack fresher portfolio project with clean CRUD screens, validation, search, filters, pagination, sorting and REST APIs.

## Tech Stack

- Java 21
- Spring Boot
- Spring MVC
- Spring Data JPA
- PostgreSQL
- Thymeleaf
- Bootstrap 5
- Lombok
- Hibernate
- Maven

## Features

- Create, update, delete and view jobs
- Search jobs by title, company, location and skill
- Filter jobs by job type and minimum experience
- Sort jobs by posted date, salary, experience and title
- Pagination for job listing
- Dashboard statistics
- Form validation with friendly messages
- Global exception handling
- REST API for job management
- Responsive Bootstrap UI
- Empty state, success alerts and error pages

## Folder Structure

```text
src/main/java/com/sivaram/internhub
├── config
├── controller
├── dto
├── entity
├── exception
├── repository
├── service
└── util

src/main/resources
├── static
│   ├── css
│   ├── images
│   └── js
├── templates
└── application.properties
```

## Database Setup

Create a PostgreSQL database named `internhub`.

```sql
CREATE DATABASE internhub;
```

Update environment variables if your local PostgreSQL credentials are different.

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/internhub
export SPRING_DATASOURCE_USERNAME=postgres
export SPRING_DATASOURCE_PASSWORD=postgres
```

## Run Locally

```bash
mvn spring-boot:run
```

Open the application at:

```text
http://localhost:8080
```

## REST API

```text
GET    /api/jobs
GET    /api/jobs/all
GET    /api/jobs/{id}
POST   /api/jobs
PUT    /api/jobs/{id}
DELETE /api/jobs/{id}
```

Sample request body:

```json
{
  "title": "Java Developer Intern",
  "company": "TechNova",
  "location": "Hyderabad",
  "salary": 25000,
  "experience": 0,
  "jobType": "INTERNSHIP",
  "description": "Work with the backend team to build Spring Boot APIs and learn production workflows.",
  "skillsText": "Java, Spring Boot, PostgreSQL",
  "active": true
}
```

## Screenshots

Add screenshots in `src/main/resources/static/images` and update these placeholders after running the app.

```text
Home Page Screenshot
Dashboard Screenshot
Jobs Page Screenshot
Add Job Screenshot
Job Details Screenshot
```

## Deployment

The cleanest deployment path for this repo is Docker + PostgreSQL. The repo includes:

- `Dockerfile` for building and running the Spring Boot app
- `docker-entrypoint.sh` for converting platform PostgreSQL URLs into Spring JDBC URLs
- `render.yaml` for Render Blueprint deployment with a managed PostgreSQL database

### Deploy on Render

1. Push this repository to GitHub:

```bash
git add .
git commit -m "Add deployment configuration"
git push origin main
```

2. In Render, create a new Blueprint and select:

```text
https://github.com/sivadev022/SmartLub.git
```

3. Render will read `render.yaml`, create:

```text
smartlub      - Docker web service
smartlub-db   - PostgreSQL database
```

4. After the first deploy finishes, open the Render service URL.

The app reads the port from Render's `PORT` environment variable and the database credentials from the Blueprint.

### Deploy with Neon + Any Java Host

If your host does not use `render.yaml`, create a PostgreSQL database in Neon and set these environment variables:

```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://HOST:PORT/DATABASE?sslmode=require
SPRING_DATASOURCE_USERNAME=YOUR_DATABASE_USER
SPRING_DATASOURCE_PASSWORD=YOUR_DATABASE_PASSWORD
PORT=8080
```

Then build and run:

```bash
mvn clean package -DskipTests
java -jar target/internhub-0.0.1-SNAPSHOT.jar
```

### Docker Run Locally

```bash
docker build -t smartlub .
docker run --rm -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/internhub \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=postgres \
  smartlub
```

## Author

Sivaram
