package com.sivaram.internhub.config;

import com.sivaram.internhub.entity.Job;
import com.sivaram.internhub.entity.JobType;
import com.sivaram.internhub.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final JobRepository jobRepository;

    @Override
    public void run(String... args) {
        List<Job> demoJobs = List.of(
                Job.builder()
                        .title("Java Spring Boot Developer")
                        .company("TechNova Solutions")
                        .location("Hyderabad")
                        .salary(650000.0)
                        .experience(1)
                        .jobType(JobType.FULL_TIME)
                        .description("Work on backend REST APIs using Java, Spring Boot, JPA, and PostgreSQL for business applications.")
                        .skills(List.of("Java", "Spring Boot", "REST API", "PostgreSQL"))
                        .postedDate(LocalDate.now().minusDays(2))
                        .active(true)
                        .build(),
                Job.builder()
                        .title("Frontend Developer Intern")
                        .company("PixelCraft Labs")
                        .location("Bengaluru")
                        .salary(25000.0)
                        .experience(0)
                        .jobType(JobType.INTERNSHIP)
                        .description("Build responsive web pages, reusable UI components, and interactive screens using modern frontend tools.")
                        .skills(List.of("HTML", "CSS", "JavaScript", "Bootstrap"))
                        .postedDate(LocalDate.now().minusDays(4))
                        .active(true)
                        .build(),
                Job.builder()
                        .title("Remote Full Stack Developer")
                        .company("CloudWorks Digital")
                        .location("Remote")
                        .salary(900000.0)
                        .experience(2)
                        .jobType(JobType.REMOTE)
                        .description("Develop complete web features from database design to API implementation and frontend integration.")
                        .skills(List.of("Java", "Spring Boot", "React", "MySQL"))
                        .postedDate(LocalDate.now().minusDays(7))
                        .active(true)
                        .build(),
                Job.builder()
                        .title("Data Analyst")
                        .company("Insight Metrics")
                        .location("Chennai")
                        .salary(550000.0)
                        .experience(1)
                        .jobType(JobType.FULL_TIME)
                        .description("Analyze business data, prepare reports, create dashboards, and support teams with data-driven decisions.")
                        .skills(List.of("SQL", "Excel", "Power BI", "Python"))
                        .postedDate(LocalDate.now().minusDays(10))
                        .active(true)
                        .build(),
                Job.builder()
                        .title("QA Automation Engineer")
                        .company("QualityFirst Systems")
                        .location("Pune")
                        .salary(700000.0)
                        .experience(2)
                        .jobType(JobType.CONTRACT)
                        .description("Create and maintain automated test scripts for web applications and verify release quality.")
                        .skills(List.of("Selenium", "Java", "JUnit", "API Testing"))
                        .postedDate(LocalDate.now().minusDays(12))
                        .active(true)
                        .build(),
                Job.builder()
                        .title("Part Time UI Designer")
                        .company("DesignNest Studio")
                        .location("Coimbatore")
                        .salary(30000.0)
                        .experience(1)
                        .jobType(JobType.PART_TIME)
                        .description("Design clean user interfaces, improve page layouts, and prepare visual assets for product screens.")
                        .skills(List.of("Figma", "UI Design", "Wireframes", "Prototyping"))
                        .postedDate(LocalDate.now().minusDays(15))
                        .active(true)
                        .build()
        );

        demoJobs.stream()
                .filter(job -> !jobRepository.existsByTitleAndCompany(job.getTitle(), job.getCompany()))
                .forEach(jobRepository::save);
    }
}
