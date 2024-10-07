package com.jap.jobms.job.impl;

import com.jap.jobms.job.Job;
import com.jap.jobms.job.JobRepository;
import com.jap.jobms.job.JobService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {
    private JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }


    @Override
    public List<Job> findAll() {

        return jobRepository.findAll();
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public Job findById(Long id){

        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteById(Long id){
       try{
           jobRepository.deleteById(id);
           return true;
       }
       catch(Exception e){
           return false;
       }

    }

    @Override
    public boolean update(Long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);

            if (jobOptional.isPresent()){
                Job job=jobOptional.get();
                job.setDescription(updatedJob.getDescription());
                job.setTitle(updatedJob.getTitle());
                job.setLocation(updatedJob.getLocation());
                job.setMaxSalary(updatedJob.getMaxSalary());
                job.setMinSalary(updatedJob.getMinSalary());
                job.setCompanyId(updatedJob.getCompanyId());
                jobRepository.save(job);
                return true;
            }


        return false;
    }
}
