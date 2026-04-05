package com.smartcampus.filter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.logging.Logger;

@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {
    private static final AtomicInteger requestCounter = new AtomicInteger(1);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        int requestId = requestCounter.getAndIncrement();
        requestContext.setProperty("requestId", requestId);
        logger.info("[" + requestId + "] REQUEST: " + requestContext.getMethod() + " " + requestContext.getUriInfo().getRequestUri());
    }
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        responseContext.getHeaders().add("X-API-Version", "1.0.0");
        int requestId = (int) requestContext.getProperty("requestId");
        logger.info("[" + requestId + "] RESPONSE: " + responseContext.getStatus());
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        requestContext.setProperty("startTime", System.currentTimeMillis());
        // existing code
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        long startTime = (long) requestContext.getProperty("startTime");
        long duration = System.currentTimeMillis() - startTime;
        logger.info("Duration: " + duration + "ms");
    }
}