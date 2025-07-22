package com.optimagrowth.servicefirst.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("SERVICEFOURTH")
public interface ServiceFourth {
	@GetMapping(value = "/")
	String getServiceNameAndOwner();
}
