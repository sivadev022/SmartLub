package com.sivaram.internhub.dto;

import com.sivaram.internhub.entity.JobType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobDto {

    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Company is required")
    private String company;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Salary is required")
    @DecimalMin(value = "0.1", message = "Salary must be positive")
    private Double salary;

    @NotNull(message = "Experience is required")
    @Min(value = 0, message = "Experience cannot be negative")
    private Integer experience;

    @NotNull(message = "Job type is required")
    private JobType jobType;

    @NotBlank(message = "Description is required")
    @Size(min = 30, message = "Description must be at least 30 characters")
    private String description;

    @NotBlank(message = "At least one skill is required")
    private String skillsText;

    private Boolean active = true;
}
