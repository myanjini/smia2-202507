package com.optimagrowth.gateway.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthServiceClient {
	private final WebClient.Builder webClientBuilder;
	
	public Mono<String> validateToken(String token, String memberId) {
		log.debug(">>>" + token);
		log.debug(">>>" + memberId);
		return webClientBuilder.build()
				.get()
				// .uri("http://localhost:8080/validate/"+memberId)
				.uri("http://member-service/validate/" + memberId)
				.header("Authorization", token)
				.retrieve()
				.bodyToMono(String.class)
				.onErrorReturn("INVALIDX");
	}
}
