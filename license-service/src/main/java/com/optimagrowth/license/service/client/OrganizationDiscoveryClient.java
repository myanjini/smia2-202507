package com.optimagrowth.license.service.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.optimagrowth.license.model.Organization;

@Component
public class OrganizationDiscoveryClient {

	@Autowired
	private DiscoveryClient discoveryClient;
	
	public Organization getOrganization(String organizationId) {
		List<ServiceInstance> instances = discoveryClient.getInstances("organization-service");
		if (instances.size() == 0)
			return null;
		
		RestTemplate restTemplate = new RestTemplate();
		
		String serviceUrl = String.format("%s/v1/organization/%s", 
				instances.get(0).getUri().toString(), organizationId);
		ResponseEntity<Organization> restExchange 
			= restTemplate.exchange(serviceUrl, HttpMethod.GET, null, Organization.class);
				
		return restExchange.getBody();
	}
}
