package com.optimagrowth.servicefirst.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.optimagrowth.servicefirst.service.ServiceFirst;
import com.optimagrowth.servicefirst.service.ServiceFourth;
import com.optimagrowth.servicefirst.service.ServiceSecond;
import com.optimagrowth.servicefirst.service.ServiceThird;

@RestController
public class ServiceFirstController {
	@Autowired ServiceFirst first;
	@Autowired ServiceSecond second;
	@Autowired ServiceThird third;
	@Autowired ServiceFourth fourth;
	
	@Value("${const.service-owner}")
	private String serviceOwner;
	
	@Value("${spring.application.name}")
	private String applicationName;
	
	@GetMapping("/")
	public String me() {
		return applicationName + " + " + serviceOwner;
	}
	
	@GetMapping("/{other}")
	public String other(@PathVariable("other") String other) {
		String serviceNameAndOwner = "";
		switch (other) {
		case "first": 
			serviceNameAndOwner = first.getServiceNameAndOwner(); 
			break;
		case "second": 
			serviceNameAndOwner = second.getServiceNameAndOwner(); 
			break;
		case "third": 
			serviceNameAndOwner = third.getServiceNameAndOwner(); 
			break;
		case "fourth": 
			serviceNameAndOwner = fourth.getServiceNameAndOwner(); 
			break;
		}
		return me() + " + " + serviceNameAndOwner;
	}
}
