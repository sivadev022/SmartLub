package com.sivaram.internhub.controller;

import com.sivaram.internhub.dto.JobDto;
import com.sivaram.internhub.entity.Job;
import com.sivaram.internhub.entity.JobType;
import com.sivaram.internhub.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobRestController {

    private final JobService jobService;

    @GetMapping
    public Page<Job> jobs(@RequestParam(defaultValue = "") String keyword,
                          @RequestParam(required = false) JobType jobType,
                          @RequestParam(required = false) Integer experience,
                          @RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return jobService.searchJobs(keyword, jobType, experience, pageable);
    }

    @GetMapping("/all")
    public List<Job> allJobs() {
        return jobService.getAllJobs();
    }

    @GetMapping("/{id}")
    public Job job(@PathVariable Long id) {
        return jobService.getJobById(id);
    }

    @PostMapping
    public ResponseEntity<Job> createJob(@Valid @RequestBody JobDto jobDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobService.createJob(jobDto));
    }

    @PutMapping("/{id}")
    public Job updateJob(@PathVariable Long id, @Valid @RequestBody JobDto jobDto) {
        return jobService.updateJob(id, jobDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }
}
