package com.sivaram.internhub.service;

import com.sivaram.internhub.dto.JobDto;
import com.sivaram.internhub.entity.Job;
import com.sivaram.internhub.entity.JobType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobService {

    Job createJob(JobDto jobDto);

    Job updateJob(Long id, JobDto jobDto);

    void deleteJob(Long id);

    Job getJobById(Long id);

    List<Job> getAllJobs();

    Page<Job> getJobs(Pageable pageable);

    Page<Job> searchJobs(String keyword, JobType jobType, Integer experience, Pageable pageable);

    JobDto toDto(Job job);

    long countJobs();

    long countActiveJobs();

    long countCompanies();
}
