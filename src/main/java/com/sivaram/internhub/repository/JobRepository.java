package com.sivaram.internhub.repository;

import com.sivaram.internhub.entity.Job;
import com.sivaram.internhub.entity.JobType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByTitleContainingIgnoreCase(String title);

    List<Job> findByCompanyContainingIgnoreCase(String company);

    @Query("""
            select distinct job from Job job
            join job.skills skill
            where lower(skill) like lower(concat('%', :skill, '%'))
            """)
    List<Job> findBySkillsContaining(@Param("skill") String skill);

    List<Job> findByExperienceGreaterThanEqual(Integer experience);

    List<Job> findByJobType(JobType jobType);

    boolean existsByTitleAndCompany(String title, String company);

    @Query("""
            select job from Job job
            where (:jobType is null or job.jobType = :jobType)
            and (:experience is null or job.experience >= :experience)
            and job.active = true
            """)
    Page<Job> filterActiveJobs(@Param("jobType") JobType jobType,
                               @Param("experience") Integer experience,
                               Pageable pageable);

    @Query("""
            select distinct job from Job job
            left join job.skills skill
            where (:keyword is null
                or lower(job.title) like lower(concat('%', :keyword, '%'))
                or lower(job.company) like lower(concat('%', :keyword, '%'))
                or lower(job.location) like lower(concat('%', :keyword, '%'))
                or lower(skill) like lower(concat('%', :keyword, '%')))
            and (:jobType is null or job.jobType = :jobType)
            and (:experience is null or job.experience >= :experience)
            and job.active = true
            """)
    Page<Job> searchJobs(@Param("keyword") String keyword,
                         @Param("jobType") JobType jobType,
                         @Param("experience") Integer experience,
                         Pageable pageable);
}
