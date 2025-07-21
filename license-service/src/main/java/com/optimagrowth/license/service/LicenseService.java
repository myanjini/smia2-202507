package com.optimagrowth.license.service;

import java.util.UUID;

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

@Service
public class LicenseService {
    @Autowired
    MessageSource messages;

    @Autowired
    private LicenseRepository licenseRepository;
    
    @Autowired 
    ServiceConfig config;
    
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
