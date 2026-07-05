package com.sivaram.internhub.service;

import com.sivaram.internhub.dto.JobDto;
import com.sivaram.internhub.entity.Job;
import com.sivaram.internhub.entity.JobType;
import com.sivaram.internhub.exception.JobNotFoundException;
import com.sivaram.internhub.repository.JobRepository;
import com.sivaram.internhub.util.SkillUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class JobServiceImpl implements JobService {

    private static final Logger log = LoggerFactory.getLogger(JobServiceImpl.class);

    private final JobRepository jobRepository;

    @Override
    public Job createJob(JobDto jobDto) {
        Job job = new Job();
        copyDtoToJob(jobDto, job);
        Job savedJob = jobRepository.save(job);
        log.info("Created job with id {}", savedJob.getId());
        return savedJob;
    }

    @Override
    public Job updateJob(Long id, JobDto jobDto) {
        Job job = getJobById(id);
        copyDtoToJob(jobDto, job);
        Job updatedJob = jobRepository.save(job);
        log.info("Updated job with id {}", updatedJob.getId());
        return updatedJob;
    }

    @Override
    public void deleteJob(Long id) {
        Job job = getJobById(id);
        jobRepository.delete(job);
        log.info("Deleted job with id {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElseThrow(() -> new JobNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Job> getJobs(Pageable pageable) {
        return jobRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Job> searchJobs(String keyword, JobType jobType, Integer experience, Pageable pageable) {
        String cleanKeyword = keyword == null || keyword.isBlank() ? null : keyword.trim();
        log.info("Searching jobs with keyword {}, jobType {}, experience {}", cleanKeyword, jobType, experience);
        if (cleanKeyword == null) {
            return jobRepository.filterActiveJobs(jobType, experience, pageable);
        }
        return searchJobsInMemory(cleanKeyword, jobType, experience, pageable);
    }

    @Override
    public JobDto toDto(Job job) {
        JobDto jobDto = new JobDto();
        jobDto.setId(job.getId());
        jobDto.setTitle(job.getTitle());
        jobDto.setCompany(job.getCompany());
        jobDto.setLocation(job.getLocation());
        jobDto.setSalary(job.getSalary());
        jobDto.setExperience(job.getExperience());
        jobDto.setJobType(job.getJobType());
        jobDto.setDescription(job.getDescription());
        jobDto.setSkillsText(SkillUtils.toText(job.getSkills()));
        jobDto.setActive(job.getActive());
        return jobDto;
    }

    @Override
    @Transactional(readOnly = true)
    public long countJobs() {
        return jobRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public long countActiveJobs() {
        return jobRepository.findAll().stream()
                .filter(job -> Boolean.TRUE.equals(job.getActive()))
                .count();
    }

    @Override
    @Transactional(readOnly = true)
    public long countCompanies() {
        return jobRepository.findAll().stream()
                .map(Job::getCompany)
                .distinct()
                .count();
    }

    private void copyDtoToJob(JobDto jobDto, Job job) {
        job.setTitle(jobDto.getTitle().trim());
        job.setCompany(jobDto.getCompany().trim());
        job.setLocation(jobDto.getLocation().trim());
        job.setSalary(jobDto.getSalary());
        job.setExperience(jobDto.getExperience());
        job.setJobType(jobDto.getJobType());
        job.setDescription(jobDto.getDescription().trim());
        job.setSkills(SkillUtils.fromText(jobDto.getSkillsText()));
        job.setActive(Boolean.TRUE.equals(jobDto.getActive()));
    }

    private Page<Job> searchJobsInMemory(String keyword, JobType jobType, Integer experience, Pageable pageable) {
        String searchTerm = keyword.toLowerCase(Locale.ROOT);
        List<Job> filteredJobs = jobRepository.findAll().stream()
                .filter(job -> Boolean.TRUE.equals(job.getActive()))
                .filter(job -> jobType == null || job.getJobType() == jobType)
                .filter(job -> experience == null || job.getExperience() >= experience)
                .filter(job -> matchesKeyword(job, searchTerm))
                .sorted(getComparator(pageable.getSort()))
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filteredJobs.size());
        List<Job> pageContent = start >= filteredJobs.size() ? List.of() : filteredJobs.subList(start, end);
        return new PageImpl<>(pageContent, pageable, filteredJobs.size());
    }

    private boolean matchesKeyword(Job job, String searchTerm) {
        return contains(job.getTitle(), searchTerm)
                || contains(job.getCompany(), searchTerm)
                || contains(job.getLocation(), searchTerm)
                || job.getSkills().stream().anyMatch(skill -> contains(skill, searchTerm));
    }

    private boolean contains(String value, String searchTerm) {
        return value != null && value.toLowerCase(Locale.ROOT).contains(searchTerm);
    }

    private Comparator<Job> getComparator(Sort sort) {
        Comparator<Job> comparator = Comparator.comparing(Job::getPostedDate, Comparator.nullsLast(Comparator.naturalOrder()));

        for (Sort.Order order : sort) {
            comparator = switch (order.getProperty()) {
                case "title" -> Comparator.comparing(Job::getTitle, String.CASE_INSENSITIVE_ORDER);
                case "company" -> Comparator.comparing(Job::getCompany, String.CASE_INSENSITIVE_ORDER);
                case "salary" -> Comparator.comparing(Job::getSalary, Comparator.nullsLast(Comparator.naturalOrder()));
                case "experience" -> Comparator.comparing(Job::getExperience, Comparator.nullsLast(Comparator.naturalOrder()));
                default -> Comparator.comparing(Job::getPostedDate, Comparator.nullsLast(Comparator.naturalOrder()));
            };
            if (order.isDescending()) {
                comparator = comparator.reversed();
            }
            break;
        }

        return comparator;
    }
}
