package com.optimagrowth.license.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "example")
@Getter
@Setter
public class ServiceConfig {
	// 설정에 example.property 항목의 값을 가지는 변수
	private String property;
}
