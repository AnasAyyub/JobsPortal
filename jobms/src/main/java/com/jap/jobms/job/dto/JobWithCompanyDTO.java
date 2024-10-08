package com.jap.jobms.job.dto;

import com.jap.jobms.job.Job;
import com.jap.jobms.job.external.Company;

public class JobWithCompanyDTO {

    private Job job;

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    private Company company;
}
