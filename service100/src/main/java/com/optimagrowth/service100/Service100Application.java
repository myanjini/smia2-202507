package com.optimagrowth.service100;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class Service100Application {

	public static void main(String[] args) {
		SpringApplication.run(Service100Application.class, args);
	}

}
