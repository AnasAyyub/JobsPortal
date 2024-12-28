package com.jap.companyms.company.impl;

import com.jap.companyms.company.Company;
import com.jap.companyms.company.CompanyRepository;
import com.jap.companyms.company.CompanyService;
import com.jap.companyms.company.clients.ReviewClient;
import com.jap.companyms.company.dto.ReviewMessage;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;
private ReviewClient reviewClient;

    public CompanyServiceImpl(CompanyRepository companyRepository,ReviewClient reviewClient) {

        this.companyRepository = companyRepository;
        this.reviewClient = reviewClient;
    }

    @Override
    public List<Company> getAllCompanies() {

        return companyRepository.findAll();
    }

    @Override
    public boolean updateCompany(Company company, Long id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);

        if (optionalCompany.isPresent()) {
            Company fetchedCompany = optionalCompany.get();
            fetchedCompany.setName(company.getName());
            fetchedCompany.setDescription(company.getDescription());
            companyRepository.save(fetchedCompany);
            return true;
        }

        return false;


    }

    @Override
    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public boolean deleteCompany(Long id) {
        try{
            companyRepository.deleteById(id);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
        Company company=companyRepository.findById(reviewMessage.getCompanyId())
                .orElseThrow(()-> new NotFoundException("Company not found" + reviewMessage.getCompanyId()));

        Double rating=reviewClient.getAverageRating(reviewMessage.getCompanyId());

        company.setRating(rating);
        companyRepository.save(company);
    }

}
