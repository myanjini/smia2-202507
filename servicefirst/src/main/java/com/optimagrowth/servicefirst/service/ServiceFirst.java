package com.optimagrowth.servicefirst.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("SERVICEFIRST")
public interface ServiceFirst {
	@GetMapping(value = "/")
	String getServiceNameAndOwner();
}
