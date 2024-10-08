package com.jap.jobms.job;

import com.jap.jobms.job.dto.JobWithCompanyDTO;

import java.util.List;

public interface JobService {
    List<JobWithCompanyDTO> findAll();
    void createJob(Job job);
    Job findById(Long id);
    boolean deleteById(Long id);

    boolean update(Long id, Job job);
}
