package com.jap.jobms.job.impl;

import com.jap.jobms.job.Job;
import com.jap.jobms.job.JobRepository;
import com.jap.jobms.job.JobService;
import com.jap.jobms.job.dto.JobWithCompanyDTO;
import com.jap.jobms.job.external.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {
    private JobRepository jobRepository;

    @Autowired
    private RestTemplate restTemplate;
    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }


    @Override
    public List<JobWithCompanyDTO> findAll() {
        List<Job> jobs=jobRepository.findAll();

        List<JobWithCompanyDTO> jobWithCompanyDTOs=new ArrayList<JobWithCompanyDTO>();

        for (Job job:jobs){
            JobWithCompanyDTO jobWithCompanyDTO=new JobWithCompanyDTO();
            jobWithCompanyDTO.setJob(job);

            Company company=restTemplate.getForObject("http://COMPANYMS:8081/companies/"+job.getCompanyId(),
                    Company.class);

            jobWithCompanyDTO.setCompany(company);

            jobWithCompanyDTOs.add(jobWithCompanyDTO);
        }
        return jobWithCompanyDTOs;
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
