package com.optimagrowth.service100.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("SERVICETHIRD")
public interface ServiceThird {
	@GetMapping(value = "/")
	String getServiceNameAndOwner();
}
