package com.optimagrowth.license.service;

import java.util.Locale;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.optimagrowth.license.model.License;

@Service
public class LicenseService {
	@Autowired
	MessageSource messageSource;
	
    public License getLicense(String licenseId, String organizationId) {
        License license = new License();
        license.setId(new Random().nextInt(1000));
        license.setLicenseId(licenseId);
        license.setOrganizationId(organizationId);
        license.setDescription("Software product");
        license.setProductName("Ostock");
        license.setLicenseType("full");
        return license;
    }

    public String createLicense(License license, String organizationId, Locale locale) {
        String responseMessage = null;
        if (license != null) {
            license.setOrganizationId(organizationId);
            String message = messageSource.getMessage("license.create.message", null, locale);
            responseMessage = String.format(message, license.toString());
        }
        return responseMessage;
    }

    public String updateLicense(License license, String organizationId) {
        String responseMessage = null;
        if (license != null) {
            license.setOrganizationId(organizationId);
            // 로케일을 지정하지 않으면 부트스트랩에 정의된 디폴트 로케일을 사용
            String message = messageSource.getMessage("license.update.message", null, null);
            responseMessage = String.format(message, license.toString());
        }
        return responseMessage;
    }

    public String deleteLicense(String licenseId, String organizationId) {
        String responseMessage = null;
        String message = messageSource.getMessage("license.delete.message", null, null);
        responseMessage = String.format(message, licenseId,
                organizationId);
        return responseMessage;
    }

}
