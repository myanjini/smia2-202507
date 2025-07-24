package com.optimagrowth.license.utils;

import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserContextFeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        String correlationId = UserContextHolder.getContext().getCorrelationId();
        String authToken = UserContextHolder.getContext().getAuthToken();

        log.debug(correlationId);
        log.debug(authToken);
        if (correlationId != null) {
        	template.header(UserContext.CORRELATION_ID, correlationId);
        }
        if (authToken != null) {
        	template.header(UserContext.AUTH_TOKEN, authToken);
        }
    }
}
