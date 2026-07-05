package com.sivaram.internhub.controller;

import com.sivaram.internhub.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final JobService jobService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("jobCount", jobService.countJobs());
        model.addAttribute("companyCount", jobService.countCompanies());
        model.addAttribute("activeJobs", jobService.countActiveJobs());
        return "home";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/favicon.ico")
    public ResponseEntity<Void> favicon() {
        return ResponseEntity.noContent().build();
    }
}
