package com.sivaram.internhub.controller;

import com.sivaram.internhub.dto.JobDto;
import com.sivaram.internhub.entity.JobType;
import com.sivaram.internhub.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping("/jobs")
    public String jobs(@RequestParam(defaultValue = "") String keyword,
                       @RequestParam(required = false) JobType jobType,
                       @RequestParam(required = false) Integer experience,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "postedDate") String sortBy,
                       @RequestParam(defaultValue = "desc") String direction,
                       Model model) {
        Pageable pageable = PageRequest.of(page, 6, getSort(sortBy, direction));
        model.addAttribute("jobs", jobService.searchJobs(keyword, jobType, experience, pageable));
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedJobType", jobType);
        model.addAttribute("experience", experience);
        model.addAttribute("jobTypes", JobType.values());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);
        return "jobs";
    }

    @GetMapping("/jobs/add")
    public String addJob(Model model) {
        model.addAttribute("jobDto", new JobDto());
        model.addAttribute("jobTypes", JobType.values());
        return "add-job";
    }

    @PostMapping("/jobs/save")
    public String saveJob(@Valid @ModelAttribute JobDto jobDto,
                          BindingResult bindingResult,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("jobTypes", JobType.values());
            return "add-job";
        }
        jobService.createJob(jobDto);
        redirectAttributes.addFlashAttribute("successMessage", "Job created successfully");
        return "redirect:/jobs";
    }

    @GetMapping("/jobs/{id}")
    public String jobDetails(@PathVariable Long id, Model model) {
        model.addAttribute("job", jobService.getJobById(id));
        return "job-details";
    }

    @GetMapping("/jobs/{id}/edit")
    public String editJob(@PathVariable Long id, Model model) {
        model.addAttribute("jobDto", jobService.toDto(jobService.getJobById(id)));
        model.addAttribute("jobTypes", JobType.values());
        return "edit-job";
    }

    @PostMapping("/jobs/{id}/update")
    public String updateJob(@PathVariable Long id,
                            @Valid @ModelAttribute JobDto jobDto,
                            BindingResult bindingResult,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            jobDto.setId(id);
            model.addAttribute("jobTypes", JobType.values());
            return "edit-job";
        }
        jobService.updateJob(id, jobDto);
        redirectAttributes.addFlashAttribute("successMessage", "Job updated successfully");
        return "redirect:/jobs/" + id;
    }

    @PostMapping("/jobs/{id}/delete")
    public String deleteJob(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        jobService.deleteJob(id);
        redirectAttributes.addFlashAttribute("successMessage", "Job deleted successfully");
        return "redirect:/jobs";
    }

    private Sort getSort(String sortBy, String direction) {
        Sort sort = Sort.by(sortBy);
        return "asc".equalsIgnoreCase(direction) ? sort.ascending() : sort.descending();
    }
}
