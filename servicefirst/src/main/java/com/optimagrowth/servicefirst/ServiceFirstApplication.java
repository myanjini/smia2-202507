package com.optimagrowth.servicefirst;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ServiceFirstApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceFirstApplication.class, args);
	}

}
