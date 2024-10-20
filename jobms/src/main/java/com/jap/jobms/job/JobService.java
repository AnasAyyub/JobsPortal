package com.jap.jobms.job;

import com.jap.jobms.job.dto.JobDTO;

import java.util.List;

public interface JobService {
    List<JobDTO> findAll();
    void createJob(Job job);
    JobDTO findById(Long id);
    boolean deleteById(Long id);

    boolean update(Long id, Job job);
}
