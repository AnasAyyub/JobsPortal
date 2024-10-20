package com.jap.jobms.job.impl;

import com.jap.jobms.job.Job;
import com.jap.jobms.job.JobRepository;
import com.jap.jobms.job.JobService;
import com.jap.jobms.job.dto.JobDTO;
import com.jap.jobms.job.external.Company;
import com.jap.jobms.job.external.Review;
import com.jap.jobms.job.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
    public List<JobDTO> findAll() {
        List<Job> jobs=jobRepository.findAll();

        List<JobDTO> jobWithCompanyDTOs=new ArrayList<JobDTO>();

        for (Job job:jobs){
            JobDTO jobWithCompanyDTO=convertToJobWithCompanyDTO(job);
            jobWithCompanyDTOs.add(jobWithCompanyDTO);
        }
        return jobWithCompanyDTOs;
    }

    public JobDTO convertToJobWithCompanyDTO(Job job) {

        Company company=restTemplate.getForObject("http://COMPANYMS:8081/companies/"+job.getCompanyId(),
                Company.class);
        ResponseEntity<List<Review>> reviewsResponse= restTemplate.exchange("http://REVIEWMS:8083/reviews?companyId="+job.getCompanyId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Review>>() {});
        List<Review> reviews=reviewsResponse.getBody();
        JobDTO jobWithCompanyDTO= JobMapper.mapToJobWithCompanyDTO(job,company,reviews);

        return jobWithCompanyDTO;
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public JobDTO findById(Long id){
        Job job=jobRepository.findById(id).orElse(null);
        return convertToJobWithCompanyDTO(job);
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
