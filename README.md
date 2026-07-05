# SmartLub

SmartLub is a full-stack job management web application built with Spring Boot, Thymeleaf, PostgreSQL, and Bootstrap. It gives users a clean interface to create, browse, search, filter, update, and manage job listings, with a dashboard and REST API included.

**Live Demo:** https://smartlub.onrender.com/

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.6-brightgreen)
![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-336791)
![Thymeleaf](https://img.shields.io/badge/View-Thymeleaf-005F0F)
![Docker](https://img.shields.io/badge/Deploy-Docker-2496ED)

## Overview

SmartLub is designed as a polished Java full-stack portfolio project. It combines server-rendered MVC pages with a REST API, persistent PostgreSQL storage, validation, pagination, sorting, search, and deployment-ready Docker configuration.

The application is currently deployed on Render and uses Neon PostgreSQL for cloud database hosting.

## Features

- Add, edit, delete, and view job listings
- Search jobs by title, company, location, and skill
- Filter jobs by job type and minimum experience
- Sort jobs by posted date, salary, experience, and title
- Paginated job listing page
- Dashboard with job statistics
- Form validation with user-friendly error messages
- REST API for job management
- Responsive Bootstrap 5 UI
- Custom 404 and 500 error pages
- PostgreSQL persistence with Spring Data JPA
- Docker-ready deployment setup

## Tech Stack

| Layer | Technology |
| --- | --- |
| Backend | Java 21, Spring Boot 3.3.6 |
| Web | Spring MVC, Thymeleaf |
| Database | PostgreSQL |
| ORM | Spring Data JPA, Hibernate |
| UI | Bootstrap 5, CSS, JavaScript |
| Build Tool | Maven |
| Deployment | Docker, Render |
| Cloud Database | Neon PostgreSQL |

## Live Routes

| Page | URL |
| --- | --- |
| Home | `/` |
| Jobs | `/jobs` |
| Add Job | `/jobs/add` |
| Dashboard | `/dashboard` |
| About | `/about` |
| Contact | `/contact` |

## REST API

Base path:

```text
/api/jobs
```

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/api/jobs` | Get paginated and filtered jobs |
| GET | `/api/jobs/all` | Get all jobs |
| GET | `/api/jobs/{id}` | Get job by ID |
| POST | `/api/jobs` | Create a new job |
| PUT | `/api/jobs/{id}` | Update a job |
| DELETE | `/api/jobs/{id}` | Delete a job |

Sample request:

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

## Project Structure

```text
src/main/java/com/sivaram/internhub
|-- config
|-- controller
|-- dto
|-- entity
|-- exception
|-- repository
|-- service
`-- util

src/main/resources
|-- static
|   |-- css
|   |-- images
|   `-- js
|-- templates
`-- application.properties
```

## Run Locally

### Prerequisites

- Java 21
- Maven
- PostgreSQL

### Database Setup

Create a local PostgreSQL database:

```sql
CREATE DATABASE internhub;
```

Set environment variables:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/internhub
export SPRING_DATASOURCE_USERNAME=postgres
export SPRING_DATASOURCE_PASSWORD=postgres
```

Start the application:

```bash
mvn spring-boot:run
```

Open:

```text
http://localhost:8080
```

## Docker

Build the image:

```bash
docker build -t smartlub .
```

Run the container:

```bash
docker run --rm -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/internhub \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=postgres \
  smartlub
```

## Deployment

The live deployment uses:

- Render Web Service for the Spring Boot Docker app
- Neon PostgreSQL for the database
- GitHub auto-deploy from the `main` branch

Required production environment variables:

```env
SPRING_DATASOURCE_URL=jdbc:postgresql://YOUR_NEON_HOST/YOUR_DATABASE?sslmode=require
SPRING_DATASOURCE_USERNAME=YOUR_NEON_USERNAME
SPRING_DATASOURCE_PASSWORD=YOUR_NEON_PASSWORD
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_THYMELEAF_CACHE=true
```

Render should use:

```text
Runtime: Docker
Branch: main
Instance Type: Free
Root Directory: empty
```

## Notes

- Render free services may sleep after inactivity, so the first request can take a little longer.
- Database credentials should only be stored as environment variables.
- Do not commit `.env` files or database passwords to GitHub.

## Author

**SivaRamaKrishna**  
GitHub: [sivadev022](https://github.com/sivadev022)
