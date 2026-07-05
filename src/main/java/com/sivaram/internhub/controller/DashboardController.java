package com.sivaram.internhub.controller;

import com.sivaram.internhub.entity.JobType;
import com.sivaram.internhub.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final JobService jobService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalJobs", jobService.countJobs());
        model.addAttribute("activeJobs", jobService.countActiveJobs());
        model.addAttribute("companies", jobService.countCompanies());
        model.addAttribute("jobTypes", JobType.values());
        model.addAttribute("recentJobs", jobService.getAllJobs().stream().limit(6).toList());
        return "dashboard";
    }
}
