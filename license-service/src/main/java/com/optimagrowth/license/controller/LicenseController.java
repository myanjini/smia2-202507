package com.optimagrowth.license.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.optimagrowth.license.model.License;
import com.optimagrowth.license.service.LicenseService;

@RestController
@RequestMapping(value = "v1/organization/{organizationId}/license")
public class LicenseController {

	@Autowired
	private LicenseService licenseService;
	
	@GetMapping(value = "/{licenseId}")
	public ResponseEntity<License> getLicense(
			@PathVariable("organizationId") String organizationId, 
			@PathVariable("licenseId") String licenseId) {
		License license = licenseService.getLicense(licenseId, organizationId);
		return ResponseEntity.ok(license);
	}
	
	@PostMapping
	public ResponseEntity<String> createLicense(
			@PathVariable("organizationId") String organizationId,
			@RequestBody License license) {
		String result = licenseService.createLicense(license, organizationId);
		return ResponseEntity.ok(result);
	}
	
	@PutMapping
	public ResponseEntity<String> updateLicense(
			@PathVariable("organizationId") String organizationId,
			@RequestBody License license) {
		String result = licenseService.updateLicense(license, organizationId);
		return ResponseEntity.ok(result);
	}
	
	@DeleteMapping(value = "/{licenseId}")
	public ResponseEntity<String> deleteLicense(
			@PathVariable("organizationId") String organizationId, 
			@PathVariable("licenseId") String licenseId) {
		String result = licenseService.deleteLicense(licenseId, organizationId);
		return ResponseEntity.ok(result);
	}
}
