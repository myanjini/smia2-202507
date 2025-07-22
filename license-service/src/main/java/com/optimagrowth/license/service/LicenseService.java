package com.optimagrowth.license.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.optimagrowth.license.config.ServiceConfig;
import com.optimagrowth.license.model.License;
import com.optimagrowth.license.model.Organization;
import com.optimagrowth.license.repository.LicenseRepository;
import com.optimagrowth.license.service.client.OrganizationDiscoveryClient;
import com.optimagrowth.license.service.client.OrganizationFeignClient;
import com.optimagrowth.license.service.client.OrganizationRestTemplateClient;
import com.optimagrowth.license.utils.UserContextHolder;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LicenseService {
    @Autowired
    MessageSource messages;

    @Autowired
    private LicenseRepository licenseRepository;
    
    @Autowired 
    ServiceConfig config;
    
    private void randomlyRunLong() throws TimeoutException {
    	Random rand = new Random();
    	int randNumber = rand.nextInt(3) + 1;	// 1 ~ 3
    	if (randNumber == 3)
    		sleep();
    }
    
    private void sleep() throws TimeoutException {
    	log.info("sleep() is called");
		try {
    		Thread.sleep(5000);
    		throw new java.util.concurrent.TimeoutException();
    	} catch (InterruptedException e) {
    		log.error(e.getMessage());
    	}
    }
    
    private int count = 0;
    
    @CircuitBreaker(name = "licenseService", fallbackMethod = "buildFallbackLicenseList")
    public List<License> getLicensesByOrganization(String organizationId) throws TimeoutException {
    	log.info("LicenseSerivce Correlation ID: {}", UserContextHolder.getContext().getCorrelationId());
    	
    	// randomlyRunLong();
//    	count ++;
//    	
//    	log.info(">>> getLicensesByOrganization ... " + count);
//    	try {
//    		Thread.sleep(3000);
//    		throw new java.util.concurrent.TimeoutException();
//    	} catch (InterruptedException e) { }
//    	
//    	log.info(">>> findByOrganizationId ... " + count);
    	
    	return licenseRepository.findByOrganizationId(organizationId);
    }

    private List<License> buildFallbackLicenseList(String organizationId, Throwable t) {
    	List<License> fallbackList = new ArrayList();
    	License license = new License();
    	license.setLicenseId("0000-0000-0000");
    	license.setOrganizationId(organizationId);
    	license.setProductName("죄송합니다. 현재 라이센스 정보가 제공되지 않습니다.");
    	fallbackList.add(license);
    	return fallbackList;
    }
    
    public License getLicense(String licenseId, String organizationId, String clientType) {
    	License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        if (license == null) {
            throw new IllegalArgumentException(
                String.format(messages.getMessage("license.search.error.message", null, null), 
                		licenseId, organizationId));
        }
        
        // 조직 정보를 조회해서 라이센스 정보에 추가 
        Organization organization = retriveOrganizationInfo(organizationId, clientType);
        if (organization != null) {
        	license.setOrganizationName(organization.getName());
        	license.setContactName(organization.getName());
        	license.setContactPhone(organization.getContactPhone());
        	license.setContactEmail(organization.getContactEmail());
        }
                
        return license.withComment(config.getProperty());
    }
    
    @Autowired
    OrganizationFeignClient organizationFeignClient;
    
    @Autowired
    OrganizationRestTemplateClient organizationRestTemplateClient;
    
    @Autowired
    OrganizationDiscoveryClient organizationDiscoveryClient; 
    
    
    private Organization retriveOrganizationInfo(String organizationId, String clientType) {
    	Organization organization = null;
    	
    	switch(clientType) {
    	case "feign":
    		organization = organizationFeignClient.getOrganization(organizationId);
    		break;
    	case "rest":
    		organization = organizationRestTemplateClient.getOrganization(organizationId);
    		break;    		
    	case "discovery":
    		organization = organizationDiscoveryClient.getOrganization(organizationId);
    		break;
    	}
    	return organization;
    }
    
    
    public License getLicense(String licenseId, String organizationId) {
    	log.info("LicenseSerivce Correlation ID: {}", UserContextHolder.getContext().getCorrelationId());
    	
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        if (license == null) {
            throw new IllegalArgumentException(
                String.format(messages.getMessage("license.search.error.message", null, null), 
licenseId, organizationId));
        }
        return license.withComment(config.getProperty());
    }

    public License createLicense(License license) {
        license.setLicenseId(UUID.randomUUID().toString());
        licenseRepository.save(license);
        return license.withComment(config.getProperty());
    }

    public License updateLicense(License license) {
        licenseRepository.save(license);
        return license.withComment(config.getProperty());
    }

    public String deleteLicense(String licenseId) {
        String responseMessage = null;
        License license = new License();
        license.setLicenseId(licenseId);
        licenseRepository.delete(license);
        responseMessage = String.format(messages.getMessage("license.delete.message", null, null), licenseId);
        return responseMessage;
    }
}
