package com.optimagrowth.license.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileTestController {
	@Value("${spring.application.name}")
	private String applicationName;
	@Value("${custom.name:anonymous}")
	private String customName;
	
	@GetMapping("/")
	public String profileTest() {
		return customName + "@" + applicationName;
	}
}
