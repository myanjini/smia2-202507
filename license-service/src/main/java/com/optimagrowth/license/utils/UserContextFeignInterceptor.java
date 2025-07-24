package com.optimagrowth.license.utils;

import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class UserContextFeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        String correlationId = UserContextHolder.getContext().getCorrelationId();
        String authToken = UserContextHolder.getContext().getAuthToken();

        if (correlationId != null) {
        	template.header(UserContext.CORRELATION_ID, correlationId);
        }
        if (authToken != null) {
        	template.header(UserContext.AUTH_TOKEN, authToken);
        }
    }
}
