package com.optimagrowth.gateway.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthServiceClient {
	private final WebClient.Builder webClientBuilder;
	
	public Mono<String> validateToken(String token, String memberId) {
		return webClientBuilder.build()
				.get()
				.uri("http://localhost:8080/validate/"+memberId)
				.header("Authorization", token)
				.retrieve()
				.bodyToMono(String.class)
				.onErrorReturn("INVALID");
	}
}
